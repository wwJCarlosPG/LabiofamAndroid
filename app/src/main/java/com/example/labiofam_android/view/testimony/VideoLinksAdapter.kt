package com.example.labiofam_android.view.testimony

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R
import com.example.labiofam_android.apiModel.Testimony

class VideoLinksAdapter(private val videos: List<Testimony>): RecyclerView.Adapter<VideoLinksViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoLinksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_link_testimony, parent, false)
        return VideoLinksViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoLinksViewHolder, position: Int) {
        holder.render(videos[position])
    }

    override fun getItemCount(): Int = videos.size

}