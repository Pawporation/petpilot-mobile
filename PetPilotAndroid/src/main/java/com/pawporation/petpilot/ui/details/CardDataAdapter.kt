package com.pawporation.petpilot.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.models.CardData

class CardDataAdapter(courseModelArrayList: ArrayList<CardData>) :
    RecyclerView.Adapter<CardDataAdapter.ViewHolder>() {
    private val courseModelArrayList: ArrayList<CardData>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // to set data to textview and imageview of each card layout
        val model: CardData = courseModelArrayList[position]
        holder.cardText.text = model.getName()
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return courseModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardText: TextView
        init {
            cardText = itemView.findViewById(R.id.card_text)
        }
    }

    // Constructor
    init {
        this.courseModelArrayList = courseModelArrayList
    }
}