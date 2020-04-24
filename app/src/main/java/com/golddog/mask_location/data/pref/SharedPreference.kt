package com.golddog.mask_location.data.pref

import android.content.Context

class SharedPreference(context: Context){
    private val preference = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    fun setAgreement(agreement: Boolean) {
        preference.edit().putBoolean("Agreement", agreement).apply()
    }

    fun getAgreement(): Boolean{
        return preference.getBoolean("Agreement", false)
    }
}