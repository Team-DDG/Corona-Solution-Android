package com.golddog.mask_location.viewmodel

import androidx.lifecycle.MutableLiveData
import com.golddog.mask_location.base.BaseViewModel
import com.golddog.mask_location.data.datasource.StatusDataSource
import com.golddog.mask_location.entity.AccumulateCoronaData
import com.golddog.mask_location.entity.CityCoronaData
import com.golddog.mask_location.entity.CoronaResult

class CoronaStatusViewModel(private val statusDataSource: StatusDataSource) : BaseViewModel() {
    val accumulateData: MutableLiveData<AccumulateCoronaData> =
        MutableLiveData(AccumulateCoronaData("갱신 중...", CoronaResult()))
    val citiesData: MutableLiveData<CityCoronaData> =
        MutableLiveData(CityCoronaData("갱신 중...", mutableListOf()))

    init {
        setAccumulateData()
        setCitiesData()
    }

    private fun setAccumulateData() {
        addDisposable(
            statusDataSource.getAccumulateData()
                .subscribe({
                    accumulateData.value = it
                }, {
                    accumulateData.value?.baseDate = "갱신 실패"
                })
        )
    }

    private fun setCitiesData() {
        addDisposable(
            statusDataSource.getCitiesData()
                .subscribe({
                    citiesData.value = it
                }, {
                    citiesData.value?.baseDate = "갱신 실패"
                })
        )
    }
}