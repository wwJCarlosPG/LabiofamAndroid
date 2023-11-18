package com.example.labiofam_android

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TestimonialsViewHolder(view: View) :RecyclerView.ViewHolder(view) {

    private val testimony_photo: ImageView = view.findViewById(R.id.testimony_photo)

    fun render(imageTestimony: ImageTestimony){
        when(imageTestimony.path) {
            "bactivec.jpg" -> testimony_photo.setImageResource(R.drawable.bactivec)
            "melab.jpg"->testimony_photo.setImageResource(R.drawable.melab)
            "nicosave.jpg"->testimony_photo.setImageResource(R.drawable.nicosave)
            "thurisave.jpg"->testimony_photo.setImageResource(R.drawable.thurisave)
        }
    }
}