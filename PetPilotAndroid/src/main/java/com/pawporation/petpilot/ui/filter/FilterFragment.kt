package com.pawporation.petpilot.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.ui.explore.ExploreFragment
import com.pawporation.petpilot.models.MarkerType


class FilterFragment : ExploreFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val outdoorsImgButton = view.findViewById(R.id.filter_outdoors) as ImageButton
        outdoorsImgButton.setOnClickListener(clicks)

        val storesImgButton = view.findViewById(R.id.filter_stores) as ImageButton
        storesImgButton.setOnClickListener(clicks)

        val restaurantsImgButton = view.findViewById(R.id.filter_restaurants) as ImageButton
        restaurantsImgButton.setOnClickListener(clicks)

        val clinicsImgButton = view.findViewById(R.id.filter_clinics) as ImageButton
        clinicsImgButton.setOnClickListener(clicks)

        val eventsImgButton = view.findViewById(R.id.filter_events) as ImageButton
        eventsImgButton.setOnClickListener(clicks)
    }

    private var clicks: View.OnClickListener = View.OnClickListener { view ->
        val tag = when(view.id) {
            R.id.filter_outdoors -> MarkerType.OUTDOOR
            R.id.filter_stores -> MarkerType.STORE
            R.id.filter_restaurants -> MarkerType.RESTAURANT
            R.id.filter_clinics -> MarkerType.CLINIC
            else -> MarkerType.EVENT
        }

        placesList.forEach {
            if (tag == it!!.tag) it.isVisible = !it.isVisible
        }
    }
}