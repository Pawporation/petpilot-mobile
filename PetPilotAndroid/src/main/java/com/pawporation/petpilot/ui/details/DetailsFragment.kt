package com.pawporation.petpilot.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
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

        val detailView = view.findViewById<LinearLayout>(R.id.bottom_sheet_details)
        detailView?.let {
            val bottomSheetBehavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(detailView)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.skipCollapsed = true
        }

        val detailsTitle = view.findViewById<TextView>(R.id.details_title)
        detailsTitle.text = arguments?.getString("pawDataTitle")
    }
}