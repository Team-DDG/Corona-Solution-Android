package com.golddog.mask_location.data.datasource

import com.golddog.mask_location.entity.StoresList
import io.reactivex.Single

interface MaskDataSource {
    fun getAroundMaskData(lat: Double, lng: Double, meter: Int): Single<StoresList>
}