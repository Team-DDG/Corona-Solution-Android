package com.golddog.mask_location.data.local

import android.content.Context

class SharedPreference(context: Context){
    private val preference = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    companion object {

        private var INSTANCE: SharedPreference? = null

        fun getInstance(context: Context): SharedPreference? {
            if (INSTANCE == null) {
                INSTANCE =
                    SharedPreference(context)
            }
            return INSTANCE
        }
    }

    fun setAgreement(agreement: Boolean) {
        preference.edit().putBoolean("Agreement", agreement).apply()
    }

    fun getAgreement(): Boolean{
        return preference.getBoolean("Agreement", false)
    }
}