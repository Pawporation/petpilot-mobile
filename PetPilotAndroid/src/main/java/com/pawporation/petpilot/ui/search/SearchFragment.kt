package com.pawporation.petpilot.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pawporation.petpilot.android.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

//        val mapFragment = MapFragment()
//        childFragmentManager.beginTransaction().apply {
//            add(R.id.map, mapFragment)
//            commit()
//        }


//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_explore, container, false)
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Log.d("SEARCH", "in onviewcreated")
//        val searchBar = childFragmentManager.findFragmentById(R.id.search_bar) as SearchBar?
//        searchBar?.inflateMenu(R.menu.search_menu);
//
////        mapFragment?.getMapAsync(callback)
//    }