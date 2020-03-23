package com.golddog.mask_location.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golddog.mask_location.data.datasource.StatusDataSource
import com.golddog.mask_location.entity.AccumulateCoronaData
import com.golddog.mask_location.entity.CityCoronaData
import com.golddog.mask_location.entity.CityStatus
import com.golddog.mask_location.entity.CoronaResult
import io.reactivex.disposables.CompositeDisposable

class CoronaStatusViewModel(private val statusDataSource: StatusDataSource) : ViewModel() {
    val accumulateData: MutableLiveData<AccumulateCoronaData> = MutableLiveData(AccumulateCoronaData("갱신 중...", CoronaResult()))
    val citiesData: MutableLiveData<CityCoronaData>
            = MutableLiveData(CityCoronaData("갱신 중...", mutableListOf()))
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    private val disposable = CompositeDisposable()

    init {
        setAccumulateData()
        setCitiesData()
    }

    private fun setAccumulateData() {
        isLoading.value = true
        val accumulateDataDisposable = statusDataSource.getAccumulateData()
            .subscribe({
                accumulateData.value = it
                isLoading.value = false
            }) {
                accumulateData.value?.baseDate = "갱신 실패"
                isLoading.value = false
            }

        disposable.add(accumulateDataDisposable)
    }

    private fun setCitiesData() {
        val citiesDataDisposable = statusDataSource.getCitiesData()
            .subscribe({
                citiesData.value = it
            }) {
                citiesData.value?.baseDate = "갱신 실패"
            }
        disposable.add(citiesDataDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}