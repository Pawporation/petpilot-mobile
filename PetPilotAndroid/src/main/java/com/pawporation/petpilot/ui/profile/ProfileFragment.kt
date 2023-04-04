package com.pawporation.petpilot.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.android.databinding.FragmentProfileBinding
import com.pawporation.petpilot.models.ReviewModel
import com.pawporation.petpilot.models.TimelineModel
import com.pawporation.petpilot.ui.details.ReviewsDataAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textProfile
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        return root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.setShowHideAnimationEnabled(false)
        supportActionBar?.show()

        val imgView: ImageView = binding.profileImg
        imgView.setImageResource(com.pawpals.petpilot.R.mipmap.prosper_foreground)

        val timeline1 = TimelineModel("Vet", "April 2, 2023",
            "Prosper is healthy and is now 36 pounds!")

        val timeline2 = TimelineModel("Dog Beach", "March 30, 2023",
            "Made some new doggo friends")

        val timelineList = listOf(timeline1, timeline2)

        val timelineDataAdapter = TimelineDataAdapter(timelineList)
        val lm = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        val timelineRV = view.findViewById<RecyclerView>(R.id.profileView_timeline_rv)
        timelineRV?.layoutManager = lm
        timelineRV?.adapter = timelineDataAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}