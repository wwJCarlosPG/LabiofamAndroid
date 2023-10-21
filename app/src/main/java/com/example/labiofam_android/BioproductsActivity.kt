package com.example.labiofam_android

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.Bioproducts.Bioproducts
import com.example.labiofam_android.Bioproducts.BioproductsAdapter
import com.example.labiofam_android.Bioproducts.BioproductsCategories
import com.example.labiofam_android.Bioproducts.BioproductsCategoriesAdapter

class BioproductsActivity : AppCompatActivity() {

    private val bioproductsCategories = listOf(
        BioproductsCategories.Agricola,
        BioproductsCategories.Epidemias,
        BioproductsCategories.Other
    )

    private val bioproducts = mutableListOf(
        Bioproducts("Spray", BioproductsCategories.Agricola, "50usd", false, "", ""),
        Bioproducts("Spray", BioproductsCategories.Agricola, "50usd", false, "", ""),
        Bioproducts("Spray", BioproductsCategories.Epidemias, "50usd", false, "", ""),
        Bioproducts("Spray", BioproductsCategories.Epidemias, "50usd", false, "", ""),
        Bioproducts("Spray", BioproductsCategories.Other, "50usd", false, "", ""),
        Bioproducts("Spray", BioproductsCategories.Other, "50usd", false, "", ""),
        Bioproducts("Spray", BioproductsCategories.Other, "50usd", false, "", "")

    )

    private lateinit var bioproducts_categories_rv: RecyclerView
    private lateinit var bioproductsCategoriesAdapter: BioproductsCategoriesAdapter

    private lateinit var bioproducts_rv: RecyclerView
    private lateinit var bioproductsAdapter: BioproductsAdapter

    private lateinit var bioproduct_cv: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bioproducts)
        initComponent()
        initUI()
        initListeners()
    }

    private fun initListeners() {
        bioproduct_cv.setOnClickListener { showBioproductDialog() }
    }

    private fun showBioproductDialog(){
        val dialog = Dialog(this)
    }

    private fun initUI() {
        bioproductsCategoriesAdapter = BioproductsCategoriesAdapter(bioproductsCategories)
        bioproducts_categories_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bioproducts_categories_rv.adapter = bioproductsCategoriesAdapter

        bioproductsAdapter = BioproductsAdapter(bioproducts)
        bioproducts_rv.layoutManager = GridLayoutManager(this, 2)
        bioproducts_rv.adapter = bioproductsAdapter
    }

    private fun initComponent() {
        bioproducts_categories_rv = findViewById(R.id.bioproducts_categories_rv)
        bioproducts_rv = findViewById(R.id.bioproducts_rv)
        bioproduct_cv = findViewById(R.id.bioproduct_cv)
    }
}