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
import com.golddog.mask_location.data.pref.SharedPreference
import com.golddog.mask_location.databinding.ActivityMainBinding
import com.golddog.mask_location.ext.showToast
import com.golddog.mask_location.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.daum.mf.map.api.MapView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val preference by lazy {
        BaseApplication.appContext?.let { SharedPreference(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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
        changeFabOpenValue()
    }

    fun clickFabMask(view: View) {
        showToast("fab_2")
        changeFabOpenValue()
    }

    fun clickFabCall(view: View) {
        showToast("fab_3")
        changeFabOpenValue()
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:1339")))
    }

    fun clickFabManualCorona(view: View) {
        showToast("fab_4")
        changeFabOpenValue()
    }

    fun clickFabCurrentCorona(view: View) {
        showToast("fab_5")
        changeFabOpenValue()
    }

    private fun changeFabOpenValue() {
        binding.isFabOpen = !binding.isFabOpen!!
    }
}
