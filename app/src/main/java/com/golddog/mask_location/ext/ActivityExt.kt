package com.golddog.mask_location.ext

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun Activity.showToast(text: String) {
    Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT).show()
}

fun Activity.showToast(text: Int) {
    Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT).show()
}

inline fun <reified T: AppCompatActivity> Activity.startActivity() =
    startActivity(Intent(this, T::class.java))