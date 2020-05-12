package com.golddog.mask_location.viewmodel

import androidx.lifecycle.MutableLiveData
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseViewModel
import com.golddog.mask_location.data.datasource.HospitalDataSource
import com.golddog.mask_location.data.datasource.MaskDataSource
import com.golddog.mask_location.entity.HospitalClinic
import com.golddog.mask_location.entity.StoreSales
import com.golddog.mask_location.util.SingleLiveEvent

class MainViewModel(
    private val maskDataSource: MaskDataSource,
    private val hospitalDataSource: HospitalDataSource
) : BaseViewModel() {
    val storesData: MutableLiveData<List<StoreSales>> = MutableLiveData()
    val clinicsData: MutableLiveData<List<HospitalClinic>> = MutableLiveData()
    val hospitalsData: MutableLiveData<List<HospitalClinic>> = MutableLiveData()

    val plentyChecked: MutableLiveData<Boolean> = MutableLiveData(true)
    val someChecked: MutableLiveData<Boolean> = MutableLiveData(true)
    val fewChecked: MutableLiveData<Boolean> = MutableLiveData(true)
    val emptyChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    val breakChecked: MutableLiveData<Boolean> = MutableLiveData(false)

    val toastEvent = SingleLiveEvent<Int>()

    fun getMapData(lat: Double, lng: Double) {
        addDisposable(
            maskDataSource.getAroundMaskData(lat, lng)
                .flatMap {
                    storesData.value = it.stores
                    hospitalDataSource.getClinicData(lat, lng)
                }.flatMap {
                    clinicsData.value = it.result
                    hospitalDataSource.getHospitalData(lat, lng)
                }.subscribe({
                    hospitalsData.value = it.result
                }, {
                    toastEvent.value = R.string.message_load_failed
                })
        )
    }
}

