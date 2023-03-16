package com.pawporation.petpilot.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object MapMarkerUtil {

    fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? {
        val background: Drawable? =
            ContextCompat.getDrawable(context, com.pawpals.petpilot.R.drawable.pawprint_48)
        if (background != null) {
            DrawableCompat.setTint(background, context.getColor(com.pawpals.petpilot.R.color.restaurants))
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
}