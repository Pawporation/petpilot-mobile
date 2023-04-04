package com.pawporation.petpilot.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.models.TimelineModel


class TimelineDataAdapter(timelineInfoList: List<TimelineModel>) :
    RecyclerView.Adapter<TimelineDataAdapter.TimelineViewHolder>() {
    private val timelineInfoList: List<TimelineModel>

    inner class TimelineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var event: TextView
        var date: TextView
        var details: TextView

        init {
            event = view.findViewById(R.id.event)
            date = view.findViewById(R.id.date)
            details = view.findViewById(R.id.details)
        }
    }

    init {
        this.timelineInfoList = timelineInfoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.timeline_card, parent, false)
        return TimelineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val timeline: TimelineModel = timelineInfoList[position]
        holder.event.text = timeline.event
        holder.date.text = timeline.date
        holder.details.text = timeline.details
    }

    override fun getItemCount(): Int {
        return timelineInfoList.size
    }
}