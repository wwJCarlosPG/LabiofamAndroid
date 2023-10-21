package com.example.labiofam_android.Bioproducts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R

class BioproductsAdapter(private val bioproducts: List<Bioproducts>) : RecyclerView.Adapter<BioproductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BioproductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bioproduct, parent, false)
        return BioproductsViewHolder(view)
    }

    override fun getItemCount(): Int = bioproducts.size

    override fun onBindViewHolder(holder: BioproductsViewHolder, position: Int) {
        holder.render(bioproducts[position])
    }
}