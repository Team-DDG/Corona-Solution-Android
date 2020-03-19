package com.golddog.mask_location.entity

data class CoronaResult(
	val cured: BaseCoronaData = BaseCoronaData(),
	val curing: BaseCoronaData = BaseCoronaData(),
	val dead: BaseCoronaData = BaseCoronaData(),
	val confirmed: BaseCoronaData = BaseCoronaData()
)
