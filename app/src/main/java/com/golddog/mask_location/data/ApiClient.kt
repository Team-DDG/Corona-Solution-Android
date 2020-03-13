package com.golddog.mask_location.data

import com.golddog.mask_location.data.api.StatusApi
import com.golddog.mask_location.data.datasource.MaskDataSource
import com.golddog.mask_location.data.datasource.StatusDataSource
import com.golddog.mask_location.entity.CoronaStatus
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiClient: MaskDataSource, StatusDataSource {
    override fun getAccumulateData(): Single<CoronaStatus> {
        return StatusApi.createDrugStoreRetrofit()
            .getAccumulateData("bc331b7685e857f31988e727b5dee3e495aa45ca8e92d8907ebb270b68a8ec42")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}