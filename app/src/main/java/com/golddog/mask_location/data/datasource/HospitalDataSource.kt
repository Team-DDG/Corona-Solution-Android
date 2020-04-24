package com.golddog.mask_location.data.datasource

import com.golddog.mask_location.entity.HospitalClinicList
import io.reactivex.Single

interface HospitalDataSource {
    fun getClinicData(lat: Double, lng: Double): Single<HospitalClinicList>

    fun getHospitalData(lat: Double, lng: Double) : Single<HospitalClinicList>
}