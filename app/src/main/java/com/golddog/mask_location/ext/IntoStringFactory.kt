package com.golddog.mask_location.ext

import android.content.Context
import android.text.SpannableString
import androidx.core.content.ContextCompat
import com.golddog.mask_location.R
import com.golddog.mask_location.entity.StoreSales
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class IntoStringFactory {
    companion object{
        fun createInfoString(
            status: String,
            context: Context,
            marker: Marker,
            storeSales: StoreSales
        ): SpannableString{
            if (status == "plenty") {
                setMarkerImage(
                    OverlayImage.fromResource(
                        R.drawable.marker_plenty
                    ), marker
                )
                return setStoreMarkerTag(
                    storeSales,
                    context.getString(R.string.plenty_status),
                    ContextCompat.getColor(context, R.color.marker_plenty)
                )
            } else if (status == "some") {
                setMarkerImage(
                    OverlayImage.fromResource(
                        R.drawable.marker_some
                    ), marker
                )
                return setStoreMarkerTag(
                    storeSales,
                    context.getString(R.string.some_status),
                    ContextCompat.getColor(context, R.color.marker_some)
                )
            } else if (status == "few") {
                setMarkerImage(
                    OverlayImage.fromResource(
                        R.drawable.marker_few
                    ), marker
                )
                return setStoreMarkerTag(
                    storeSales,
                    context.getString(R.string.few_status),
                    ContextCompat.getColor(context, R.color.marker_few)
                )
            } else if (status == "empty") {
                setMarkerImage(
                    OverlayImage.fromResource(
                        R.drawable.marker_empty
                    ), marker
                )
                return setStoreMarkerTag(
                    storeSales,
                    context.getString(R.string.empty_status),
                    ContextCompat.getColor(context, R.color.marker_none)
                )
            } else if (status == "break") {
                setMarkerImage(
                    OverlayImage.fromResource(
                        R.drawable.marker_break
                    ), marker
                )
                return setStoreMarkerTag(
                    storeSales,
                    context.getString(R.string.break_status),
                    ContextCompat.getColor(context, R.color.marker_none)
                )
            } else {
                marker.map = null
            }
            return SpannableString("")
        }
    }
}

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