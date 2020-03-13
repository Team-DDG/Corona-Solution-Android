package com.golddog.mask_location.entity

import com.google.gson.annotations.SerializedName

data class CoronaData(
	@SerializedName("increased")
	var increased: Int = -1,

	@SerializedName("percentage")
	val dead: Int = -1,

	@SerializedName("certified")
	val certified: Int = -1,

	@SerializedName("dead")
	val cure: Int = -1
) {
	override fun toString(): String {
		return "increased : $increased dead : $dead certified : $certified dead : $dead"
	}
}