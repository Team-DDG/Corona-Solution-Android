package com.golddog.mask_location.entity

import com.google.gson.annotations.SerializedName

data class StoresByGeo(

	@SerializedName("stores")
	val stores: List<StoresItem?>? = null,

	@SerializedName("count")
	val count: Int? = null
)