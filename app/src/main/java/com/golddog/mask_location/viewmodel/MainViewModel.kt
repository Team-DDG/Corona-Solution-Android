package com.golddog.mask_location.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golddog.mask_location.data.datasource.MaskDataSource
import com.golddog.mask_location.entity.StoresList

class MainViewModel(private val maskDataSource: MaskDataSource) : ViewModel() {
    var storesData: MutableLiveData<StoresList> = MutableLiveData()
//    var clinicData: MutableLiveData<StoresList> = MutableLiveData()
//    var hospitalData: MutableLiveData<StoresList> = MutableLiveData()
//    TODO : 장호승 서버개발 완료시 사용할 데이터, 자료형 변경예정


    override fun onCleared() {
        super.onCleared()
    }

}