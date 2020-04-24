package com.golddog.mask_location.ext

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.golddog.mask_location.R

@BindingAdapter("year", "dayOfWeekNumber")
fun TextView.setMaskPurchaseDateColor(year: LiveData<Int>, dayOfWeekNumber: Int) {
    year.value?.let {
        val purchaseNumber = it % 10 % 5
        if (purchaseNumber == dayOfWeekNumber) {
            this.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else {
            this.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
        }
    }
}