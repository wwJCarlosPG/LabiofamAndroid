package com.example.labiofam_android.view.bioproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R
import com.example.labiofam_android.api_model.Bioproducts

class BioproductsAdapter(var bioproducts: List<Bioproducts>,
                         private val onItemSelected:(Bioproducts) -> Unit) : RecyclerView.Adapter<BioproductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BioproductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bioproduct, parent, false)
        return BioproductsViewHolder(view)
    }

    override fun getItemCount(): Int = bioproducts.size

    override fun onBindViewHolder(holder: BioproductsViewHolder, position: Int) {
        holder.render(bioproducts[position], onItemSelected)
    }
}