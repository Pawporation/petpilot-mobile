package com.pawporation.petpilot.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.pawporation.petpilot.models.MarkerType

object MapMarkerUtil {

    fun bitmapDescriptorFromVector(
        context: Context,
        markerType: MarkerType
    ): BitmapDescriptor? {
        val background: Drawable? =
            ContextCompat.getDrawable(context, com.pawpals.petpilot.R.drawable.pawprint_48)

        val color = when(markerType) {
            MarkerType.OUTDOOR -> context.getColor(com.pawpals.petpilot.R.color.hiking)
            MarkerType.STORE -> context.getColor(com.pawpals.petpilot.R.color.stores)
            MarkerType.CLINIC -> context.getColor(com.pawpals.petpilot.R.color.medical)
            MarkerType.RESTAURANT -> context.getColor(com.pawpals.petpilot.R.color.restaurants)
            MarkerType.EVENT -> context.getColor(com.pawpals.petpilot.R.color.events)
        }

        val vectorDrawableResourceId = when(markerType) {
            MarkerType.OUTDOOR -> com.pawpals.petpilot.R.drawable.outdoors_18
            MarkerType.STORE -> com.pawpals.petpilot.R.drawable.store_18
            MarkerType.CLINIC -> com.pawpals.petpilot.R.drawable.medical_services_18
            MarkerType.RESTAURANT -> com.pawpals.petpilot.R.drawable.restaurant_18
            MarkerType.EVENT -> com.pawpals.petpilot.R.drawable.events_18
        }

        if (background != null) {
            DrawableCompat.setTint(background, color)
        };
        background?.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
        val vectorDrawable: Drawable? = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable?.setBounds(
            40,
            65,
            vectorDrawable.intrinsicWidth + 40,
            vectorDrawable.intrinsicHeight + 65
        )

        val bitmap = background?.let {
            Bitmap.createBitmap(
                it.intrinsicWidth,
                it.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = bitmap?.let { Canvas(it) }
        if (canvas != null) {
            background.draw(canvas)
        }
        if (canvas != null) {
            vectorDrawable?.draw(canvas)
        }

        return bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }
    }

    fun constructTag(markerType: MarkerType, index: Int) : String {
        return "$markerType:$index"
    }
}