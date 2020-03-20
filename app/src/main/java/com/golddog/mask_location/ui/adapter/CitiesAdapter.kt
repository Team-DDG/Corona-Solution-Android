package com.golddog.mask_location.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.golddog.mask_location.R
import com.golddog.mask_location.databinding.ItemCitiesStatusBinding
import com.golddog.mask_location.entity.CityStatus
import com.golddog.mask_location.util.ItemBindDIffCallback


class CitiesAdapter: RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder<ItemCitiesStatusBinding>>() {
    private val cityStatus: ArrayList<CityStatus> = ArrayList()

    fun updateItems(cityStatus: ArrayList<CityStatus>?) {
        val callback = cityStatus?.let { ItemBindDIffCallback(this.cityStatus, it) }
        val result = callback?.let { DiffUtil.calculateDiff(it) }
        this.cityStatus.clear()
        cityStatus?.let { this.cityStatus.addAll(it) }
        result?.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CitiesViewHolder<ItemCitiesStatusBinding> {
        val inflater = LayoutInflater.from(parent.context)
        return CitiesViewHolder(inflater.inflate(R.layout.item_cities_status, parent, false))
    }

    override fun onBindViewHolder(
        holder: CitiesViewHolder<ItemCitiesStatusBinding>,
        position: Int
    ) {
        holder.binding().status = cityStatus[position]
    }

    override fun getItemCount(): Int {
        return cityStatus.size
    }

    inner class CitiesViewHolder<T: ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: T = (DataBindingUtil.bind(itemView) as T?)!!
        fun binding(): T {
            return binding
        }
    }
}