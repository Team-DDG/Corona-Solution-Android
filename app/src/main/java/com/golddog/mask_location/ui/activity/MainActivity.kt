package com.golddog.mask_location.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpannable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.golddog.mask_location.R
import com.golddog.mask_location.data.ApiClient
import com.golddog.mask_location.data.pref.SharedPreference
import com.golddog.mask_location.databinding.ActivityMainBinding
import com.golddog.mask_location.ext.showToast
import com.golddog.mask_location.viewmodel.MainViewModel
import com.golddog.mask_location.viewmodelfactory.MainViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity(), OnMapReadyCallback, NaverMapSdk.OnAuthFailedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var mapView: MapFragment
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
    private val preference by lazy { SharedPreference(this) }

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
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) return
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setNaverMap() {
        val fm = supportFragmentManager
        mapView = fm.findFragmentById(R.id.mapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mapView, it).commit()
            }

        mapView.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.locationSource = locationSource
        naverMap.addOnLocationChangeListener {

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
        // 람다로 작성함, 기능에 대해 변경해야 될 사항이 있다면, 중괄호 안에 확장해서 사용
    }

    fun clickFabMain(view: View) {
        changeFabOpenValue()
    }

    fun clickFabMask(view: View) {
        startActivity(Intent(applicationContext, MaskActivity::class.java))
        changeFabOpenValue()
    }

    fun clickFabCall(view: View) {
        changeFabOpenValue()
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:1339")))
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
