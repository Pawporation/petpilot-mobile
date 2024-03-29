package com.pawporation.petpilot.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.models.PawDataModel

class CardDataAdapter(pawDataModelArrayList: ArrayList<PawDataModel>) :
    RecyclerView.Adapter<CardDataAdapter.ViewHolder>() {
    val pawDataModelArrayList: ArrayList<PawDataModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_details, parent, false)

        val itemClick: ViewHolder.CardClick = object : ViewHolder.CardClick {
            override fun onClick(view: View?, position: Int) {
                val activity = parent.context as AppCompatActivity
                val bundle = Bundle()
                val clickedItem = pawDataModelArrayList[position]
                bundle.putString("pawDataTitle", clickedItem.title)

                val transaction = activity.supportFragmentManager.beginTransaction()
                val detailsFragment = DetailsFragment()
                detailsFragment.arguments = bundle
                transaction.replace(R.id.container_main, detailsFragment)
                transaction.disallowAddToBackStack()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.commit()
            }
        }
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: PawDataModel = pawDataModelArrayList[position]
        holder.cardText.text = model.getInfo()
        holder.img.setImageResource(model.imgSrc)
        holder.itemView.tag = model.uniqueID

        val pawRating = model.pawRating.ordinal + 1
        if (pawRating > 0) {
            holder.pawRatingOne.visibility = View.VISIBLE
        }

        if (pawRating > 1) {
            holder.pawRatingTwo.visibility = View.VISIBLE
        }

        if (pawRating > 2) {
            holder.pawRatingThree.visibility = View.VISIBLE
        }

        if (pawRating > 3) {
            holder.pawRatingFour.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return pawDataModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View, itemClickListener: CardClick)
        : RecyclerView.ViewHolder(itemView) {
        var itemClickListener: CardClick? = null
        val cardText: TextView
        val img: ImageView
        val pawRatingOne: ImageView
        val pawRatingTwo: ImageView
        val pawRatingThree: ImageView
        val pawRatingFour: ImageView
        init {
            cardText = itemView.findViewById(R.id.card_text)
            img = itemView.findViewById(R.id.card_business_img)
            pawRatingOne = itemView.findViewById(R.id.paw_rating_1)
            pawRatingTwo = itemView.findViewById(R.id.paw_rating_2)
            pawRatingThree = itemView.findViewById(R.id.paw_rating_3)
            pawRatingFour = itemView.findViewById(R.id.paw_rating_4)
            this.itemClickListener = itemClickListener
            itemView.setOnClickListener(cardOnClickListener())
        }

        private fun cardOnClickListener(): OnClickListener = OnClickListener { view ->
            itemClickListener?.onClick(view, adapterPosition)
        }


        interface CardClick {
            fun onClick(view: View?, position: Int)
        }
    }


    // Constructor
    init {
        this.pawDataModelArrayList = pawDataModelArrayList
    }
}