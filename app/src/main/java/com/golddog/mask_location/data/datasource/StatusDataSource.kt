package com.golddog.mask_location.data.datasource

import com.golddog.mask_location.entity.AccumulateCoronaData
import com.golddog.mask_location.entity.CityCoronaData
import io.reactivex.Single

interface StatusDataSource {
    fun getAccumulateData(): Single<AccumulateCoronaData>

    fun getSidoData(): Single<CityCoronaData>
}