package com.golddog.mask_location.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golddog.mask_location.data.datasource.StatusDataSource
import com.golddog.mask_location.entity.CoronaData
import com.golddog.mask_location.entity.CoronaList
import io.reactivex.disposables.CompositeDisposable
import java.text.DecimalFormat

class CoronaStatusViewModel(private val statusDataSource: StatusDataSource) : ViewModel() {
    var coronaList: MutableLiveData<CoronaList> = MutableLiveData(CoronaList(listOf(CoronaData(0, 0, 0, 0))))
    var patient: MutableLiveData<String> = MutableLiveData("0")
    private var disposable = CompositeDisposable()

    init {
        setAccumulateData()
    }


    private fun setAccumulateData() {
        val accumulateDataDisposable = statusDataSource.getAccumulateData()
            .subscribe({
                coronaList.postValue(it)
                val patientData = it.data[0].certified - it.data[0].cure - it.data[0].dead

                patient.postValue("$patientData")
            }) {
                // TODO : Toast 띄워서 오류발생 알리기, 오류로그 : 데이터를 불러오지 못했습니다.
            }

        disposable.add(accumulateDataDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}