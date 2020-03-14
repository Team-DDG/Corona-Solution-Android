package com.golddog.mask_location.data.datasource

import com.golddog.mask_location.entity.CoronaList
import io.reactivex.Single

interface StatusDataSource {
    fun getAccumulateData(): Single<CoronaList>
}