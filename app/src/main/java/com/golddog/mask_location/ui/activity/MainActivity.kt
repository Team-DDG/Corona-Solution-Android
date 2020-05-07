package com.golddog.mask_location.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.annotation.UiThread
import androidx.core.text.toSpannable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseActivity
import com.golddog.mask_location.data.ApiClient
import com.golddog.mask_location.data.pref.SharedPreference
import com.golddog.mask_location.databinding.ActivityMainBinding
import com.golddog.mask_location.entity.StoreSales
import com.golddog.mask_location.ext.*
import com.golddog.mask_location.ui.dialog.InfoBottomSheet
import com.golddog.mask_location.viewmodel.MainViewModel
import com.golddog.mask_location.viewmodelfactory.MainViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>(),
    OnMapReadyCallback, NaverMapSdk.OnAuthFailedListener {
    override val layoutResourceId: Int = R.layout.activity_main

    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var mapView: MapFragment
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    private var plentyMarkerList: ArrayList<Marker> = arrayListOf()
    private var someMarkerList: ArrayList<Marker> = arrayListOf()
    private var fewMarkerList: ArrayList<Marker> = arrayListOf()
    private var emptyMarkerList: ArrayList<Marker> = arrayListOf()
    private var breakMarkerList: ArrayList<Marker> = arrayListOf()
    private var clinicMarkerList: ArrayList<Marker> = arrayListOf()
    private var hospitalMarkerList: ArrayList<Marker> = arrayListOf()

    private val infoBottomSheet = InfoBottomSheet()

    private val preference by lazy { SharedPreference(this) }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        viewModelFactory = MainViewModelFactory(ApiClient())
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.vm = viewModel
        binding.isFabOpen = false

        val toolbar = toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        setNaverMap()
        checkAgreement()

        viewModel.storesData.observe(this,
            Observer { list ->
                setMarkerInvisible(plentyMarkerList)
                setMarkerInvisible(someMarkerList)
                setMarkerInvisible(fewMarkerList)
                setMarkerInvisible(emptyMarkerList)
                setMarkerInvisible(breakMarkerList)

                plentyMarkerList.clear()
                someMarkerList.clear()
                fewMarkerList.clear()
                emptyMarkerList.clear()
                breakMarkerList.clear()

                list.forEach {
                    setStoreMarkerOnMap(it)
                }
            })

        viewModel.clinicsData.observe(this,
            Observer { list ->
                setMarkerInvisible(clinicMarkerList)

                clinicMarkerList.clear()

                list.forEach {
                    if (::naverMap.isInitialized) {
                        clinicMarkerList.add(
                            setHospitalClinicMarker(it, naverMap, infoBottomSheet, this, true)
                        )
                    }
                }
            })

        viewModel.hospitalsData.observe(this,
            Observer { list ->
                setMarkerInvisible(hospitalMarkerList)

                hospitalMarkerList.clear()

                list.forEach {
                    if (::naverMap.isInitialized) {
                        hospitalMarkerList.add(
                            setHospitalClinicMarker(it, naverMap, infoBottomSheet, this, false)
                        )
                    }
                }
            })

        viewModel.plentyChecked.observe(this,
            Observer {
                if (::naverMap.isInitialized) {
                    if (it) setMarkerVisible(plentyMarkerList, naverMap)
                    else setMarkerInvisible(plentyMarkerList)
                }
            })

        viewModel.someChecked.observe(this,
            Observer {
                if (::naverMap.isInitialized) {
                    if (it) setMarkerVisible(someMarkerList, naverMap)
                    else setMarkerInvisible(someMarkerList)
                }
            })

        viewModel.fewChecked.observe(this,
            Observer {
                if (::naverMap.isInitialized) {
                    if (it) setMarkerVisible(fewMarkerList, naverMap)
                    else setMarkerInvisible(fewMarkerList)
                }
            })

        viewModel.emptyChecked.observe(this,
            Observer {
                if (::naverMap.isInitialized) {
                    if (it) setMarkerVisible(emptyMarkerList, naverMap)
                    else setMarkerInvisible(emptyMarkerList)
                }
            })

        viewModel.breakChecked.observe(this,
            Observer {
                if (::naverMap.isInitialized) {
                    if (it) setMarkerVisible(breakMarkerList, naverMap)
                    else setMarkerInvisible(breakMarkerList)
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        ) return
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.uiSettings.isZoomControlEnabled = false
        naverMap.locationSource = locationSource
        naverMap.addOnCameraIdleListener {
            val latitude = naverMap.cameraPosition.target.latitude
            val longitude = naverMap.cameraPosition.target.longitude
            viewModel.getAroundMaskData(latitude, longitude)
            viewModel.getAroundClinicData(latitude, longitude)
            viewModel.getAroundHospitalData(latitude, longitude)
        }
        this.naverMap = naverMap
    }

    private fun setNaverMap() {
        val fm = supportFragmentManager
        mapView = fm.findFragmentById(R.id.mapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mapView, it).commit()
            }
        mapView.getMapAsync(this)
    }

    private fun setStoreMarkerOnMap(storeSales: StoreSales) {
        val status = storeSales.remainStat
        val marker = setStoreMarker(storeSales, infoBottomSheet, this)

        if (status == "plenty") {
            setMarkerVisibility(marker, viewModel.plentyChecked.value!!, naverMap)
            plentyMarkerList.add(marker)
        } else if (status == "some") {
            setMarkerVisibility(marker, viewModel.someChecked.value!!, naverMap)
            someMarkerList.add(marker)
        } else if (status == "few") {
            setMarkerVisibility(marker, viewModel.fewChecked.value!!, naverMap)
            fewMarkerList.add(marker)
        } else if (status == "empty") {
            setMarkerVisibility(marker, viewModel.emptyChecked.value!!, naverMap)
            emptyMarkerList.add(marker)
        } else if (status == "break") {
            setMarkerVisibility(marker, viewModel.breakChecked.value!!, naverMap)
            breakMarkerList.add(marker)
        } else {
            marker.map = null
        }
    }

    private fun checkAgreement() {
        if (!preference.getAgreement()) getAgreementDialog().show()
    }

    private fun changeIsFabOpen() {
        binding.isFabOpen = !binding.isFabOpen!!
    }

    private fun getAgreementDialog(): MaterialAlertDialogBuilder {
        val contentSpan: Spannable = getString(R.string.service_agreement).toSpannable()
        contentSpan.setSpan(
            ForegroundColorSpan(Color.RED),
            200,
            582,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return MaterialAlertDialogBuilder(this)
            .setTitle(R.string.agreement)
            .setMessage(contentSpan)
            .setNegativeButton(
                R.string.disagree
            ) { _, _ ->
                showToast(R.string.authority_denied)
                finish()
            }
            .setPositiveButton(
                R.string.agree
            ) { _, _ ->
                preference.setAgreement(true)
                showToast(R.string.authority_granted)
            }
            .setCancelable(false)
    }

    override fun onAuthFailed(e: NaverMapSdk.AuthFailedException) {
        if (e.errorCode == "429") {
            showToast("사용량이 많아 지도를 사용할 수 없습니다.")
        }
    }

    fun clickFabMain(view: View) {
        changeIsFabOpen()
    }

    fun clickFabMask(view: View) {
        startActivity(Intent(applicationContext, MaskActivity::class.java))
        changeIsFabOpen()
    }

    fun clickFabCall(view: View) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:1339")))
        changeIsFabOpen()
    }

    fun clickFabManualCorona(view: View) {
        startActivity(Intent(applicationContext, CoronaManualActivity::class.java))
        changeIsFabOpen()
    }

    fun clickFabCurrentCorona(view: View) {
        startActivity(Intent(applicationContext, CoronaStatusActivity::class.java))
        changeIsFabOpen()
    }
}