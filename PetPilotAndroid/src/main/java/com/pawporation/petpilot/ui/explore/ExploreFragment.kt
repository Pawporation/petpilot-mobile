package com.pawporation.petpilot.ui.explore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.Marker
import com.google.android.material.textview.MaterialTextView
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.android.databinding.FragmentExploreBinding
import com.pawporation.petpilot.models.CardData
import com.pawporation.petpilot.models.MarkerType
import com.pawporation.petpilot.models.PawRating
import com.pawporation.petpilot.ui.details.CardDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        protected var placesList = mutableListOf<Marker?>()
    }

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
        val cardDataList: ArrayList<CardData> = ArrayList<CardData>()
        cardDataList.add(CardData(MarkerType.RESTAURANT, "Cuddle Cafe",
            PawRating.FOUR_PAW))
        cardDataList.add(CardData(MarkerType.RESTAURANT, "Cuddle Cafe",
            PawRating.FOUR_PAW))
        cardDataList.add(CardData(MarkerType.RESTAURANT, "Cuddle Cafe",
            PawRating.FOUR_PAW))
        cardDataList.add(CardData(MarkerType.RESTAURANT, "Cuddle Cafe",
            PawRating.FOUR_PAW))
        cardDataList.add(CardData(MarkerType.RESTAURANT, "Cuddle Cafe",
            PawRating.FOUR_PAW))
        cardDataList.add(CardData(MarkerType.RESTAURANT, "Cuddle Cafe",
            PawRating.FOUR_PAW))
        cardDataList.add(CardData(MarkerType.RESTAURANT, "Cuddle Cafe",
            PawRating.FOUR_PAW))


        // we are initializing our adapter class and passing our arraylist to it.
        val courseAdapter = CardDataAdapter(cardDataList)

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        val linearLayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        cardDataRV?.layoutManager = linearLayoutManager
        cardDataRV?.adapter = courseAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var selection: View.OnClickListener = View.OnClickListener { view ->
        val selectAll = resources.getString(R.string.select_all)
        val deselectAll = resources.getString(R.string.deselect_all)
        val textView = view as MaterialTextView
        val currText = textView.text

        placesList.forEach {
            it!!.isVisible = currText == selectAll
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