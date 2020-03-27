package com.golddog.mask_location.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseActivity
import com.golddog.mask_location.data.ApiClient
import com.golddog.mask_location.data.pref.SharedPreference
import com.golddog.mask_location.databinding.ActivityMainBinding
import com.golddog.mask_location.entity.HospitalClinic
import com.golddog.mask_location.entity.StoreSales
import com.golddog.mask_location.ext.showToast
import com.golddog.mask_location.ui.dialog.InfoWindowDialog
import com.golddog.mask_location.viewmodel.MainViewModel
import com.golddog.mask_location.viewmodelfactory.MainViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
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

    private val infoWindow = InfoWindow()
    private val infoWindowDialog = InfoWindowDialog()

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
                    setClinicMarkerOnMap(it)
                }
        })

        viewModel.hospitalsData.observe(this,
            Observer{list ->
                setMarkerInvisible(hospitalMarkerList)

                hospitalMarkerList.clear()

                list.forEach {
                    setHospitalMarkerOnMap(it)
                }
        })

        viewModel.plentyChecked.observe(this,
            Observer {
                if (it) setMarkerVisible(plentyMarkerList)
                else setMarkerInvisible(plentyMarkerList)
            })

        viewModel.someChecked.observe(this,
            Observer {
                if (it) setMarkerVisible(someMarkerList)
                else setMarkerInvisible(someMarkerList)
            })

        viewModel.fewChecked.observe(this,
            Observer {
                if (it) setMarkerVisible(fewMarkerList)
                else setMarkerInvisible(fewMarkerList)
            })

        viewModel.emptyChecked.observe(this,
            Observer {
                if (it) setMarkerVisible(emptyMarkerList)
                else setMarkerInvisible(emptyMarkerList)
            })

        viewModel.breakChecked.observe(this,
            Observer {
                if (it) setMarkerVisible(breakMarkerList)
                else setMarkerInvisible(breakMarkerList)
            })

        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return infoWindow.marker?.tag as CharSequence? ?: ""
            }
        }
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
        naverMap.setOnMapClickListener { _, _ ->
            infoWindow.close()
        }
        naverMap.addOnCameraIdleListener {
            val cameraLatLng = naverMap.cameraPosition.target
            viewModel.getAroundMaskData(cameraLatLng.latitude, cameraLatLng.longitude)
            viewModel.getAroundClinicData(cameraLatLng.latitude, cameraLatLng.longitude)
            viewModel.getAroundHospitalData(cameraLatLng.latitude, cameraLatLng.longitude)
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

    private fun setMarkerVisible(markers: List<Marker>) {
        markers.forEach {
            it.map = naverMap
        }
    }

    private fun setMarkerInvisible(markers: List<Marker>) {
        markers.forEach {
            it.map = null
        }
    }

    private fun setMarkerVisibility(marker: Marker, visibility: Boolean) {
        if (visibility) marker.map = naverMap
        else marker.map = null
    }

    private fun setMarkerImage(overlayImage: OverlayImage, marker: Marker) {
        marker.icon = overlayImage
    }

    private fun setStoreMarkerOnMap(storeSales: StoreSales) {
        val marker = Marker()
        val status = storeSales.remainStat

        marker.position = LatLng(storeSales.lat, storeSales.lng)
        marker.setOnClickListener {
            infoWindow.open(marker)
            //TODO: here to call bottom sheet fragment
            Log.d("marker", marker.tag.toString())
            Toast.makeText(applicationContext, marker.tag.toString(), Toast.LENGTH_LONG).show()
            infoWindowDialog.setInfo(marker.tag.toString())
            infoWindowDialog.show(supportFragmentManager, "infoWindow")
            true
        }

        if (status == "plenty") {
            setMarkerImage(OverlayImage.fromResource(R.drawable.marker_plenty), marker)
            setStoreMarkerTag(
                storeSales,
                getString(R.string.plenty_status),
                ContextCompat.getColor(this, R.color.marker_plenty),
                marker
            )
            viewModel.plentyChecked.value?.let { setMarkerVisibility(marker, it) }
            plentyMarkerList.add(marker)
        } else if (status == "some") {
            setMarkerImage(OverlayImage.fromResource(R.drawable.marker_some), marker)
            setStoreMarkerTag(
                storeSales,
                getString(R.string.some_status),
                ContextCompat.getColor(this, R.color.marker_some),
                marker
            )
            viewModel.someChecked.value?.let { setMarkerVisibility(marker, it) }
            someMarkerList.add(marker)
        } else if (status == "few") {
            setMarkerImage(OverlayImage.fromResource(R.drawable.marker_few), marker)
            setStoreMarkerTag(
                storeSales,
                getString(R.string.few_status),
                ContextCompat.getColor(this, R.color.marker_few),
                marker
            )
            viewModel.fewChecked.value?.let { setMarkerVisibility(marker, it) }
            fewMarkerList.add(marker)
        } else if (status == "empty") {
            setMarkerImage(OverlayImage.fromResource(R.drawable.marker_empty), marker)
            setStoreMarkerTag(
                storeSales,
                getString(R.string.empty_status),
                ContextCompat.getColor(this, R.color.marker_none),
                marker
            )
            viewModel.emptyChecked.value?.let { setMarkerVisibility(marker, it) }
            emptyMarkerList.add(marker)
        } else if (status == "break") {
            setMarkerImage(OverlayImage.fromResource(R.drawable.marker_break), marker)
            setStoreMarkerTag(
                storeSales,
                getString(R.string.break_status),
                ContextCompat.getColor(this, R.color.marker_none),
                marker
            )
            viewModel.breakChecked.value?.let { setMarkerVisibility(marker, it) }
            breakMarkerList.add(marker)
        } else {
            marker.map = null
        }
        // if문으로 비교하는 이유는 when문은 hashcode 까지 비교해서 오류가 발생함.
    }

    private fun setClinicMarkerOnMap(clinic: HospitalClinic) {
        val marker = Marker()
        marker.position = LatLng(clinic.lat.toDouble(), clinic.lng.toDouble())
        marker.setOnClickListener {
            infoWindow.open(marker)
            true
        }
        setMarkerVisible(listOf(marker))
        marker.iconTintColor = ContextCompat.getColor(this, R.color.marker_clinic)
        setHospitalClinicMarkerTag(clinic, true, marker)
        clinicMarkerList.add(marker)
    }

    private fun setHospitalMarkerOnMap(hospital: HospitalClinic) {
        val marker = Marker()
        marker.position = LatLng(hospital.lat.toDouble(), hospital.lng.toDouble())
        marker.setOnClickListener {
            infoWindow.open(marker)
            true
        }
        setMarkerVisible(listOf(marker))
        marker.iconTintColor = ContextCompat.getColor(this, R.color.marker_hospital)
        setHospitalClinicMarkerTag(hospital, false, marker)
        hospitalMarkerList.add(marker)
    }

    private fun setStoreMarkerTag(
        storeSales: StoreSales,
        status: String,
        colorCode: Int,
        marker: Marker
    ) {
        val storeName = "${storeSales.name}\n"
        val tagString =
            "${storeSales.name}\n${storeSales.address}\n${status}\n" +
                    "입고시간 : ${storeSales.stockAt}\n갱신시간 : ${storeSales.createdAt}"
        val storeNameStart = 0
        val storeNameEnd = storeName.length
        val statusStart = tagString.indexOf(status)
        val statusEnd = statusStart + status.length

        val spannableString = SpannableString(tagString)
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            storeNameStart,
            storeNameEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            RelativeSizeSpan(1.35f),
            storeNameStart,
            storeNameEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(colorCode),
            statusStart,
            statusEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        marker.tag = spannableString
    }

    private fun setHospitalClinicMarkerTag(
        hospitalClinic: HospitalClinic,
        isClinic: Boolean,
        marker: Marker
    ) {
        val storeName = hospitalClinic.name
        val hospitalOrClinic = if (isClinic) "선별진료소" else "국민안심병원"
        val tagString = "$storeName ($hospitalOrClinic)\n" +
                "${hospitalClinic.address}\n${hospitalClinic.phone}"
        val firstLineStart = 0
        val firstLineEnd = storeName.length + 3 + hospitalOrClinic.length

        val spannableString = SpannableString(tagString)
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            firstLineStart,
            firstLineEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            RelativeSizeSpan(1.35f),
            firstLineStart,
            firstLineEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        marker.tag = spannableString
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