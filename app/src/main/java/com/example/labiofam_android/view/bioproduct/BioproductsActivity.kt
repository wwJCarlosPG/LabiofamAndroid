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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BioproductsActivity : ViewInterface, AppCompatActivity(), BioproductContract.BioproductView {



    /*private val bioproductCategories = listOf(

        BioproductsCategories.Bioestimulantes,
        BioproductsCategories.Biolarvicidas,
        BioproductsCategories.Bioplagicidas,
        BioproductsCategories.Biofertilizantes,
        BioproductsCategories.Biofungicidas
    )*/
    private var bioproductCategories:List<BioproductsCategories> = listOf()

    private var bioproducts = listOf<Bioproducts>()

    private lateinit var bioproducts_categories_rv:RecyclerView
    private lateinit var bioproductsCategoriesAdapter: BioproductsCategoriesAdapter
    private lateinit var  search_bioproducts:SearchView
    private lateinit var bioproducts_rv: RecyclerView
    private var bioproduct_model = BioproductModel()
    private var bioproduct_presenter = BioproductPresenter(this@BioproductsActivity, bioproduct_model)
    private lateinit var bioproductsAdapter: BioproductsAdapter
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
                var query = newText.toString()
                if(query==""){
                    getAllBioproducts()
                }
                else{
                    getBySubstring(query)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                var query = query.toString()
                if(query==""){
                    getAllBioproducts()
                }
                else{
                    getBySubstring(query)
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
                    bioproductsAdapter.notifyDataSetChanged()
                    bioproducts_rv.adapter = bioproductsAdapter
                }

            }
            else{
                runOnUiThread{
                bioproductsAdapter = BioproductsAdapter(bioproducts, onItemSelected ={ navigateToBioproductDialog(it) })
                bioproductsAdapter.bioproducts = bioproducts
                bioproductsAdapter.notifyDataSetChanged()
                bioproducts_rv.adapter = bioproductsAdapter
                showError("No hay bioproductos que mostrar.")
                }

            }
        }
    }
    fun getBySubstring(substring:String){
        lifecycleScope.launch(Dispatchers.IO) {
            val bioproducts = bioproduct_presenter.getBioproductBySubstring(substring)
            Log.d("jc", "${bioproducts.size}")
            runOnUiThread{
                if(bioproducts.isNotEmpty()){
                bioproductsAdapter = BioproductsAdapter(bioproducts, onItemSelected ={ navigateToBioproductDialog(it) })
                bioproductsAdapter.bioproducts = bioproducts
                Log.d("cantidad","${bioproductsAdapter.itemCount.toString()}")
                bioproductsAdapter.notifyDataSetChanged()
                bioproducts_rv.adapter = bioproductsAdapter
                //no modifica los adapter
                }
                else{
                    runOnUiThread{
                        bioproductsAdapter = BioproductsAdapter(bioproducts, onItemSelected ={ navigateToBioproductDialog(it) })
                        bioproductsAdapter.bioproducts = bioproducts
                        bioproductsAdapter.notifyDataSetChanged()
                        bioproducts_rv.adapter = bioproductsAdapter
                        showError("No hay bioproductos que mostrar.")
                    }
                }
            }



        }
    }
     override fun initUI() {
        search_bioproducts = findViewById(R.id.search_bioproducts)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                bioproducts = bioproduct_presenter.getBioproducts()
                if(bioproducts.isNotEmpty()) {
                    bioproductCategories = bioproduct_presenter.getCategories(bioproducts)
                    Log.d("jc",bioproductCategories.size.toString())
                    showBioproducts(bioproducts,bioproductCategories)
                }else{
                    showError("No hay bioproductos que mostrar")
                }
            }
            catch(ex: Exception){
                showError("Error de conexión")
            }


        }

    }


    override fun showError(message: String) {
        runOnUiThread{
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateBioproductsCategories(position: Int){
        for (i in bioproductCategories.indices){
            if(bioproductCategories[i].isSelected && position!=i){
                return
            }
        }
        bioproductCategories[position].isSelected = !bioproductCategories[position].isSelected
        bioproductsCategoriesAdapter.notifyItemChanged(position)
        updateBioproducts()
    }

    private fun updateBioproducts(){
        val selectedBioproductsCategories: BioproductsCategories? = bioproductCategories.firstOrNull{ it.isSelected}
        if(selectedBioproductsCategories == null){
            val newBioproducts = bioproducts
            bioproductsAdapter.bioproducts = newBioproducts
            bioproductsAdapter.notifyDataSetChanged()
        }
        else{
            val newBioproducts = bioproducts.filter { selectedBioproductsCategories.toString()==it.type_of_product }
            bioproductsAdapter.bioproducts = newBioproducts
            bioproductsAdapter.notifyDataSetChanged()
        }

    }

    private fun navigateToBioproductDialog(bioproduct: Bioproducts) {
        try {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_bioproduct)
            var bioproduct_description_title:TextView =
                dialog.findViewById(R.id.bioproduct_dialog_descriptiont_tv)
            var bioproduct_advantages_title:TextView =
                dialog.findViewById(R.id.bioproduct_dialog_advantagest_tv)
            var bioproduct_sum_title:TextView =
                dialog.findViewById(R.id.bioproduct_dialog_sumt_tv)
            var bioproduct_sum:TextView =
                dialog.findViewById(R.id.bioproduct_dialog_sum_tv)

            var bioproduct_dialog_back_buttom: ImageView =
                dialog.findViewById((R.id.bioproduct_dialog_back_buttom))

            var bioproduct_dialog_name_tv: TextView =
                dialog.findViewById(R.id.bioproduct_dialog_name_tv)
            var bioproduct_dialog_advantages_tv: TextView =
                dialog.findViewById(R.id.bioproduct_dialog_advantages_tv)
            var bioproduct_dialog_description_tv: TextView =
                dialog.findViewById(R.id.bioproduct_dialog_description_tv)
            var bioproduct_dialog_iv: ImageView = dialog.findViewById(R.id.bioproduct_dialog_iv)

            var bioproduct_diseases_title_tv: TextView =
                dialog.findViewById(R.id.bioproduct_dialog_diseasest_tv)
            var bioproduct_dialog_diseases_tv: TextView =
                dialog.findViewById(R.id.bioproduct_dialog_diseases_tv)

            if(bioproduct.diseases!=null){
                bioproduct_dialog_diseases_tv.text = bioproduct.diseases
                bioproduct_diseases_title_tv.text = "Enfermedades:"
            }
            if(bioproduct.description!=null) {
                bioproduct_dialog_description_tv.text = bioproduct.description
                bioproduct_description_title.text = "Descripcion:"
            }
            if(bioproduct.advantages!=null){
                bioproduct_advantages_title.text = "Ventajas:"
                bioproduct_dialog_advantages_tv.text = bioproduct.advantages
            }
            if(bioproduct.summary.size()>0){
                bioproduct_sum_title.text = "Otras especifiaciones"
                val jsonObject: JsonObject = bioproduct.summary
                val gson: Gson = GsonBuilder().setPrettyPrinting().create()
                val jsonString: String = gson.toJson(jsonObject)
                var result = jsonString.replace("\"","").replace("{","").replace("}","")
                bioproduct_sum.text = result

            }

            bioproduct_dialog_name_tv.text = bioproduct.name

            Glide.with(bioproduct_dialog_iv.context).load(bioproduct.image)
                .into(bioproduct_dialog_iv)

            bioproduct_dialog_back_buttom.setOnClickListener { dialog.hide() }

            dialog.show()
        }
        catch (e:Exception){
            showError("Error de conexión")
        }
    }

    override fun showBioproducts(bioproducts: List<Bioproducts>,bioproductCategories:List<BioproductsCategories> ) {
        runOnUiThread {
            bioproductsCategoriesAdapter = BioproductsCategoriesAdapter(bioproductCategories) { position -> updateBioproductsCategories(position) }
            bioproducts_categories_rv.layoutManager = LinearLayoutManager(this@BioproductsActivity, LinearLayoutManager.HORIZONTAL, false)
            bioproducts_categories_rv.adapter = bioproductsCategoriesAdapter

            bioproductsAdapter = BioproductsAdapter(bioproducts, onItemSelected = { navigateToBioproductDialog(it) })
            bioproducts_rv.layoutManager = GridLayoutManager(this@BioproductsActivity, 2)
            bioproducts_rv.adapter = bioproductsAdapter
        }
    }
}
