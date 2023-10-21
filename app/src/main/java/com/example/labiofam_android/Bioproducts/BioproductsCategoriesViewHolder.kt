package com.example.labiofam_android.Bioproducts

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R

class BioproductsCategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tv_bioproductCategoryName:TextView = view.findViewById(R.id.tv_bioproductCategoryName)


    fun render(bioproductCategory: BioproductsCategories){

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