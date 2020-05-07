package com.golddog.mask_location.ext

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan

fun SpannableString.bold(start: Int, end: Int): SpannableString {
    this.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun SpannableString.sizeUp(start: Int, end: Int): SpannableString {
    this.setSpan(RelativeSizeSpan(1.35f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun SpannableString.color(colorCode: Int, start: Int, end: Int): SpannableString {
    this.setSpan(ForegroundColorSpan(colorCode), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}