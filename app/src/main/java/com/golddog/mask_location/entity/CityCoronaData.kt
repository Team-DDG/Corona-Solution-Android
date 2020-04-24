package com.golddog.mask_location.entity

import com.google.gson.annotations.SerializedName

data class CityCoronaData(
	var baseDate: String,
	@SerializedName("result")
	var cityStatuses: MutableList<CityStatus>
)
