package com.example.labiofam_android.view.testimony

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.labiofam_android.ImageTestimony
import com.example.labiofam_android.R
import com.example.labiofam_android.apiModel.Testimony

class TestimonialsViewHolder(view: View) :RecyclerView.ViewHolder(view) {

    private val testimony_photo: ImageView = view.findViewById(R.id.testimony_photo)

    fun render(imageTestimony: Testimony){


        Glide.with(testimony_photo.context).load(imageTestimony.image).into(testimony_photo)

    }

}