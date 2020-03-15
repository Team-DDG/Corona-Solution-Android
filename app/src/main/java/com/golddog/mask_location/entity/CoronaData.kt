package com.golddog.mask_location.entity

import com.google.gson.annotations.SerializedName

data class CoronaData(
	@SerializedName("increased")
	var increased: Int,

	@SerializedName("dead")
	var dead: Int,

	@SerializedName("certified")
	var certified: Int,

	@SerializedName("deisolated")
	var cure: Int
) {
	override fun toString(): String {
		return "increased : $increased dead : $dead certified : $certified dead : $dead"
	}
}