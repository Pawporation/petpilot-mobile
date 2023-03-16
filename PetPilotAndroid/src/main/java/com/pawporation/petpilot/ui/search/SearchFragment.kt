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


//        // The usage of an interface lets you inject your own implementation
//        val menuHost: MenuHost = requireActivity()
//
//        // Add menu items without using the Fragment Menu APIs
//        // Note how we can tie the MenuProvider to the viewLifecycleOwner
//        // and an optional Lifecycle.State (here, RESUMED) to indicate when
//        // the menu should be visible
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                // Add menu items here
//                menuInflater.inflate(R.menu.search_menu, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                // Handle the menu selection
//                return when (menuItem.itemId) {
////                    R.id.menu_clear -> {
////                        // clearCompletedTasks()
////                        true
////                    }
////                    R.id.menu_refresh -> {
////                        // loadTasks(true)
////                        true
////                    }
//                    else -> false
//                }
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