package com.example.labiofam_android.view.testimony

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.ImageTestimony
import com.example.labiofam_android.R
import com.example.labiofam_android.apiModel.Testimony

class TestimonialsAdapter(private val image_testimonials: List<Testimony>): RecyclerView.Adapter<TestimonialsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_testimony, parent, false)
        return TestimonialsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestimonialsViewHolder, position: Int) {
        holder.render(image_testimonials[position])
    }

    override fun getItemCount(): Int = image_testimonials.size

}