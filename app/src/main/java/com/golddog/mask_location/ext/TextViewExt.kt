package com.golddog.mask_location.ext

import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.databinding.BindingAdapter
import java.text.NumberFormat
import java.util.*

@BindingAdapter("numberText")
fun setTextWithNumberFormat(textView: TextView, numberData: String) {
    if (numberData != "" && numberData.isDigitsOnly()) {
        val data = NumberFormat.getInstance(Locale.getDefault()).format(numberData.toInt())
        textView.text = data
    }
}

@BindingAdapter("numberText")
fun setTextWithNumberFormat(textView: TextView, numberData: Int) {
    val data = NumberFormat.getInstance(Locale.getDefault()).format(numberData)
    textView.text = data
}

@BindingAdapter("increaseNumberText")
fun setTextWithIncreaseNumberFormat(textView: TextView, numberData: Int) {
    val data = NumberFormat.getInstance(Locale.getDefault()).format(numberData.toInt())
    textView.text = "( + $data )"
}