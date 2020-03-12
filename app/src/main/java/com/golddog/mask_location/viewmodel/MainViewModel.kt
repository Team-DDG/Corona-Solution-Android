package com.golddog.mask_location.viewmodel

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golddog.mask_location.base.BaseApplication
import com.golddog.mask_location.data.local.SharedPreference
import com.golddog.mask_location.util.showToast

class MainViewModel : ViewModel() {
    private val preference by lazy {
        BaseApplication.appContext?.let { SharedPreference.getInstance(it) }
    }

    var fabOpen: MutableLiveData<Boolean> = MutableLiveData(false)
    val agreement: MutableLiveData<Boolean> = MutableLiveData(preference!!.getAgreement())

    fun setLocalAgreementData(agreement: Boolean) {
        preference?.setAgreement(agreement)
    }

    fun clickFabMain() {
        setFabOpen()
        showToast("fab_1")
    }

    fun clickFabMask() {
        setFabOpen()
        showToast("fab_2")
    }

    fun clickFabCall() {
        setFabOpen()
        BaseApplication.appContext?.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:1339")))
    }

    fun clickFabManualCorona() {
        setFabOpen()
        showToast("fab_4")

    }

    fun clickFabCurrentCorona() {
        setFabOpen()
        showToast("fab_5")
    }

    private fun setFabOpen() {
        this.fabOpen.value = !this.fabOpen.value!!
    }
}