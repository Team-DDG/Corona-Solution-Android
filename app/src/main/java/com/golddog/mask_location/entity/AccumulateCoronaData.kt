package com.golddog.mask_location.entity

data class AccumulateCoronaData(
	val baseDate: String,
	val cured: CoronaStatus,
	val curing: CoronaStatus,
	val dead: CoronaStatus,
	val confirmed: CoronaStatus
)
