package com.golddog.mask_location.data.api

import com.golddog.mask_location.entity.HospitalClinicList
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HospitalApi {
    companion object {
        private const val baseUrl = "https://www.janghoseung.com"

        fun createHospitalRetrofit(): HospitalApi {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HospitalApi::class.java)
        }
    }

    @GET("api/status/clinic")
    fun getClinicData(@Query("lat") lat: Double,
                      @Query("lng") lng: Double): Single<HospitalClinicList>

    @GET("api/status/hospital")
    fun getHospitalData(@Query("lat") lat: Double,
                        @Query("lng") lng: Double) : Single<HospitalClinicList>
}