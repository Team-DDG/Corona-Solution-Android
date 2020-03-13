package com.golddog.mask_location.data.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface DrugStoreApi {
    companion object {
        private const val baseUrl = "https://api.dropper.tech/covid19/status/korea"

        private lateinit var retrofit : Retrofit

        fun createRetrofit(): Retrofit {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
    }
}