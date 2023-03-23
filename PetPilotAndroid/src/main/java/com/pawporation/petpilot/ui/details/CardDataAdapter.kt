package com.pawporation.petpilot.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.models.PawDataModel

class CardDataAdapter(pawDataModelArrayList: ArrayList<PawDataModel>) :
    RecyclerView.Adapter<CardDataAdapter.ViewHolder>() {
    val pawDataModelArrayList: ArrayList<PawDataModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: PawDataModel = pawDataModelArrayList[position]
        holder.cardText.text = model.getInfo()
        holder.itemView.tag = model.uniqueID
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return pawDataModelArrayList.size
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
        this.pawDataModelArrayList = pawDataModelArrayList
    }
}