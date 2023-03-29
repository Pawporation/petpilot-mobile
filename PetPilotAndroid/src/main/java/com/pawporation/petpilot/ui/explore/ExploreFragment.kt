package com.pawporation.petpilot.ui.explore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textview.MaterialTextView
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.models.MarkerType
import com.pawporation.petpilot.models.PawDataModel
import com.pawporation.petpilot.models.PawRating
import com.pawporation.petpilot.ui.details.CardDataAdapter
import com.pawporation.petpilot.ui.filter.FilterFragment
import com.pawporation.petpilot.ui.map.MapFragment
import com.pawporation.petpilot.ui.search.SearchFragment

open class ExploreFragment : Fragment() {

    private val dataList: ArrayList<PawDataModel> = ArrayList()
    private val indexToMarkerMapping = HashMap<Int, Marker?>()
    private val markerToIndexMapping = HashMap<Marker?, Int>()
    private val markerToPawDataMapping = HashMap<Marker?, PawDataModel>()
    private val uniqueIdToMarkerMapping = HashMap<String, Marker?>()
    private val markerToUniqueIdMapping = HashMap<Marker?, String>()
    private lateinit var cardDataAdapter: CardDataAdapter

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
        dataList.add(PawDataModel("1", LatLng(37.414728, -122.0811),
            MarkerType.RESTAURANT, "Cuddle Cafe", PawRating.FOUR_PAW, com.pawpals.petpilot.R.mipmap.cuddle_cafe))
        dataList.add(PawDataModel("2", LatLng(37.416856, -122.089430),
            MarkerType.OUTDOOR, "Sierra Vista Park", PawRating.THREE_PAW, com.pawpals.petpilot.R.mipmap.sierra_vista_park))
        dataList.add(PawDataModel("3", LatLng(37.415129, -122.077435),
            MarkerType.STORE, "CBG", PawRating.FOUR_PAW, com.pawpals.petpilot.R.mipmap.cbg))
        dataList.add(PawDataModel("4", LatLng(37.415034, -122.086125),
            MarkerType.CLINIC, "Vet Pet", PawRating.TWO_PAW, com.pawpals.petpilot.R.mipmap.vet))
        dataList.add(PawDataModel("5", LatLng(37.416884, -122.077306),
            MarkerType.EVENT, "Poodle Romp", PawRating.FOUR_PAW, com.pawpals.petpilot.R.mipmap.poodle_romp))
        dataList.add(PawDataModel("6", LatLng(37.422029, -122.081691),
            MarkerType.OUTDOOR, "Charleston Park", PawRating.ONE_PAW, com.pawpals.petpilot.R.mipmap.charleston_park))
        dataList.add(PawDataModel("7", LatLng(37.421892, -122.084804),
            MarkerType.RESTAURANT, "Be My Mate", PawRating.FOUR_PAW, com.pawpals.petpilot.R.mipmap.be_my_mate))

        // we are initializing our adapter class and passing our arraylist to it.
        cardDataAdapter = CardDataAdapter(dataList)

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        val linearLayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        cardDataRV?.layoutManager = linearLayoutManager
        cardDataRV?.adapter = cardDataAdapter
        cardDataRV?.addOnScrollListener(scrollListener)

        cardDataRV?.let {
            val bottomSheetBehavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(cardDataRV)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        insertChildFragments()
    }

    // Embeds the child fragment dynamically
    open fun insertChildFragments() {
        val mapFragment: Fragment = MapFragment(
            dataList, indexToMarkerMapping, markerToIndexMapping,
            markerToPawDataMapping, uniqueIdToMarkerMapping, markerToUniqueIdMapping)
        val filterFragment: Fragment = FilterFragment(
            markerToIndexMapping, markerToPawDataMapping, cardDataAdapter)
        val searchFragment: Fragment = SearchFragment()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.explore_fragment_map, mapFragment)
        transaction.replace(R.id.explore_fragment_filter, filterFragment)
        transaction.replace(R.id.explore_fragment_search, searchFragment).commit()
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val lastCompletelyVisible = layoutManager.findLastCompletelyVisibleItemPosition()
            val lastVisible = layoutManager.findLastVisibleItemPosition()
            val indexValue = if (lastCompletelyVisible == -1) lastVisible else lastCompletelyVisible
            val itemTag = recyclerView.findViewHolderForAdapterPosition(indexValue)?.itemView?.tag
            // Highlight the marker
            val marker = uniqueIdToMarkerMapping[itemTag]
            marker?.showInfoWindow()
        }
    }

    private var selection: View.OnClickListener = View.OnClickListener { view ->
        val selectAll = resources.getString(R.string.select_all)
        val deselectAll = resources.getString(R.string.deselect_all)
        val textView = view as MaterialTextView
        val currText = textView.text

        markerToIndexMapping.forEach { entry ->
            if (currText == selectAll) {
                if (!entry.key!!.isVisible) {
                    entry.key!!.isVisible = true
                    markerToPawDataMapping[entry.key]?.let {
                        cardDataAdapter.pawDataModelArrayList.add(
                            it
                        )
                    }
                }

            } else {
                if (entry.key!!.isVisible) {
                    entry.key!!.isVisible = false
                    cardDataAdapter.pawDataModelArrayList.remove(markerToPawDataMapping[entry.key])
                }
            }
        }

        cardDataAdapter.run {
            notifyDataSetChanged()
        }

        textView.text = when (currText) {
            selectAll -> deselectAll
            deselectAll -> selectAll
            else -> {
                ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataList.clear()
    }
}