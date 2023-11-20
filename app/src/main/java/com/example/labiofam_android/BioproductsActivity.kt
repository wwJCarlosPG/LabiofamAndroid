package com.example.labiofam_android

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
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
        BioproductsCategories.Other,
        BioproductsCategories.Epidemias,
        BioproductsCategories.Agricola
    )

    private val bioproducts = mutableListOf(
        Bioproducts("BACTIVEC", BioproductsCategories.Epidemias, "50usd", false, "....", "bactivec.jpg"),
        Bioproducts("THURISLAVE-26", BioproductsCategories.Epidemias, "50usd", false, "....", "thurisave.jpg"),
        Bioproducts("THURISLAVE-13", BioproductsCategories.Epidemias, "50usd", false, "....", "thurisave.jpg"),
        Bioproducts("NICOSAVE", BioproductsCategories.Epidemias, "50usd", false, "....", "nicosave.jpg"),
        Bioproducts("MELab", BioproductsCategories.Epidemias, "50usd", false, "....", "melab.jpg")
    )

    private lateinit var bioproducts_categories_rv:RecyclerView
    private lateinit var bioproductsCategoriesAdapter: BioproductsCategoriesAdapter

    private lateinit var bioproductsSearchView_sv: SearchView

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
        bioproductsSearchView_sv = findViewById(R.id.search_bioproducts)
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
        for (item in bioproductCategories) { if(item == BioproductsCategories.Other){item.isSelected = true}}
        bioproducts_categories_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bioproducts_categories_rv.adapter = bioproductsCategoriesAdapter

        bioproductsAdapter = BioproductsAdapter(bioproducts, onItemSelected ={ navigateToBioproductDialog(it) })
        bioproducts_rv.layoutManager = GridLayoutManager(this, 2)
        bioproducts_rv.adapter = bioproductsAdapter

        bioproductsSearchView_sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
        )
    }

    private fun searchByName(query: String) {
        val newBioproducts = bioproducts.filter { it.name.lowercase().contains(query.lowercase()) }
        bioproductsAdapter.bioproducts = newBioproducts
        bioproductsAdapter.notifyDataSetChanged()
    }

    private fun updateBioproductsCategories(position: Int){

        for (item in bioproductCategories)

            if (item != bioproductCategories[position]) {
                item.isSelected = false
                bioproductsCategoriesAdapter.notifyItemChanged(bioproductCategories.indexOf(item))
            }
            else {
                item.isSelected = !bioproductCategories[position].isSelected
                bioproductsCategoriesAdapter.notifyItemChanged(position)
                updateBioproducts()
            }

        if (bioproductCategories[position] == BioproductsCategories.Other &&
            bioproductCategories[position].isSelected){
            updateBioproductsAllSelected()
        }
    }

    private fun updateBioproductsAllSelected() {
        val newBioproducts = bioproducts.filter { bioproducts.contains(it) }
        bioproductsAdapter.bioproducts = newBioproducts
        bioproductsAdapter.notifyDataSetChanged()
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

        //Glide.with(bioproduct_dialog_iv.context).load(bioproduct.photo).into(bioproduct_dialog_iv)

        bioproduct_dialog_back_buttom.setOnClickListener { dialog.hide() }

        dialog.show()
    }
}
