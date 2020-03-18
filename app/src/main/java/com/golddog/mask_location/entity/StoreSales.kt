package com.golddog.mask_location.entity

import com.google.gson.annotations.SerializedName

data class StoreSales(
	val code: String,
	val lat: Double,
	val lng: Double,
	@SerializedName("stock_at")
	val stockAt: String,
	val name: String,
	@SerializedName("created_at")
	val createdAt: String,
	@SerializedName("addr")
	val address: String,
	val type: String,
	@SerializedName("remain_stat")
	val remainStat: String
) {
	override fun toString(): String {
		return "[$code : $name($lat, $lng), $remainStat] "
	}
}
