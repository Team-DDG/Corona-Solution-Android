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
import com.golddog.mask_location.base.BaseApplication
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

    val spannableString = SpannableString(tagString)
    spannableString.setSpan(
        StyleSpan(Typeface.BOLD),
        storeNameStart,
        storeNameEnd,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannableString.setSpan(
        RelativeSizeSpan(1.35f),
        storeNameStart,
        storeNameEnd,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannableString.setSpan(
        ForegroundColorSpan(colorCode),
        statusStart,
        statusEnd,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
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

    val spannableString = SpannableString(tagString)
    spannableString.setSpan(
        StyleSpan(Typeface.BOLD),
        firstLineStart,
        firstLineEnd,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannableString.setSpan(
        RelativeSizeSpan(1.35f),
        firstLineStart,
        firstLineEnd,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}

fun setClinicMarker(clinic: HospitalClinic, naverMap: NaverMap, infoBottomSheet: InfoBottomSheet, context: Context): Marker {
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

fun setHospitalMarker(hospital: HospitalClinic, naverMap: NaverMap, infoBottomSheet: InfoBottomSheet, context: Context): Marker {
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