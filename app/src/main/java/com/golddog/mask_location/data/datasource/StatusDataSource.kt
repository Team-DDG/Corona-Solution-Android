package com.golddog.mask_location.data.datasource

import com.golddog.mask_location.entity.CoronaStatus
import io.reactivex.Single

interface StatusDataSource {
    fun getAccumulateData(): Single<CoronaStatus>
}