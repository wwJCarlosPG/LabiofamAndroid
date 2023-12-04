package com.example.labiofam_android.view.testimony

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.ImageTestimony
import com.example.labiofam_android.R

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