package com.example.labiofam_android.view.bioproduct

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.labiofam_android.R
import com.example.labiofam_android.apiServices.BioproductService
import com.example.labiofam_android.apiServices.RetrofitHelper
import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.contract.BioproductContract
import com.example.labiofam_android.model.BioproductModel
import com.example.labiofam_android.presenter.BioproductPresenter
import com.example.labiofam_android.view_interface.ViewInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BioproductsActivity : ViewInterface, AppCompatActivity(), BioproductContract.BioproductView {

    private val bioproductCategories = listOf(
        BioproductsCategories.Other,
        BioproductsCategories.Agricola,
        BioproductsCategories.Epidemias
    )

    private var bioproducts = listOf<Bioproducts>()

    private lateinit var bioproducts_categories_rv:RecyclerView
    private lateinit var bioproductsCategoriesAdapter: BioproductsCategoriesAdapter
    private lateinit var  search_bioproducts:SearchView
    private lateinit var bioproducts_rv: RecyclerView
    private var bioproduct_model = BioproductModel()
    private var bioproduct_presenter = BioproductPresenter(this@BioproductsActivity, bioproduct_model)
    private lateinit var bioproductsAdapter: BioproductsAdapter
    val bioproduct_service = RetrofitHelper.getInstance().create(BioproductService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bioproducts)
        initUI()
        initComponents()

    }


    override fun initComponents(){
        bioproducts_categories_rv = findViewById(R.id.bioproducts_categories_rv)
        bioproducts_rv = findViewById(R.id.bioproducts_rv)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        search_bioproducts!!.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                var location = newText.toString()
                Log.d("jc", "djsakdfj")
                if(location==""){
                    getAllBioproducts()
                }
                else{
                    getBySubstring(location)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                var location = query.toString()
                if(location==""){
                    getAllBioproducts()
                }
                else{
                    getBySubstring(location)
                }
                return true
            }
        })
    }
    fun getAllBioproducts(){
        lifecycleScope.launch(Dispatchers.IO) {
            val bioproducts = bioproduct_presenter.getBioproducts()
            if(bioproducts.isNotEmpty()){
                runOnUiThread{
                    bioproductsAdapter = BioproductsAdapter(bioproducts, onItemSelected ={ navigateToBioproductDialog(it) })
                    bioproductsAdapter.bioproducts = bioproducts
                    Log.d("cantidad","${bioproductsAdapter.itemCount.toString()}")
                    bioproductsAdapter.notifyDataSetChanged()
                    bioproducts_rv.adapter = bioproductsAdapter
                }

            }
            else{
                showError("Error de conexión.")
            }
        }
    }
    fun getBySubstring(substring:String){
        lifecycleScope.launch(Dispatchers.IO) {
            val bioproducts = bioproduct_presenter.getBioproductBySubstring(substring)
            if(bioproducts.isNotEmpty()){
                Log.d("jc", "${bioproducts.size}")
                runOnUiThread{
                    bioproductsAdapter = BioproductsAdapter(bioproducts, onItemSelected ={ navigateToBioproductDialog(it) })
                    bioproductsAdapter.bioproducts = bioproducts
                    Log.d("cantidad","${bioproductsAdapter.itemCount.toString()}")
                    bioproductsAdapter.notifyDataSetChanged()
                    bioproducts_rv.adapter = bioproductsAdapter
                    //no modifica los adapter
                }

            }
            else{

                showError("Error de conexión")
            }
        }
    }
     override fun initUI() {
        search_bioproducts = findViewById(R.id.search_bioproducts)
        lifecycleScope.launch(Dispatchers.IO) {
            bioproducts = bioproduct_presenter.getBioproducts()
            if(bioproducts.isNotEmpty()) {
                showBioproducts(bioproducts)
            }else{
                showError("Error de conexión")
            }

        }

    }


    override fun showError(message: String) {
        Toast.makeText(this, "${message}", Toast.LENGTH_SHORT).show()
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
        try {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_bioproduct)

            var bioproduct_dialog_name_tv: TextView =
                dialog.findViewById(R.id.bioproduct_dialog_name_tv)
            var bioproduct_dialog_summary_tv: TextView =
                dialog.findViewById(R.id.bioproduct_dialog_summary_tv)
            var bioproduct_dialog_description_tv: TextView =
                dialog.findViewById(R.id.bioproduct_dialog_description_tv)
            var bioproduct_dialog_iv: ImageView = dialog.findViewById(R.id.bioproduct_dialog_iv)
            var bioproduct_dialog_back_buttom: ImageView =
                dialog.findViewById((R.id.bioproduct_dialog_back_buttom))

            bioproduct_dialog_name_tv.text = bioproduct.name.toString()
            bioproduct_dialog_description_tv.text = bioproduct.specifications
            bioproduct_dialog_summary_tv.text = bioproduct.summary
            Glide.with(bioproduct_dialog_iv.context).load(bioproduct.image)
                .into(bioproduct_dialog_iv)

            bioproduct_dialog_back_buttom.setOnClickListener { dialog.hide() }

            dialog.show()
        }
        catch (e:Exception){
            showError("Error de conexión")
        }
    }

    override fun showBioproducts(bioproducts: List<Bioproducts>) {
        runOnUiThread{
            bioproductsCategoriesAdapter = BioproductsCategoriesAdapter(bioproductCategories) { position -> updateBioproductsCategories(position)}
            bioproducts_categories_rv.layoutManager = LinearLayoutManager(this@BioproductsActivity, LinearLayoutManager.HORIZONTAL, false)
            bioproducts_categories_rv.adapter = bioproductsCategoriesAdapter
            bioproductsAdapter = BioproductsAdapter(bioproducts, onItemSelected ={ navigateToBioproductDialog(it) })
            bioproducts_rv.layoutManager = GridLayoutManager(this@BioproductsActivity, 2)
            bioproducts_rv.adapter = bioproductsAdapter

        }
    }
}
