package com.golddog.mask_location.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.golddog.mask_location.entity.CityStatus
import com.golddog.mask_location.ui.adapter.CitiesAdapter

@BindingAdapter("list_item")
fun setItems(recyclerView: RecyclerView, citiesStatus: List<CityStatus>) {
    val adapter: CitiesAdapter?
    if(recyclerView.adapter == null) {
        adapter = CitiesAdapter()
        recyclerView.adapter = adapter
    } else {
        adapter = recyclerView.adapter as CitiesAdapter?
    }
    adapter?.updateItems(citiesStatus as ArrayList<CityStatus>)
}
