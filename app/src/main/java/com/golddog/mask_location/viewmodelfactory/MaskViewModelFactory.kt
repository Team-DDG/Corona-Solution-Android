package com.golddog.mask_location.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.golddog.mask_location.viewmodel.MaskViewModel

class MaskViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MaskViewModel::class.java)){
            return MaskViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}