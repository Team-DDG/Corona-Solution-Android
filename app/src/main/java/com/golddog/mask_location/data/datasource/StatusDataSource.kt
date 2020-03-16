package com.golddog.mask_location.data.datasource

import com.golddog.mask_location.entity.CoronaList
import io.reactivex.Single

interface StatusDataSource {
    fun getCoronaStatusData(locale: String?): Single<CoronaList>
}