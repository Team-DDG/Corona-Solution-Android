package com.golddog.mask_location.data

import com.golddog.mask_location.data.api.MaskApi
import com.golddog.mask_location.data.api.StatusApi
import com.golddog.mask_location.data.datasource.MaskDataSource
import com.golddog.mask_location.data.datasource.StatusDataSource
import com.golddog.mask_location.entity.CoronaList
import com.golddog.mask_location.entity.StoresList
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiClient : StatusDataSource, MaskDataSource {
    private val statusApi: StatusApi by lazy { StatusApi.createDrugStoreRetrofit() }
    private val maskApi: MaskApi by lazy { MaskApi.createMaskRetrofit() }

    override fun getCoronaStatusData(): Single<CoronaList> {
        return statusApi.getAccumulateData("synthesize")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * TODO : 여기서 추가적으로 서버통신을 할텐데 , 위와 같이 개발 시 ApiClient 가 비대해질 염려가 있음.
     *      따라서 개발할 때, 위 getCoronaStatusData() 를 개량해서 사용하는 것을 추천
     *      locale 을 parameter 로 받아서 등록해서 개발해 보는 것이 좋을 것 같음.
     */

    override fun getAroundMaskData(lat: Int, lng: Int, meter: Int): Single<StoresList> {
        return maskApi.getAroundMaskData(lat, lng, meter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}