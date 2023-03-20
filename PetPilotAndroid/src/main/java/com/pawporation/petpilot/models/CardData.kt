package com.pawporation.petpilot.models

data class CardData(private val markerType: MarkerType,
                    private val name: String,
                    private val pawRating: PawRating) {

    fun getName() : String {
        return name
    }


}
