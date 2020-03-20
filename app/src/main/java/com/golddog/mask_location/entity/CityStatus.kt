package com.golddog.mask_location.entity

import com.google.gson.annotations.SerializedName

data class CityStatus(
	@SerializedName("sido")
	val cityName: String,
	val confirmed: String,
	val dead: String
)
