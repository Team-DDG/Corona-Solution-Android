package com.golddog.mask_location.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseApplication
import com.golddog.mask_location.databinding.ItemCitiesStatusBinding
import com.golddog.mask_location.entity.CityStatus
import com.golddog.mask_location.util.ItemBindDIffCallback


class CitiesAdapter :
    RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder<ItemCitiesStatusBinding>>() {
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
    ): CitiesViewHolder<ItemCitiesStatusBinding> =
        CitiesViewHolder(
            ItemCitiesStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: CitiesViewHolder<ItemCitiesStatusBinding>,
        position: Int
    ) {
        holder.binding().status = cityStatus[position]
    }

    override fun getItemCount(): Int {
        return cityStatus.size
    }

    inner class CitiesViewHolder<T : ViewDataBinding>(private val itemBinding: ItemCitiesStatusBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val binding: T = (DataBindingUtil.bind(itemBinding.root) as T?)!!

        private val textColor = Color.BLACK

        fun binding(): T {
            val backgroundColor =
                if ((adapterPosition % 2) == 0) ContextCompat.getColor(
                    itemView.context!!,
                    R.color.colorSecondaryLight
                )
                else ContextCompat.getColor(itemView.context!!, R.color.colorSecondaryDark)
            itemBinding.layoutList.background = backgroundColor.toDrawable()
            itemBinding.tvCityNameList.setTextColor(textColor)
            itemBinding.tvConfirmedList.setTextColor(textColor)
            itemBinding.tvDeadList.setTextColor(textColor)
            if (adapterPosition == 0) {
                itemBinding.layoutList.background = ContextCompat.getDrawable(
                    itemView.context!!,
                    R.drawable.corona_status_city_top_background
                )
                itemBinding.tvCityNameList.setTextColor(Color.WHITE)
                itemBinding.tvConfirmedList.setTextColor(Color.WHITE)
                itemBinding.tvDeadList.setTextColor(Color.WHITE)
            }
            return binding
        }
    }
}