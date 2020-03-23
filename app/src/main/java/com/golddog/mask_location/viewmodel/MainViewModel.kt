package com.golddog.mask_location.viewmodel

import androidx.lifecycle.MutableLiveData
import com.golddog.mask_location.base.BaseViewModel
import com.golddog.mask_location.data.datasource.MaskDataSource
import com.golddog.mask_location.entity.StoreSales

class MainViewModel(private val maskDataSource: MaskDataSource) : BaseViewModel() {
    var storesData: MutableLiveData<List<StoreSales>> = MutableLiveData()
    var plentyChecked: MutableLiveData<Boolean> = MutableLiveData(true)
    var someChecked: MutableLiveData<Boolean> = MutableLiveData(true)
    var fewChecked: MutableLiveData<Boolean> = MutableLiveData(true)
    var emptyChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    var breakChecked: MutableLiveData<Boolean> = MutableLiveData(false)

//    var clinicData: MutableLiveData<StoresList> = MutableLiveData()
//    var hospitalData: MutableLiveData<StoresList> = MutableLiveData()
//    TODO : 장호승 서버개발 완료시 사용할 데이터, 자료형 변경예정

    fun getAroundMaskData(lat: Double, lng: Double) {
        addDisposable(
            maskDataSource.getAroundMaskData(lat, lng, 3000)
                .subscribe({
                    storesData.value = it.stores
                }, {
                    // TODO : "공적 마스크 정보를 불러오는데 실패했습니다." 토스트 띄우는 로직 작성
                })
        )
    }
}

