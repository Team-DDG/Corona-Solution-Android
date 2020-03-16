package com.golddog.mask_location.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golddog.mask_location.R

class MaskViewModel(private val application: Application): ViewModel(){
    var year = MutableLiveData<Int>(-1)
    var monday: MutableLiveData<Int> = MutableLiveData(ContextCompat.getColor(application, R.color.colorSecondaryDark))
    var tuesday: MutableLiveData<Int> = MutableLiveData(ContextCompat.getColor(application, R.color.colorSecondaryDark))
    var wednesday: MutableLiveData<Int> = MutableLiveData(ContextCompat.getColor(application, R.color.colorSecondaryDark))
    var thursday: MutableLiveData<Int> = MutableLiveData(ContextCompat.getColor(application, R.color.colorSecondaryDark))
    var friday: MutableLiveData<Int> = MutableLiveData(ContextCompat.getColor(application, R.color.colorSecondaryDark))
}