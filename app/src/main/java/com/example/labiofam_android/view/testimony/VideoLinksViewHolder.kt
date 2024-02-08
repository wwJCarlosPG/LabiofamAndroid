package com.example.labiofam_android.view.testimony

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.labiofam_android.R
import com.example.labiofam_android.apiModel.Testimony

class VideoLinksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val testimony_link: TextView = view.findViewById(R.id.testimony_link)

    fun render(testimonyLink: Testimony){

        testimony_link.text = testimonyLink.videoUrl
    }

}