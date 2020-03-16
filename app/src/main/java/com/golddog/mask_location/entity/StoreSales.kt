package com.golddog.mask_location.entity

import com.google.gson.annotations.SerializedName

data class StoreSales(
	val code: String,
	val lat: Int,
	val lng: Int,
	val stockAt: String,
	val name: String,
	val createdAt: String,
	@SerializedName("addr")
	val address: String,
	val type: String,
	val remainStat: String
)
