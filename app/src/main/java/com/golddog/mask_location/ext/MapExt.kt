package com.golddog.mask_location.ext

import android.content.Context
import android.text.SpannableString
import androidx.core.content.ContextCompat
import com.golddog.mask_location.R
import com.golddog.mask_location.entity.HospitalClinic
import com.golddog.mask_location.entity.StoreSales
import com.golddog.mask_location.ui.dialog.InfoBottomSheet
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

fun setMarkerVisible(markers: List<Marker>, naverMap: NaverMap) {
    markers.forEach {
        it.map = naverMap
    }
}

fun setMarkerInvisible(markers: List<Marker>) {
    markers.forEach {
        it.map = null
    }
}

fun setMarkerVisibility(marker: Marker, visibility: Boolean, naverMap: NaverMap) {
    if (visibility) marker.map = naverMap
    else marker.map = null
}

fun setHospitalClinicMarkerTag(
    hospitalClinic: HospitalClinic,
    isClinic: Boolean
): SpannableString {
    val storeName = hospitalClinic.name
    val hospitalOrClinic = if (isClinic) "선별진료소" else "국민안심병원"
    val tag = "$storeName ($hospitalOrClinic)\n" +
            "${hospitalClinic.address}\n${hospitalClinic.phone}"
    val firstLineStart = 0
    val firstLineEnd = storeName.length + 3 + hospitalOrClinic.length

    return SpannableString(tag)
        .bold(firstLineStart, firstLineEnd)
        .sizeUp(firstLineStart, firstLineEnd)
}

fun setStoreMarker(
    storeSales: StoreSales,
    infoBottomSheet: InfoBottomSheet,
    context: Context
): Marker {
    val marker = Marker()
    val status = storeSales.remainStat ?: ""
    var infoString = SpannableString("")
    marker.position = LatLng(storeSales.lat, storeSales.lng)

    infoString = IntoStringFactory.createInfoString(status, context, marker, storeSales)

    marker.setOnClickListener {
        infoBottomSheet.showWithInfo(context, infoString)
        true
    }

    return marker
}

fun setHospitalClinicMarker(
    hospitalClinic: HospitalClinic,
    naverMap: NaverMap,
    infoBottomSheet: InfoBottomSheet,
    context: Context,
    isClinic: Boolean
): Marker{
    val marker = Marker()
    marker.position = LatLng(hospitalClinic.lat.toDouble(), hospitalClinic.lng.toDouble())
    setMarkerVisible(listOf(marker), naverMap)
    marker.setOnClickListener {
        infoBottomSheet.showWithInfo(context, setHospitalClinicMarkerTag(hospitalClinic, isClinic))
        true
    }
    if (isClinic) marker.iconTintColor = ContextCompat.getColor(context, R.color.marker_clinic)
    else marker.iconTintColor = ContextCompat.getColor(context, R.color.marker_hospital)

    return marker
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