package com.golddog.mask_location.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golddog.mask_location.data.datasource.StatusDataSource
import java.text.DecimalFormat

class CoronaStatusViewModel(private val statusDataSource: StatusDataSource) : ViewModel() {
    var certified: MutableLiveData<String> = MutableLiveData("갱신중")
    var increased: MutableLiveData<String> = MutableLiveData("")
    var cure: MutableLiveData<String> = MutableLiveData("잠시만")
    var patient: MutableLiveData<String> = MutableLiveData("기다려")
    var dead: MutableLiveData<String> = MutableLiveData("주세요")

    @SuppressLint("CheckResult")
    fun setAccumulateData() {
        statusDataSource.getAccumulateData()
            .subscribe({
                val patientData = it.data[0].certified - it.data[0].cure - it.data[0].dead

                certified.postValue(setNumberFormat(it.data[0].certified))
                cure.postValue(setNumberFormat(it.data[0].cure))
                increased.postValue("( +${setNumberFormat(it.data[0].increased)} )")
                dead.postValue(setNumberFormat(it.data[0].dead))
                patient.postValue(setNumberFormat(patientData))
            }) {
                certified.postValue("인터넷")
                cure.postValue("상태를")
                patient.postValue("확인해")
                dead.postValue("주세요")
            }
    }

    private fun setNumberFormat(numberRes: Int): String {
        val df = DecimalFormat("#,###")

        return df.format(numberRes)
    }
}