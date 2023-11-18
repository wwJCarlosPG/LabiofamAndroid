package com.example.labiofam_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TestimonialsAdapter(private val image_testimonials: List<ImageTestimony>): RecyclerView.Adapter<TestimonialsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_testimony, parent, false)
        return TestimonialsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestimonialsViewHolder, position: Int) {
        holder.render(image_testimonials[position])
    }

    override fun getItemCount(): Int = image_testimonials.size

}