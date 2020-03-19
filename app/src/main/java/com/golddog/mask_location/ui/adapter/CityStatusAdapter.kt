package com.golddog.mask_location.ui.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseApplication
import com.golddog.mask_location.entity.CityStatus
import kotlinx.android.synthetic.main.activity_corona_status.view.*


class CityStatusAdapter: RecyclerView.Adapter<CityStatusAdapter.BindingViewHolder>() {
    private var statusList: List<CityStatus> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city_status, parent, false)
        return BindingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return statusList.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.bind(statusList[position], position)
    }

    fun setItems(items: ArrayList<CityStatus>){
        statusList = items
        notifyDataSetChanged()
    }

    class BindingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val container = view.findViewById<ConstraintLayout>(R.id.cv_container_item)
        private val city_name = view.findViewById<TextView>(R.id.tv_city_corona_status)
        private val city_accomulate = view.findViewById<TextView>(R.id.tv_accumulate_corona_status)
        private val city_dead = view.findViewById<TextView>(R.id.tv_dead_corona_status)

        fun bind(cityStatus: CityStatus, position: Int){
            var backgroundColor =
                if ((position % 2) == 0) ContextCompat.getColor(BaseApplication.appContext!!, R.color.colorSecondaryLight)
                else ContextCompat.getColor(BaseApplication.appContext!!, R.color.colorSecondaryDark)
            var textColor =
                if ((position % 2) == 0) ContextCompat.getColor(BaseApplication.appContext!!, R.color.black)
                else ContextCompat.getColor(BaseApplication.appContext!!, R.color.colorSecondaryLight)
            container.background = backgroundColor.toDrawable()
            city_name.setTextColor(textColor)
            city_name.text = cityStatus.sido
            city_accomulate.setTextColor(textColor)
            city_accomulate.text = cityStatus.confirmed
            city_dead.setTextColor(textColor)
            city_dead.text = cityStatus.dead
        }
    }
}