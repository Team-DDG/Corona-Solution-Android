package com.golddog.mask_location.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpannable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.golddog.mask_location.R
import com.golddog.mask_location.data.local.SharedPreference
import com.golddog.mask_location.databinding.ActivityMainBinding
import com.golddog.mask_location.util.showToast
import com.golddog.mask_location.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.daum.mf.map.api.MapView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        val agreement = Observer<Boolean> {
            if (!it) {
                setupAgreementDialog().show()
            }
        }

        viewModel.agreement.observe(this, agreement)
        setupMap()
    }

    private fun setupMap() {
        val mapViewContainer = findViewById<ViewGroup>(R.id.map_view)
        mapViewContainer.addView(MapView(this))
    }

    private fun setupAgreementDialog(): MaterialAlertDialogBuilder {
        val span: Spannable = getString(R.string.service_agreement).toSpannable()
        span.setSpan(ForegroundColorSpan(Color.RED), 225, 442, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return MaterialAlertDialogBuilder(this)
            .setTitle(R.string.agreement)
            .setMessage(span)
            .setNegativeButton(
                R.string.disagree
            ) { _, _ ->
                showToast("서약 비동의시 서비스를 이용할 수 없습니다.")
                finish()
            }
            .setPositiveButton(
                R.string.agree
            ) { _, _ ->
                binding.vm?.setLocalAgreementData(true)
                showToast("서비스 사용 서약에 동의했습니다.")
            }
            .setCancelable(false)
        // 람다로 작성함, 기능에 대해 변경해야 될 사항이 있다면, 중괄호 안에 확장해서 사용
    }

//    private fun fabAnim() {
//        if (isFabOpen) {
//            val fabClose = FabAnimation.fabClose()
//            val fabRotateBackward = FabAnimation.fabRotateBackward()
//
//            fab_main_main.startAnimation(fabRotateBackward)
//            fab_mask_main.startAnimation(fabClose)
//            fab_1339call_main.startAnimation(fabClose)
//            fab_corona_manual_main.startAnimation(fabClose)
//            fab_corona_now_main.startAnimation(fabClose)
//
//            fab_mask_main.isClickable = false
//            fab_1339call_main.isClickable = false
//            fab_corona_manual_main.isClickable = false
//            fab_corona_now_main.isClickable = false
//            isFabOpen = false
//        } else {
//            val fabRotateForward = FabAnimation.fabRotateForward()
//            val fabOpen = FabAnimation.fabOpen()
//
//            fab_main_main.startAnimation(fabRotateForward)
//            fab_mask_main.startAnimation(fabOpen)
//            fab_1339call_main.startAnimation(fabOpen)
//            fab_corona_manual_main.startAnimation(fabOpen)
//            fab_corona_now_main.startAnimation(fabOpen)
//
//            fab_mask_main.isClickable = true
//            fab_1339call_main.isClickable = true
//            fab_corona_manual_main.isClickable = true
//            fab_corona_now_main.isClickable = true
//            isFabOpen = true
//        }
//    }
}
