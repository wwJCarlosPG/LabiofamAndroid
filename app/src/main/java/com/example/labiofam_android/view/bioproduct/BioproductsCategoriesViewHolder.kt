package com.example.labiofam_android.view.bioproduct

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R

class BioproductsCategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tv_bioproductCategoryName:TextView = view.findViewById(R.id.tv_bioproductCategoryName)
    private val bioproducts_category_cv: CardView = view.findViewById(R.id.bioproduct_category_cv)


    fun render(bioproductCategory: BioproductsCategories, onItemSelected: (Int) -> Unit){

        val color = if(bioproductCategory.isSelected){
            R.color.itemToClick
        } else{
            R.color.white
        }

        bioproducts_category_cv.setCardBackgroundColor(ContextCompat.getColor(bioproducts_category_cv.context, color))

        itemView.setOnClickListener { onItemSelected(layoutPosition) }

        when(bioproductCategory){
            BioproductsCategories.Agricola -> {
                tv_bioproductCategoryName.text = "Agrícolas"
            }
            BioproductsCategories.Epidemias -> {
                tv_bioproductCategoryName.text = "Epidemias"
            }
            BioproductsCategories.Other -> {
                tv_bioproductCategoryName.text = "Todos"
            }
        }


    }


}