package com.example.labiofam_android.view.bioproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R

class BioproductsCategoriesAdapter(private val categories: List<BioproductsCategories>,
                                   private val onItemSelected: (Int) -> Unit):
    RecyclerView.Adapter<BioproductsCategoriesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BioproductsCategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_bioproduct_categories, parent, false)
        return BioproductsCategoriesViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: BioproductsCategoriesViewHolder, position: Int) {
        holder.render(categories[position], onItemSelected)
    }
}