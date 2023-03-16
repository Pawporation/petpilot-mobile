package com.pawporation.petpilot.ui.explore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.android.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        searchBar.inflateMenu(R.menu.searchbar_menu)
//        searchBar.setOnMenuItemClickListener { menuItem -> true }


//        val mapFragment = MapFragment()
//        childFragmentManager.beginTransaction().apply {
//            add(R.id.map, mapFragment)
//            commit()
//        }
        return root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.setShowHideAnimationEnabled(false)
        supportActionBar?.hide()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}