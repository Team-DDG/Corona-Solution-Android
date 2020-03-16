package com.golddog.mask_location.ext

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.databinding.BindingAdapter
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseApplication
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

@BindingAdapter("setTextColor")
fun setTextColor(textView: TextView, year: Int) {
    val context = BaseApplication.appContext!!
    Log.d("binding", year.toString())
    when(year){
        1 -> if(textView.text == "월") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
        6 -> if(textView.text == "월") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
        2 -> if(textView.text == "화") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
        7 -> if(textView.text == "화") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
        3 -> if(textView.text == "수") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
        8 -> if(textView.text == "수") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
        4 -> if(textView.text == "목") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
        9 -> if(textView.text == "목") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
        5 -> if(textView.text == "금") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
        0 -> if(textView.text == "금") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
        } else textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
    }
}