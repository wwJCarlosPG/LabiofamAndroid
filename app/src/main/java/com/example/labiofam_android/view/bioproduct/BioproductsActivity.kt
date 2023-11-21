package com.example.labiofam_android.view.bioproduct

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R
import com.example.labiofam_android.api_model.Bioproducts

class BioproductsActivity : AppCompatActivity() {

    private val bioproductCategories = listOf(
        BioproductsCategories.Other,
        BioproductsCategories.Agricola,
        BioproductsCategories.Epidemias
    )

    private val bioproducts = listOf<Bioproducts>()

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
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.leftarrow)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initUI() {
        bioproductsCategoriesAdapter = BioproductsCategoriesAdapter(bioproductCategories) { position -> updateBioproductsCategories(position)}
        bioproducts_categories_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bioproducts_categories_rv.adapter = bioproductsCategoriesAdapter

        bioproductsAdapter = BioproductsAdapter(bioproducts, onItemSelected ={ navigateToBioproductDialog(it) })
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
        //val newBioproducts = bioproducts.filter { selectedBioproductsCategories.contains(it.type) }
        //bioproductsAdapter.bioproducts = newBioproducts
        //bioproductsAdapter.notifyDataSetChanged()
    }

    private fun navigateToBioproductDialog(bioproduct: Bioproducts) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_bioproduct)

        var bioproduct_dialog_name_tv: TextView = dialog.findViewById(R.id.bioproduct_dialog_name_tv)
        var bioproduct_dialog_cost_tv: TextView = dialog.findViewById(R.id.bioproduct_dialog_cost_tv)
        var bioproduct_dialog_description_tv: TextView = dialog.findViewById(R.id.bioproduct_dialog_description_tv)
        var bioproduct_dialog_iv: ImageView = dialog.findViewById(R.id.bioproduct_dialog_iv)
        var bioproduct_dialog_back_buttom: ImageView = dialog.findViewById((R.id.bioproduct_dialog_back_buttom))

        bioproduct_dialog_name_tv.text = bioproduct.name.toString()
        //bioproduct_dialog_cost_tv.text = bioproduct.cost
        //bioproduct_dialog_description_tv.text = bioproduct.description

        //Glide.with(bioproduct_dialog_iv.context).load(bioproduct.photo).into(bioproduct_dialog_iv)

        bioproduct_dialog_back_buttom.setOnClickListener { dialog.hide() }

        dialog.show()
    }
}
