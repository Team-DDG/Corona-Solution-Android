package com.golddog.mask_location.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.golddog.mask_location.R
import com.golddog.mask_location.data.ApiClient
import com.golddog.mask_location.databinding.ActivityCoronaStatusBinding
import com.golddog.mask_location.viewmodel.CoronaStatusViewModel
import com.golddog.mask_location.viewmodelfactory.CoronaStatusViewModelFactory
import kotlinx.android.synthetic.main.activity_corona_status.*

class CoronaStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoronaStatusBinding
    private lateinit var viewModel: CoronaStatusViewModel
    private lateinit var viewModelFactory: CoronaStatusViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_corona_status)
        viewModelFactory = CoronaStatusViewModelFactory(ApiClient())
        viewModel = ViewModelProvider(this, viewModelFactory).get(CoronaStatusViewModel::class.java)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        btn_back_corona_status.setOnClickListener {
            finish()
        }
    }
}