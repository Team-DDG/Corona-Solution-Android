package com.golddog.mask_location.data

import com.golddog.mask_location.data.api.HospitalApi
import com.golddog.mask_location.data.api.MaskApi
import com.golddog.mask_location.data.api.StatusApi
import com.golddog.mask_location.data.datasource.HospitalDataSource
import com.golddog.mask_location.data.datasource.MaskDataSource
import com.golddog.mask_location.data.datasource.StatusDataSource
import com.golddog.mask_location.entity.AccumulateCoronaData
import com.golddog.mask_location.entity.CityCoronaData
import com.golddog.mask_location.entity.HospitalClinicList
import com.golddog.mask_location.entity.StoresList
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiClient : StatusDataSource, MaskDataSource, HospitalDataSource {
    // 얘가 subscribeOn과 observeOn을 이렇게 반복하는 로직이 좀 보기 싫어서 setSchedulers 함수를 제작해 줄여봄.
    private val statusApi: StatusApi by lazy { StatusApi.createDrugStoreRetrofit() }
    private val maskApi: MaskApi by lazy { MaskApi.createMaskRetrofit() }
    private val hospitalApi: HospitalApi by lazy {HospitalApi.createHospitalRetrofit()}

    override fun getAccumulateData(): Single<AccumulateCoronaData> {
        return setSchedulers(statusApi.getAccumulateData())
    }

    override fun getCitiesData(): Single<CityCoronaData> {
        return setSchedulers(statusApi.getCitiesData())
    }

    override fun getAroundMaskData(lat: Double, lng: Double): Single<StoresList> {
        return setSchedulers(maskApi.getAroundMaskData(lat, lng, 3000))
    }

    override fun getClinicData(lat: Double, lng: Double): Single<HospitalClinicList> {
        return setSchedulers(hospitalApi.getClinicData(lat, lng))
    }

    override fun getHospitalData(lat: Double, lng: Double): Single<HospitalClinicList> {
        return setSchedulers(hospitalApi.getHospitalData(lat, lng))
    }

    private fun <T : Any> setSchedulers(single: Single<T>): Single<T> {
        return single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}