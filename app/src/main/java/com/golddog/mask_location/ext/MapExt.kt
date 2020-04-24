package com.golddog.mask_location.ext

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