package com.golddog.mask_location.entity

import com.google.gson.annotations.SerializedName

data class CoronaStatus(

	@SerializedName("announced_timestamp")
	val announcedTimestamp: String? = null,

	@SerializedName("increased")
	val increased: String? = null,

	@SerializedName("percentage")
	val percentage: String? = null,

	@SerializedName("certified")
	val certified: String? = null,

	@SerializedName("dead")
	val dead: String? = null,

	@SerializedName("check")
	val check: String? = null,

	@SerializedName("timestamp")
	val timestamp: String? = null
)