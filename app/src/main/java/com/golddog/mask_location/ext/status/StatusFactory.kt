package com.golddog.mask_location.ext.status

import android.content.Context
import android.text.SpannableString
import com.golddog.mask_location.entity.StoreSales
import com.naver.maps.map.overlay.Marker

class StatusFactory {
    companion object{
        fun getStatus(
            statusAbstractFactory: StatusAbstractFactory,
            context: Context,
            marker: Marker,
            storeSales: StoreSales
        ): SpannableString{
            return statusAbstractFactory.getSettingForStatus(context, marker, storeSales)
        }
    }
}