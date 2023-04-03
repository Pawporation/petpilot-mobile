package com.pawporation.petpilot.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.android.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailView = view.findViewById<RelativeLayout>(R.id.bottom_sheet_details)
        detailView?.let {
            val bottomSheetBehavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(detailView)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.skipCollapsed = true
        }

        // Fetch items to add.
        // Urls of our images.
        val url1 = "https://images.squarespace-cdn.com/content/v1/5b904a2fd274cba52d493ff1/645a1fc4-ccec-44b8-b2b3-046274bed15d/3.png"
        val url2 = "https://sprudge.com/wp-content/uploads/2016/07/Sprudge-CoffeeOnInstagramReagandoodle-AnnaBrones-13531917_170645166683036_975009223_n.jpg"
        val url3 = "https://images.squarespace-cdn.com/content/v1/5dda8663782768313a35b549/1631509576411-HDPFCGXZYRQ42HABOR4G/Singapore+Pet-Friendly+Cafe+Five+Oars+Coffee+Roasters?format=1000w"
        val url4 = "https://sprudge.com/wp-content/uploads/2016/07/Sprudge-CoffeeOnInstagramReagandoodle-AnnaBrones-13320016_1012846415498199_690291443_n-740x740.jpg"
        val imageUrls = arrayListOf(url1, url2, url3, url4)
        val sliderAdapter = SliderAdapter(imageUrls)

        val linearLayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        val detailViewRV = view.findViewById<RecyclerView>(R.id.detailView_rv)
        detailViewRV?.layoutManager = linearLayoutManager
        detailViewRV?.adapter = sliderAdapter

        val textView = view.findViewById<TextView>(R.id.detailView_title)
        textView.text = arguments?.getString("pawDataTitle")
    }
}