package com.golddog.mask_location.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpannable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseApplication
import com.golddog.mask_location.data.ApiClient
import com.golddog.mask_location.data.pref.SharedPreference
import com.golddog.mask_location.databinding.ActivityMainBinding
import com.golddog.mask_location.ext.showToast
import com.golddog.mask_location.viewmodel.MainViewModel
import com.golddog.mask_location.viewmodelfactory.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.daum.mf.map.api.MapView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    private val preference by lazy {
        BaseApplication.appContext?.let { SharedPreference(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory = ViewModelFactory(ApiClient())
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.vm = viewModel
        binding.isFabOpen = false
        binding.lifecycleOwner = this

        checkAgreement()

        setupMap()
    }

    private fun setupMap() {
        val mapViewContainer = findViewById<ViewGroup>(R.id.map_view)
        mapViewContainer.addView(MapView(this))
    }

    private fun checkAgreement() {
        if (!preference?.getAgreement()!!) getAgreementDialog().show()
    }

    private fun getAgreementDialog(): MaterialAlertDialogBuilder {
        val span: Spannable = getString(R.string.service_agreement).toSpannable()
        span.setSpan(ForegroundColorSpan(Color.RED), 225, 442, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return MaterialAlertDialogBuilder(this)
            .setTitle(R.string.agreement)
            .setMessage(span)
            .setNegativeButton(
                R.string.disagree
            ) { _, _ ->
                showToast(R.string.authority_denied)
                finish()
            }
            .setPositiveButton(
                R.string.agree
            ) { _, _ ->
                preference?.setAgreement(true)
                showToast(R.string.authority_granted)
            }
            .setCancelable(false)
        // 람다로 작성함, 기능에 대해 변경해야 될 사항이 있다면, 중괄호 안에 확장해서 사용
    }

    fun clickFabMain(view: View) {
        showToast("fab_1")
//        fabAnimation()
        changeFabOpenValue()
    }

    fun clickFabMask(view: View) {
        showToast("fab_2")
//        fabAnimation()
        changeFabOpenValue()
    }

    fun clickFabCall(view: View) {
        showToast("fab_3")
//        fabAnimation()
        changeFabOpenValue()
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:1339")))
    }

    fun clickFabManualCorona(view: View) {
        showToast("fab_4")
//        fabAnimation()
        changeFabOpenValue()
    }

    fun clickFabCurrentCorona(view: View) {
        showToast("fab_5")
//        fabAnimation()
        changeFabOpenValue()
    }

//    private fun fabAnimation() {
//        if (binding.vm?.isFabOpen?.value!!) {
//            binding.fabMainMain.startAnimation(FabAnimation.fabRotateBackward())
//            binding.fabMaskMain.startAnimation(FabAnimation.fabClose())
//            binding.tvFabMaskLabelMain.startAnimation(FabAnimation.fabClose())
//            binding.fab1339callMain.startAnimation(FabAnimation.fabClose())
//            binding.tvFab1339callLabelMain.startAnimation(FabAnimation.fabClose())
//            binding.fabCoronaManualMain.startAnimation(FabAnimation.fabClose())
//            binding.tvFabCoronaManualLabelMain.startAnimation(FabAnimation.fabClose())
//            binding.fabCoronaCurrentMain.startAnimation(FabAnimation.fabClose())
//            binding.tvFabCoronaCurrentLabelMain.startAnimation(FabAnimation.fabClose())
//        } else {
//            binding.fabMainMain.startAnimation(FabAnimation.fabRotateForward())
//            binding.fabMaskMain.startAnimation(FabAnimation.fabOpen())
//            binding.tvFabMaskLabelMain.startAnimation(FabAnimation.fabOpen())
//            binding.fab1339callMain.startAnimation(FabAnimation.fabOpen())
//            binding.tvFab1339callLabelMain.startAnimation(FabAnimation.fabOpen())
//            binding.fabCoronaManualMain.startAnimation(FabAnimation.fabOpen())
//            binding.tvFabCoronaManualLabelMain.startAnimation(FabAnimation.fabOpen())
//            binding.fabCoronaCurrentMain.startAnimation(FabAnimation.fabOpen())
//            binding.tvFabCoronaCurrentLabelMain.startAnimation(FabAnimation.fabOpen())
//        }
//    }

    private fun changeFabOpenValue() {
//        binding.vm?.isFabOpen?.value = !binding.vm?.isFabOpen?.value!!
        binding.isFabOpen = !binding.isFabOpen!!
    }
}
