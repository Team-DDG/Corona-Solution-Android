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
    Log.d("binding", year.toString()+" "+textView.text)
    when(year){
        1, 6 -> if(textView.text == "월") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
            Log.d("binding", "inside when"+textView.text)
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
            Log.d("binding", "inside when"+textView.text)
        }
        2, 7 -> if(textView.text == "화") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
            Log.d("binding", "inside when"+textView.text)
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
            Log.d("binding", "inside when"+textView.text)
        }
        3, 8 -> if(textView.text == "수") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
            Log.d("binding", "inside when"+textView.text)
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
            Log.d("binding", "inside when"+textView.text)
        }
        4, 9 -> if(textView.text == "목") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
            Log.d("binding", "inside when"+textView.text)
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
            Log.d("binding", "inside when"+textView.text)
        }
        5, 0 -> if(textView.text == "금") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight))
            Log.d("binding", "inside when"+textView.text)
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryDark))
            Log.d("binding", "inside when"+textView.text)
        }
    }
}