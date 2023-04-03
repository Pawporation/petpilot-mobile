package com.pawporation.petpilot.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.models.ReviewModel


class ReviewsDataAdapter(reviewsList: List<ReviewModel>) :
    RecyclerView.Adapter<ReviewsDataAdapter.ReviewViewHolder>() {
    private val reviewsList: List<ReviewModel>

    inner class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var reviewer: TextView
        var date: TextView
        var review: TextView

        init {
            reviewer = view.findViewById(R.id.reviewer)
            date = view.findViewById(R.id.date)
            review = view.findViewById(R.id.review)
        }
    }

    init {
        this.reviewsList = reviewsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.reviews, parent, false)
        return ReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review: ReviewModel = reviewsList[position]
        holder.reviewer.text = review.reviewer
        holder.date.text = review.date
        holder.review.text = review.review
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }
}