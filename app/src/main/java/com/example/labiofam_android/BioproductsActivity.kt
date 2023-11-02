package com.example.labiofam_android

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.labiofam_android.Bioproducts.Bioproducts
import com.example.labiofam_android.Bioproducts.BioproductsAdapter
import com.example.labiofam_android.Bioproducts.BioproductsCategories
import com.example.labiofam_android.Bioproducts.BioproductsCategoriesAdapter

class BioproductsActivity : AppCompatActivity() {

    private val bioproductCategories = listOf(
        BioproductsCategories.Agricola,
        BioproductsCategories.Epidemias,
        BioproductsCategories.Other
    )

    private val bioproducts = mutableListOf(
        Bioproducts("BACTIVEC", BioproductsCategories.Epidemias, "50usd", false, "....", "https://photos.google.com/photo/AF1QipNSZJnPVmp2p9rYiNHSzu9Hh9F_qHY-NqmACy52"),
        Bioproducts("THURISLAVE-26", BioproductsCategories.Epidemias, "50usd", false, "....", "https://photos.google.com/photo/AF1QipMFdV-peyg3oZ6ZnyqMc9WmAucWKfuETNjES-qr"),
        Bioproducts("THURISLAVE-13", BioproductsCategories.Epidemias, "50usd", false, "....", "https://photos.google.com/photo/AF1QipOCr4KSnGHbhYdPCl9pU2S5GPveHfCjo6wrBiyb"),
        Bioproducts("NICOSAVE", BioproductsCategories.Epidemias, "50usd", false, "....", "https://photos.google.com/photo/AF1QipNDztc27-LEsDcsMWlKbNCZ28TmQUjfUPaiEu_8"),
        Bioproducts("MELab", BioproductsCategories.Epidemias, "50usd", false, "....", "https://photos.google.com/photo/AF1QipM4eLpn7dAZNhJUBH5klrh5uYT8MGpwRIiHcmr2")
    )

    private lateinit var bioproducts_categories_rv:RecyclerView
    private lateinit var bioproductsCategoriesAdapter: BioproductsCategoriesAdapter

    private lateinit var bioproducts_rv: RecyclerView
    private lateinit var bioproductsAdapter: BioproductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bioproducts)

        initComponent()
        initUI()
    }


    private fun initComponent(){
        bioproducts_categories_rv = findViewById(R.id.bioproducts_categories_rv)
        bioproducts_rv = findViewById(R.id.bioproducts_rv)
    }

    private fun initUI() {
        bioproductsCategoriesAdapter = BioproductsCategoriesAdapter(bioproductCategories) { position -> updateBioproductsCategories(position)}
        bioproducts_categories_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bioproducts_categories_rv.adapter = bioproductsCategoriesAdapter

        bioproductsAdapter = BioproductsAdapter(bioproducts, onItemSelected = { navigateToBioproductDialog(it) })
        bioproducts_rv.layoutManager = GridLayoutManager(this, 2)
        bioproducts_rv.adapter = bioproductsAdapter
    }

    private fun updateBioproductsCategories(position: Int){
        bioproductCategories[position].isSelected = !bioproductCategories[position].isSelected
        bioproductsCategoriesAdapter.notifyItemChanged(position)
        updateBioproducts()
    }

    private fun updateBioproducts(){
        val selectedBioproductsCategories: List<BioproductsCategories> = bioproductCategories.filter { it.isSelected }
        val newBioproducts = bioproducts.filter { selectedBioproductsCategories.contains(it.category) }
        bioproductsAdapter.bioproducts = newBioproducts
        bioproductsAdapter.notifyDataSetChanged()
    }

    private fun navigateToBioproductDialog(bioproduct: Bioproducts) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_bioproduct)

        var bioproduct_dialog_name_tv: TextView = dialog.findViewById(R.id.bioproduct_dialog_name_tv)
        var bioproduct_dialog_cost_tv: TextView = dialog.findViewById(R.id.bioproduct_dialog_cost_tv)
        var bioproduct_dialog_description_tv: TextView = dialog.findViewById(R.id.bioproduct_dialog_description_tv)
        var bioproduct_dialog_iv: ImageView = dialog.findViewById(R.id.bioproduct_dialog_iv)
        var bioproduct_dialog_back_buttom: ImageView = dialog.findViewById((R.id.bioproduct_dialog_back_buttom))

        bioproduct_dialog_name_tv.text = bioproduct.name
        bioproduct_dialog_cost_tv.text = bioproduct.cost
        bioproduct_dialog_description_tv.text = bioproduct.description

        Glide.with(bioproduct_dialog_iv.context).load(bioproduct.photo).into(bioproduct_dialog_iv)

        bioproduct_dialog_back_buttom.setOnClickListener { dialog.hide() }

        dialog.show()
    }
}
