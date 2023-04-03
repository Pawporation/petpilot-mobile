package com.pawporation.petpilot.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pawporation.petpilot.android.R

// on below line we are creating a class for slider
// adapter and passing our array list to it.
class SliderAdapter(imageUrl: ArrayList<String>) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    // on below line we are creating a
    // new array list and initializing it.
    var sliderList: ArrayList<String> = imageUrl

    // on below line we are calling get method
    override fun getItemCount(): Int {
        // in this method we are returning
        // the size of our slider list.
        return Int.MAX_VALUE
    }

    // on below line we are calling on create view holder method.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        // inside this method we are inflating our layout file for our slider view.
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.img_details, parent, false)

        // on below line we are simply passing
        // the view to our slider view holder.
        return SliderViewHolder(inflate)
    }

    // on below line we are calling on bind view holder method to set the data to our image view.
    override fun onBindViewHolder(viewHolder : SliderViewHolder, position: Int) {

        // on below line we are checking if the view holder is null or not.
        val pos = position.mod(sliderList.size)
        Glide.with(viewHolder.itemView).load(sliderList[pos]).fitCenter()
            .into(viewHolder.imageView)
    }

    // on below line we are creating a class for slider view holder.
    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // on below line we are creating a variable for our
        // image view and initializing it with image id.
        var imageView: ImageView = itemView.findViewById(R.id.imgDetails)
    }
}
