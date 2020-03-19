package com.golddog.mask_location.data

import com.golddog.mask_location.data.api.MaskApi
import com.golddog.mask_location.data.api.StatusApi
import com.golddog.mask_location.data.datasource.MaskDataSource
import com.golddog.mask_location.data.datasource.StatusDataSource
import com.golddog.mask_location.entity.AccumulateCoronaData
import com.golddog.mask_location.entity.CityCoronaData
import com.golddog.mask_location.entity.StoresList
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiClient : StatusDataSource, MaskDataSource {
    private val statusApi: StatusApi by lazy { StatusApi.createDrugStoreRetrofit() }
    private val maskApi: MaskApi by lazy { MaskApi.createMaskRetrofit() }

    override fun getAccumulateData(): Single<AccumulateCoronaData> {
        return statusApi.getAccumulateData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSidoData(): Single<CityCoronaData> {
        return statusApi.getSidoData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAroundMaskData(lat: Double, lng: Double, meter: Int): Single<StoresList> {
        return maskApi.getAroundMaskData(lat, lng, meter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}