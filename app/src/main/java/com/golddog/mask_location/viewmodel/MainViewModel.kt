package com.golddog.mask_location.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.golddog.mask_location.base.BaseViewModel
import com.golddog.mask_location.data.datasource.HospitalDataSource
import com.golddog.mask_location.data.datasource.MaskDataSource
import com.golddog.mask_location.entity.HospitalClinic
import com.golddog.mask_location.entity.StoreSales

class MainViewModel(private val maskDataSource: MaskDataSource, private val hospitalDataSource: HospitalDataSource) : BaseViewModel() {
    var storesData: MutableLiveData<List<StoreSales>> = MutableLiveData()
    var clinicsData: MutableLiveData<List<HospitalClinic>> = MutableLiveData()
    var hospitalsData: MutableLiveData<List<HospitalClinic>> = MutableLiveData()

    var plentyChecked: MutableLiveData<Boolean> = MutableLiveData(true)
    var someChecked: MutableLiveData<Boolean> = MutableLiveData(true)
    var fewChecked: MutableLiveData<Boolean> = MutableLiveData(true)
    var emptyChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    var breakChecked: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getAroundMaskData(lat: Double, lng: Double) {
        addDisposable(
            maskDataSource.getAroundMaskData(lat, lng)
                .subscribe({
                    storesData.value = it.stores
                }, {
                    // TODO : "공적 마스크 정보를 불러오는데 실패했습니다." 토스트 띄우는 로직 작성
                })
        )
    }

    fun getAroundClinicData(lat: Double, lng: Double) {
        addDisposable(
            hospitalDataSource.getClinicData(lat, lng)
                .subscribe({
                    clinicsData.value = it.result
                }, {
                    // TODO : "선별진로소 정보를 불러오는데 실패했습니다." 토스트 띄우는 로직 작성
                })
        )
    }

    fun getAroundHospitalData(lat: Double, lng: Double) {
        addDisposable(hospitalDataSource.getHospitalData(lat, lng)
                .subscribe({
                    hospitalsData.value = it.result
                }, {
                    // TODO : "국민 안심 병원 정보를 불러오는데 실패했습니다." 토스트 띄우는 로직 작성
                })
        )
    }
}

