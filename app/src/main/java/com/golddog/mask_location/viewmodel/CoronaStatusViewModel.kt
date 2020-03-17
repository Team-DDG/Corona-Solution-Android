package com.golddog.mask_location.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golddog.mask_location.data.datasource.StatusDataSource
import com.golddog.mask_location.entity.CoronaData
import com.golddog.mask_location.entity.CoronaList
import io.reactivex.disposables.CompositeDisposable

class CoronaStatusViewModel(private val statusDataSource: StatusDataSource) : ViewModel() {
    var coronaList: MutableLiveData<CoronaData> = MutableLiveData(CoronaData(0, 0, 0, 0))
    var cityCoronaList: MutableLiveData<CoronaData> = MutableLiveData(CoronaData(0, 0, 0, 0))
    var patient: MutableLiveData<String> = MutableLiveData("0")
    private var disposable = CompositeDisposable()

    init {
        setAccumulateData()
    }


    private fun setAccumulateData() {
        val accumulateDataDisposable = statusDataSource.getCoronaStatusData(null)
            .subscribe({
                val serverData = it.data[0]
                coronaList.value = serverData
                val patientData = serverData.certified - serverData.cure - serverData.dead

                patient.value = patientData.toString()
            }) {
                // TODO : Toast 띄워서 오류발생 알리기, 오류로그 : 데이터를 불러오지 못했습니다.
            }

        disposable.add(accumulateDataDisposable)
    }

    private fun setCityStatusData() {
        val cityArray = ArrayList<String>()
        for (city in cityArray){
            val cityStatusDataDisposable = statusDataSource.getCoronaStatusData(city)
                .subscribe({
                    val serverData = it.data[0]
                    cityCoronaList.value = serverData
                }){

                }

            disposable.add(cityStatusDataDisposable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}