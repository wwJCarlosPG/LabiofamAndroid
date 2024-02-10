package com.example.labiofam_android.view.bioproduct

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R

class BioproductsCategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tv_bioproductCategoryName: TextView =
        view.findViewById(R.id.tv_bioproductCategoryName)
    private val bioproducts_category_cv: CardView = view.findViewById(R.id.bioproduct_category_cv)


    fun render(bioproductCategory: BioproductsCategories, onItemSelected: (Int) -> Unit) {

        val color = if (bioproductCategory.isSelected) {
            R.color.itemToClick
        } else {
            R.color.white
        }

        bioproducts_category_cv.setCardBackgroundColor(
            ContextCompat.getColor(
                bioproducts_category_cv.context,
                color
            )
        )

        itemView.setOnClickListener { onItemSelected(layoutPosition) }

        when (bioproductCategory) {
            BioproductsCategories.Biofungicidas -> {
                tv_bioproductCategoryName.text = "Biofungicidas"
            }

            BioproductsCategories.Bioestimulantes -> {
                tv_bioproductCategoryName.text = "Bioestimulantes"
            }

            BioproductsCategories.Biofertilizantes -> {
                tv_bioproductCategoryName.text = "Biofertilizantes"
            }
            BioproductsCategories.Biolarvicidas -> {
                tv_bioproductCategoryName.text = "Biolarvicidas"
            }
            BioproductsCategories.Bioplagicidas -> {
                tv_bioproductCategoryName.text = "Bioplagicidas"
            }


        }


    }
}