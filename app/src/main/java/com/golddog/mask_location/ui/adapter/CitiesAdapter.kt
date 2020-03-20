package com.golddog.mask_location.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
        holder.binding(position).status = cityStatus[position]
    }

    override fun getItemCount(): Int {
        return cityStatus.size
    }

    inner class CitiesViewHolder<T: ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: T = (DataBindingUtil.bind(itemView) as T?)!!

        private val container = itemView.findViewById<ConstraintLayout>(R.id.layout_list)
        private val city_name = itemView.findViewById<TextView>(R.id.tv_city_name_list)
        private val city_accomulate = itemView.findViewById<TextView>(R.id.tv_confirmed_list)
        private val city_dead = itemView.findViewById<TextView>(R.id.tv_dead_list)
        private val textColor = Color.BLACK

        fun binding(position: Int): T {
            val backgroundColor =
                if ((position % 2) == 0) ContextCompat.getColor(BaseApplication.appContext!!, R.color.colorSecondaryLight)
                else ContextCompat.getColor(BaseApplication.appContext!!, R.color.colorSecondaryDark)
            container.background = backgroundColor.toDrawable()
            city_name.setTextColor(textColor)
            city_accomulate.setTextColor(textColor)
            city_dead.setTextColor(textColor)
            if (position == 0){
                container.background = ContextCompat.getDrawable(BaseApplication.appContext!!, R.drawable.corona_status_city_top_background)
                city_name.setTextColor(Color.WHITE)
                city_accomulate.setTextColor(Color.WHITE)
                city_dead.setTextColor(Color.WHITE)
            }
            return binding
        }
    }
}