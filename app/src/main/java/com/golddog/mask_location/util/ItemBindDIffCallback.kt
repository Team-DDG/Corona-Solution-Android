package com.golddog.mask_location.util

import androidx.recyclerview.widget.DiffUtil
import com.golddog.mask_location.entity.CityStatus

class ItemBindDIffCallback(
    private val oldData: ArrayList<CityStatus>,
    private val newData: ArrayList<CityStatus>
): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }

    override fun getOldListSize() = oldData.size

    override fun getNewListSize() = newData.size


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }
}