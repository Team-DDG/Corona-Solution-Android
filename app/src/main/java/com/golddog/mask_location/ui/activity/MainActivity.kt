package com.golddog.mask_location.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpannable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.golddog.mask_location.R
import com.golddog.mask_location.data.ApiClient
import com.golddog.mask_location.data.pref.SharedPreference
import com.golddog.mask_location.databinding.ActivityMainBinding
import com.golddog.mask_location.entity.StoreSales
import com.golddog.mask_location.ext.showToast
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

class MainActivity : AppCompatActivity(), OnMapReadyCallback, NaverMapSdk.OnAuthFailedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var mapView: MapFragment
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private var markerList: ArrayList<Marker> = arrayListOf()
    private val infoWindow = InfoWindow()

    private val preference by lazy { SharedPreference(this) }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory = MainViewModelFactory(ApiClient())
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.vm = viewModel
        binding.isFabOpen = false
        binding.lifecycleOwner = this

        setNaverMap()
        checkAgreement()

        binding.vm?.storesData?.observe(this,
            Observer { list ->
                markerList.forEach {
                    it.map = null
                }

                list.forEach {
                    setMarkerOnMap(it)
                }
            })
        /** marker list 를 V에서 observe 해서 map 에 등록하는 로직
         * 비교 & 지도에 핀 꼽는 로직 중 비교하는 로직을 VM 으로 Refactoring 하는 방법이 있을까 ?
         * MapView 를 FrameLayout 으로 설정해서, BindingAdapter 를 이용하는 방법에는 한계가 있을 거 같다.
         * foreach 를 두번 도는데 코드는 간결해 보이나 실제 도는 횟수는 몇 천번에 육박할 듯 함
         * 이부분 메모리 릭이 발생할거 같음 로직 변경을 생각해 봐야 한다
         */

        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return infoWindow.marker?.tag as CharSequence? ?: ""
            }
        }
    }

    private fun setNaverMap() {
        val fm = supportFragmentManager
        mapView = fm.findFragmentById(R.id.mapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mapView, it).commit()
            }

        mapView.getMapAsync(this)
    }

    private fun setMarkerOnMap(storeSales: StoreSales) {
        val status = storeSales.remainStat
        val lat = storeSales.lat
        val lng = storeSales.lng
        var marker = Marker()

        marker.position = LatLng(lat, lng)
        marker.map = naverMap

        if (status == "plenty") {
            marker.icon = OverlayImage.fromResource(R.drawable.marker_plenty)
            marker = setMarkerInfoWindow(storeSales, "재고 100개 이상", marker)
        } else if (status == "some") {
            marker.icon = OverlayImage.fromResource(R.drawable.marker_some)
            marker = setMarkerInfoWindow(storeSales, "재고 30개 이상", marker)
        } else if (status == "few") {
            marker.icon = OverlayImage.fromResource(R.drawable.marker_few)
            marker = setMarkerInfoWindow(storeSales, "재고 30개 이하", marker)
        } else if (status == "empty") {
            marker.icon = OverlayImage.fromResource(R.drawable.marker_empty)
            marker = setMarkerInfoWindow(storeSales, "품절", marker)
        } else if (status == "break") {
            marker.icon = OverlayImage.fromResource(R.drawable.marker_break)
            marker = setMarkerInfoWindow(storeSales, "판매 중지", marker)
        } else {
            marker.map = null
        }
        // if문으로 비교하는 이유는 왜인지 when문을 이용하니 튕기는 현상이 발생. 이유는 모름 ㅠㅠ

        marker.setOnClickListener {
            infoWindow.open(marker)
            true
        }
        markerList.add(marker)
    }

    private fun setMarkerInfoWindow(
        storeSales: StoreSales,
        status: String,
        marker: Marker
    ): Marker {
        marker.tag =
            "${storeSales.name}\n${storeSales.address}\n${status}\n입고시간 : ${storeSales.stockAt}\n갱신시간 : ${storeSales.createdAt}"
        return marker
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
        naverMap.locationSource = locationSource
        naverMap.setOnMapClickListener { _, _ ->
            infoWindow.close()
        }
        naverMap.addOnCameraIdleListener {
            val cameraLatLng = naverMap.cameraPosition.target
            binding.vm?.getAroundMaskData(cameraLatLng.latitude, cameraLatLng.longitude)
        }
        this.naverMap = naverMap
    }

    private fun checkAgreement() {
        if (!preference.getAgreement()) getAgreementDialog().show()
    }

    private fun getAgreementDialog(): MaterialAlertDialogBuilder {
        val contentSpan: Spannable = getString(R.string.service_agreement).toSpannable()
        contentSpan.setSpan(
            ForegroundColorSpan(Color.RED),
            200,
            598,
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

    fun clickFabMain(view: View) {
        changeFabOpenValue()
    }

    fun clickFabMask(view: View) {
        startActivity(Intent(applicationContext, MaskActivity::class.java))
        changeFabOpenValue()
    }

    fun clickFabCall(view: View) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:1339")))
        changeFabOpenValue()
    }

    fun clickFabManualCorona(view: View) {
        startActivity(Intent(applicationContext, CoronaManualActivity::class.java))
        changeFabOpenValue()
    }

    fun clickFabCurrentCorona(view: View) {
        startActivity(Intent(applicationContext, CoronaStatusActivity::class.java))
        changeFabOpenValue()
    }

    private fun changeFabOpenValue() {
        binding.isFabOpen = !binding.isFabOpen!!
    }

    override fun onAuthFailed(e: NaverMapSdk.AuthFailedException) {
        if (e.errorCode == "429") {
            showToast("사용량이 많아 지도를 사용할 수 없습니다.")
        }
    }
}
