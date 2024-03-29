package com.pawporation.petpilot.models

import com.google.android.gms.maps.model.LatLng

data class PawDataModel(val uniqueID: String, val location: LatLng,
                        val type: MarkerType, val title: String,
                        val pawRating: PawRating, val imgSrc: Int) {

    fun getInfo() : String {
        return title
    }
}