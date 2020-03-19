package com.golddog.mask_location.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.golddog.mask_location.R
import com.golddog.mask_location.data.ApiClient
import com.golddog.mask_location.databinding.ActivityCoronaStatusBinding
import com.golddog.mask_location.ui.adapter.CityStatusAdapter
import com.golddog.mask_location.viewmodel.CoronaStatusViewModel
import com.golddog.mask_location.viewmodelfactory.CoronaStatusViewModelFactory
import kotlinx.android.synthetic.main.activity_corona_status.*

class CoronaStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoronaStatusBinding
    private lateinit var viewModel: CoronaStatusViewModel
    private lateinit var viewModelFactory: CoronaStatusViewModelFactory
    private lateinit var adapter: CityStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_corona_status)
        viewModelFactory = CoronaStatusViewModelFactory(ApiClient())
        viewModel = ViewModelProvider(this, viewModelFactory).get(CoronaStatusViewModel::class.java)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        viewModel.cityStatusList.observe(this, Observer {
            adapter.setItems(ArrayList(it))
        })
    }

    private fun initAdapter(){
        adapter = CityStatusAdapter()
        rv_city_status_corona_status.adapter = adapter
        rv_city_status_corona_status.layoutManager = LinearLayoutManager(this)
    }
}