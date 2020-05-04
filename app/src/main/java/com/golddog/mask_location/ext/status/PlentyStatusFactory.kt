package com.golddog.mask_location.ext.status

import android.content.Context
import android.text.SpannableString
import androidx.core.content.ContextCompat
import com.golddog.mask_location.R
import com.golddog.mask_location.entity.StoreSales
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class PlentyStatusFactory : StatusAbstractFactory {
    override fun getSettingForStatus(
        context: Context,
        marker: Marker,
        storeSales: StoreSales
    ): SpannableString {
        setMarkerImage(OverlayImage.fromResource(R.drawable.marker_plenty), marker)
        return setStoreMarkerTag(
            storeSales,
            context.getString(R.string.plenty_status),
            ContextCompat.getColor(context, R.color.marker_plenty)
        )
    }
}