package com.example.labiofam_android.Bioproducts

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.labiofam_android.R

class BioproductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val bioproduct_name_tv:TextView = view.findViewById(R.id.bioproduct_name_tv)
    private  val bioproduct_cost_tv:TextView = view.findViewById(R.id.bioproduct_cost_tv)
    private val  bioproduct_iv:ImageView = view.findViewById(R.id.bioproduct_iv)

    fun render(bioproduct: Bioproducts){
        bioproduct_name_tv.text = bioproduct.name
        bioproduct_cost_tv.text = bioproduct.cost

        Glide.with(bioproduct_iv.context).load(bioproduct.photo).into(bioproduct_iv)
    }
}