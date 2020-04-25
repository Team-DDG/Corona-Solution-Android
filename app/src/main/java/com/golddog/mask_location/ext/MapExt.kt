package com.golddog.mask_location.ext

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
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

fun setHospitalClinicMarkerTag(
    hospitalClinic: HospitalClinic,
    isClinic: Boolean
): SpannableString {
    val storeName = hospitalClinic.name
    val hospitalOrClinic = if (isClinic) "선별진료소" else "국민안심병원"
    val tagString = "$storeName ($hospitalOrClinic)\n" +
            "${hospitalClinic.address}\n${hospitalClinic.phone}"
    val firstLineStart = 0
    val firstLineEnd = storeName.length + 3 + hospitalOrClinic.length

    return SpannableString(tagString)
        .bold(firstLineStart, firstLineEnd)
        .sizeUp(firstLineStart, firstLineEnd)
}

fun setStoreMarker(
    storeSales: StoreSales,
    infoBottomSheet: InfoBottomSheet,
    context: Context
): Marker {
    val marker = Marker()
    val status = storeSales.remainStat
    var tag = SpannableString("")
    marker.position = LatLng(storeSales.lat, storeSales.lng)

    if (status == "plenty") {
        setMarkerImage(OverlayImage.fromResource(R.drawable.marker_plenty), marker)
        tag = setStoreMarkerTag(
            storeSales,
            context.getString(R.string.plenty_status),
            ContextCompat.getColor(context, R.color.marker_plenty)
        )
    } else if (status == "some") {
        setMarkerImage(OverlayImage.fromResource(R.drawable.marker_some), marker)
        tag = setStoreMarkerTag(
            storeSales,
            context.getString(R.string.some_status),
            ContextCompat.getColor(context, R.color.marker_some)
        )
    } else if (status == "few") {
        setMarkerImage(OverlayImage.fromResource(R.drawable.marker_few), marker)
        tag = setStoreMarkerTag(
            storeSales,
            context.getString(R.string.few_status),
            ContextCompat.getColor(context, R.color.marker_few)
        )
    } else if (status == "empty") {
        setMarkerImage(OverlayImage.fromResource(R.drawable.marker_empty), marker)
        tag = setStoreMarkerTag(
            storeSales,
            context.getString(R.string.empty_status),
            ContextCompat.getColor(context, R.color.marker_none)
        )
    } else if (status == "break") {
        setMarkerImage(OverlayImage.fromResource(R.drawable.marker_break), marker)
        tag = setStoreMarkerTag(
            storeSales,
            context.getString(R.string.break_status),
            ContextCompat.getColor(context, R.color.marker_none)
        )
    } else {
        marker.map = null
    }

    marker.setOnClickListener {
        infoBottomSheet.setInfo(tag)
        infoBottomSheet.show((context as FragmentActivity).supportFragmentManager, "infoWindow")
        true
    }

    return marker
}


fun setClinicMarker(
    clinic: HospitalClinic,
    naverMap: NaverMap,
    infoBottomSheet: InfoBottomSheet,
    context: Context
): Marker {
    val marker = Marker()
    marker.position = LatLng(clinic.lat.toDouble(), clinic.lng.toDouble())
    setMarkerVisible(listOf(marker), naverMap)
    marker.iconTintColor = ContextCompat.getColor(context, R.color.marker_clinic)
    marker.setOnClickListener {
        infoBottomSheet.setInfo(setHospitalClinicMarkerTag(clinic, true))
        infoBottomSheet.show((context as FragmentActivity).supportFragmentManager, "infoWindow")
        true
    }

    return marker
}

fun setHospitalMarker(
    hospital: HospitalClinic,
    naverMap: NaverMap,
    infoBottomSheet: InfoBottomSheet,
    context: Context
): Marker {
    val marker = Marker()
    marker.position = LatLng(hospital.lat.toDouble(), hospital.lng.toDouble())
    setMarkerVisible(listOf(marker), naverMap)
    marker.iconTintColor = ContextCompat.getColor(context, R.color.marker_hospital)
    marker.setOnClickListener {
        infoBottomSheet.setInfo(setHospitalClinicMarkerTag(hospital, false))
        infoBottomSheet.show((context as FragmentActivity).supportFragmentManager, "infoWindow")
        true
    }

    return marker
}