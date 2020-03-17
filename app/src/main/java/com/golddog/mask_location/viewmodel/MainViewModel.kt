package com.golddog.mask_location.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golddog.mask_location.data.datasource.MaskDataSource
import com.golddog.mask_location.entity.StoreSales
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val maskDataSource: MaskDataSource) : ViewModel() {
    var storesData: MutableLiveData<List<StoreSales>> = MutableLiveData()
//    var clinicData: MutableLiveData<StoresList> = MutableLiveData()
//    var hospitalData: MutableLiveData<StoresList> = MutableLiveData()
//    TODO : 장호승 서버개발 완료시 사용할 데이터, 자료형 변경예정

    private val disposable = CompositeDisposable()

    fun getAroundMaskData(lat: Double, lng: Double) {
        val storesDataDisposable = maskDataSource.getAroundMaskData(lat, lng, 3000)
            .subscribe({
                storesData.value = it.stores
            }) {
                // TODO : "공적 마스크 정보를 불러오는데 실패했습니다." 토스트 띄우는 로직 작성
            }

        this.disposable.add(storesDataDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}

