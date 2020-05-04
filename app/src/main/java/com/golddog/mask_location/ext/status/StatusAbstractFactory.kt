package com.golddog.mask_location.ext.status

import android.content.Context
import android.text.SpannableString
import com.golddog.mask_location.entity.StoreSales
import com.golddog.mask_location.ext.bold
import com.golddog.mask_location.ext.color
import com.golddog.mask_location.ext.sizeUp
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

interface StatusAbstractFactory {
    fun getSettingForStatus(context: Context, marker: Marker, storeSales: StoreSales) : SpannableString

    fun setMarkerImage(overlayImage: OverlayImage, marker: Marker) {
        marker.icon = overlayImage
    }

    fun setStoreMarkerTag(
        storeSales: StoreSales,
        status: String,
        colorCode: Int
    ): SpannableString {
        val storeName = "${storeSales.name}\n"
        val tagString =
            "${storeSales.name}\n${storeSales.address}\n${status}\n" +
                    "입고시간 : ${storeSales.stockAt}\n갱신시간 : ${storeSales.createdAt}"
        val storeNameStart = 0
        val storeNameEnd = storeName.length
        val statusStart = tagString.indexOf(status)
        val statusEnd = statusStart + status.length

        return SpannableString(tagString)
            .bold(storeNameStart, storeNameEnd)
            .sizeUp(storeNameStart, storeNameEnd)
            .color(colorCode, statusStart, statusEnd)
    }
}