package com.golddog.mask_location.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golddog.mask_location.data.datasource.StatusDataSource
import com.golddog.mask_location.entity.AccumulateCoronaData
import com.golddog.mask_location.entity.CoronaResult
import io.reactivex.disposables.CompositeDisposable

class CoronaStatusViewModel(private val statusDataSource: StatusDataSource) : ViewModel() {
    var coronaList: MutableLiveData<AccumulateCoronaData> = MutableLiveData(
        AccumulateCoronaData(
            "갱신 중...",
            CoronaResult()
        )
    )
    private val disposable = CompositeDisposable()

    init {
        setAccumulateData()
    }

    private fun setAccumulateData() {
        val accumulateDataDisposable = statusDataSource.getAccumulateData()
            .subscribe({
                coronaList.value = it
            }) {
                coronaList.value?.baseDate = "갱신 실패"
            }

        disposable.add(accumulateDataDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}