package com.pawporation.petpilot.ui.explore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.Marker
import com.google.android.material.textview.MaterialTextView
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.models.MarkerType
import com.pawporation.petpilot.models.PawRating
import com.pawporation.petpilot.ui.details.CardDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pawporation.petpilot.models.PawDataModel

open class ExploreFragment : Fragment() {

    protected val dataList: ArrayList<PawDataModel> = ArrayList()
    protected var placesMap = mutableMapOf<Marker?, Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.setShowHideAnimationEnabled(false)
        supportActionBar?.hide()

        val textView = view.findViewById<MaterialTextView>(R.id.explore_selection)
        textView?.setOnClickListener(selection)

        val cardDataRV = view.findViewById<RecyclerView>(R.id.card_rv)

        // Here, we have created new array list and added data to it
        dataList.add(PawDataModel(LatLng(37.414728, -122.0811),
            MarkerType.RESTAURANT, "Cuddle Cafe", PawRating.FOUR_PAW, ""))
        dataList.add(PawDataModel(LatLng(37.416856, -122.089430),
            MarkerType.OUTDOOR, "Sierra Vista Park", PawRating.THREE_PAW, ""))
        dataList.add(PawDataModel(LatLng(37.415129, -122.077435),
            MarkerType.STORE, "CBG", PawRating.FOUR_PAW, ""))
        dataList.add(PawDataModel(LatLng(37.415034, -122.086125),
            MarkerType.CLINIC, "Vet Pet", PawRating.TWO_PAW, ""))
        dataList.add(PawDataModel(LatLng(37.416884, -122.077306),
            MarkerType.EVENT, "Poodle Romp", PawRating.FOUR_PAW, ""))
        dataList.add(PawDataModel(LatLng(37.422029, -122.081691),
            MarkerType.OUTDOOR, "Charleston Park", PawRating.ONE_PAW, ""))
        dataList.add(PawDataModel(LatLng(37.421892, -122.084804),
            MarkerType.RESTAURANT, "Be My Mate", PawRating.FOUR_PAW, ""))

        // we are initializing our adapter class and passing our arraylist to it.
        val cardDataAdapter = CardDataAdapter(dataList)

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        val linearLayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        cardDataRV?.layoutManager = linearLayoutManager
        cardDataRV?.adapter = cardDataAdapter

        cardDataRV?.let {
            val bottomSheetBehavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(cardDataRV)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private var selection: View.OnClickListener = View.OnClickListener { view ->
        val selectAll = resources.getString(R.string.select_all)
        val deselectAll = resources.getString(R.string.deselect_all)
        val textView = view as MaterialTextView
        val currText = textView.text

        placesMap.forEach { entry ->
            entry.key!!.isVisible = currText == selectAll
        }

        textView.text = when (currText) {
            selectAll -> deselectAll
            deselectAll -> selectAll
            else -> {
                ""
            }
        }
    }
}