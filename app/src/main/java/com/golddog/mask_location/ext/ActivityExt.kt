package com.golddog.mask_location.ext

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(text: String) {
    Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT).show()
}

fun Activity.showToast(text: Int) {
    Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT).show()
}
