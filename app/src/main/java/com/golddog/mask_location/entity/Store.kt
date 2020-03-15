package com.golddog.mask_location.entity

import com.google.gson.annotations.SerializedName

data class Store(

	@SerializedName("code")
	val code: String? = null,

	@SerializedName("lng")
	val lng: Int? = null,

	@SerializedName("stock_at")
	val stockAt: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("created_at")
	val createdAt: String? = null,

	@SerializedName("addr")
	val addr: String? = null,

	@SerializedName("type")
	val type: String? = null,

	@SerializedName("remain_stat")
	val remainStat: String? = null,

	@SerializedName("lat")
	val lat: Int? = null
)