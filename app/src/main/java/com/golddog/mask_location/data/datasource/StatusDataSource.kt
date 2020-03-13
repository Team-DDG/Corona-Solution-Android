package com.golddog.mask_location.data.datasource

import com.golddog.mask_location.entity.CoronaList
import io.reactivex.Flowable

interface StatusDataSource {
    fun getAccumulateData(): Flowable<CoronaList>
}