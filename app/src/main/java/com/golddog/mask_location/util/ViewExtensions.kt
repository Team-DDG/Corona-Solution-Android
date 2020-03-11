package com.golddog.mask_location.util

import android.widget.Toast
import com.golddog.mask_location.base.BaseApplication

fun showToast(text: String) {
    Toast.makeText(BaseApplication.appContext, text, Toast.LENGTH_SHORT).show()
}