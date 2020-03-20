package com.golddog.mask_location.viewmodel

import android.util.Log
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
    private val disposable = CompositeDisposable()

    init {
        setAccumulateData()
        setCitiesData()
    }

    private fun setAccumulateData() {
        val accumulateDataDisposable = statusDataSource.getAccumulateData()
            .subscribe({
                accumulateData.value = it
            }) {
                accumulateData.value?.baseDate = "갱신 실패"
            }

        disposable.add(accumulateDataDisposable)
    }

    private fun setCitiesData() {
        val citiesDataDisposable = statusDataSource.getCitiesData()
            .subscribe({
                it.cityStatuses.add(0, CityStatus("지역명", "누적 확진환자", "사망자"))
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