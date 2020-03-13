package com.golddog.mask_location.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.golddog.mask_location.data.ApiClient
import com.golddog.mask_location.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiClient: ApiClient) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(apiClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}