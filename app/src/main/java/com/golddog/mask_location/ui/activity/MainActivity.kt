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
import com.golddog.mask_location.data.local.SharedPreference
import com.golddog.mask_location.databinding.ActivityMainBinding
import com.golddog.mask_location.util.FabAnimation
import com.golddog.mask_location.util.showToast
import com.golddog.mask_location.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var isFabOpen = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val preference by lazy {
        SharedPreference.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        setupFab()
        setupMap()

        if (!preference?.getAgreement()!!) {
            setupAgreementDialog().show()
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            fab_main_main -> {
                fabAnim()
                showToast("fab_1")
            }
            fab_mask_main -> {
                fabAnim()
                showToast("fab_2")
            }
            fab_1339call_main -> {
                fabAnim()
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:1339")))
            }
            fab_corona_manual_main -> {
                fabAnim()
                showToast("fab_4")
            }
            fab_corona_now_main -> {
                fabAnim()
                showToast("fab_5")
            }
        }
    }

    private fun setupFab() {
        fab_main_main.setOnClickListener(this)
        fab_mask_main.setOnClickListener(this)
        fab_1339call_main.setOnClickListener(this)
        fab_corona_manual_main.setOnClickListener(this)
        fab_corona_now_main.setOnClickListener(this)
    }

    private fun setupMap() {
//        val mapView = MapView(this)
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
                preference?.setAgreement(true)
                showToast("서비스 사용 서약에 동의했습니다.")
            }
            .setCancelable(false)
        // 람다로 작성함, 기능에 대해 변경해야 될 사항이 있다면, 중괄호 안에 확장해서 사용
    }

    private fun fabAnim() {
        if (isFabOpen) {
            val fabClose = FabAnimation.fabClose()
            val fabRotateBackward = FabAnimation.fabRotateBackward()

            fab_main_main.startAnimation(fabRotateBackward)
            fab_mask_main.startAnimation(fabClose)
            fab_1339call_main.startAnimation(fabClose)
            fab_corona_manual_main.startAnimation(fabClose)
            fab_corona_now_main.startAnimation(fabClose)

            fab_mask_main.isClickable = false
            fab_1339call_main.isClickable = false
            fab_corona_manual_main.isClickable = false
            fab_corona_now_main.isClickable = false
            isFabOpen = false
        } else {
            val fabRotateForward = FabAnimation.fabRotateFoward()
            val fabOpen = FabAnimation.fabOpen()

            fab_main_main.startAnimation(fabRotateForward)
            fab_mask_main.startAnimation(fabOpen)
            fab_1339call_main.startAnimation(fabOpen)
            fab_corona_manual_main.startAnimation(fabOpen)
            fab_corona_now_main.startAnimation(fabOpen)

            fab_mask_main.isClickable = true
            fab_1339call_main.isClickable = true
            fab_corona_manual_main.isClickable = true
            fab_corona_now_main.isClickable = true
            isFabOpen = true
        }
    }
}
