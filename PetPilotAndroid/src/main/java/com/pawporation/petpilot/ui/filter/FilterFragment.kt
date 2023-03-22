package com.pawporation.petpilot.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.Marker
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.models.MarkerType
import com.pawporation.petpilot.models.PawDataModel
import com.pawporation.petpilot.ui.details.CardDataAdapter


class FilterFragment(private val markerToIndexMapping: HashMap<Marker?, Int>,
                     private val markerToPawDataMapping: HashMap<Marker?, PawDataModel>,
                     private val cardDataAdapter: CardDataAdapter) : Fragment() {

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

        markerToIndexMapping.forEach { entry ->
            if (tag == entry.key!!.tag) {
                if (entry.key!!.isVisible) {
                    cardDataAdapter.pawDataModelArrayList.remove(markerToPawDataMapping[entry.key])
                } else {
                    markerToPawDataMapping[entry.key]?.let {
                        cardDataAdapter.pawDataModelArrayList.add(entry.value,
                            it
                        )
                    }
                }
                entry.key!!.isVisible = !entry.key!!.isVisible
            }
        }

        cardDataAdapter.run {
            notifyDataSetChanged()
        }

    }
}