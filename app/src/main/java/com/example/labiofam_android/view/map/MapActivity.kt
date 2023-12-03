package com.example.labiofam_android.view.map

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.labiofam_android.R
import com.example.labiofam_android.Services.BioproductService
import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.Services.SellPointService
import com.example.labiofam_android.api_model.Bioproducts
import com.example.labiofam_android.api_model.SellPoint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener, InfoWindowAdapter {
    private lateinit var mGoogleMap: GoogleMap
    private lateinit var searchView:SearchView
    private lateinit var toolbar:Toolbar
    val sellPoint_service = RetrofitHelper.getInstance().create(SellPointService::class.java)
    val bioproduct_service = RetrofitHelper.getInstance().create(BioproductService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initUI()
        initComponents()
        //val api = RetrofitHelper.getInstance().create(SellPointService::class.java)

    }

    fun initUI(){
        searchView = findViewById(R.id.search_map)
    }


    fun initComponents(){
        toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.leftarrow)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment)
                as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchView!!.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                var location = newText.toString()
                if(location==""){
                    getAllSellPoints()
                }
                else{
                    getBySubstring(location)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                var location = query.toString()
                if(location==""){
                    getAllSellPoints()
                }
                else{
                    getBySubstring(location)
                }
                return true
            }
        })

    }
    //metodo auxiliar cuando el search view esta vacio.
    fun getAllSellPoints(){

        lifecycleScope.launch {
            val sellPoints_response = sellPoint_service.getSellPoints()
            if(sellPoints_response.isSuccessful){
                var sellpoints = sellPoints_response.body()!!
                Log.d("jc", "Entra a esta parte")
                runOnUiThread{
                    sellpoints.forEach{
                            item->
                        val location = LatLng(item.latitude, item.longitude)
                        mGoogleMap.addMarker(MarkerOptions()
                            .position(location))
                    }
                }

            }
            else{
                Log.d("jc", "Entra al else")
                //show error message(definir una vista para errores).
            }
        }


    }
    //metodo auxiliar cuando el search view tiene contenido.
    fun getBySubstring(location:String){
        GlobalScope.launch {
            var sellPoints_by_address = sellPoint_service.getSellPointsByAddress(location)
            if(sellPoints_by_address.isSuccessful){
                Log.d("jc", "is successful")
                runOnUiThread{
                    mGoogleMap.clear()
                }
                sellPoints_by_address.body()!!.forEach { item->
                    runOnUiThread{
                        mGoogleMap.addMarker(MarkerOptions().position(LatLng(item.latitude, item.longitude)))
                    }
                }
            }
            else{
                Log.d("jc", "No sirve")
            }
        }
    }
    override fun getInfoContents(marker: Marker):View?
    {

        return null
    }

    override fun onMarkerClick(marker:Marker):Boolean
    {
        Log.d("jc","getInfowindow")
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_point)
        //cuando toque el marcador puedo saber su latitud y longitud y asi
        //recojo todos los datos de este punto de venta, y estos datos los pinto
        //en el dialog
        var spinner:Spinner = dialog.findViewById(R.id.bioproducts_spinner)
        //esta lista son los bioproductos disponibles para este punto de venta
        //gettype1bytype2 coge el id del punto de venta y te da los productos para este
        var items = listOf("Productos","Polvo blanco","Polvo azul", "Polvo rojo","Polvo verde","Polvo amarillo","Polvo negro")
        val adapter = ArrayAdapter(this,  android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                val selectedItem = items[position]
                if(position!=0){
                    Toast.makeText(this@MapActivity,"$selectedItem",Toast.LENGTH_SHORT).show()
                    GlobalScope.launch {
                        var response = bioproduct_service.getByName(items[position])
                        if(response.isSuccessful){
                            runOnUiThread {
                            navigateToBioproductDialog(response.body()!!)
                            }

                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        dialog.show()
        return true
    }
    override fun onMapReady(googleMap: GoogleMap) {

        mGoogleMap = googleMap
        mGoogleMap?.setOnMarkerClickListener(this)
        mGoogleMap?.setInfoWindowAdapter(this)
        val initialPosition = LatLng(21.5218, -77.7812)
        mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(initialPosition))
        mGoogleMap!!.animateCamera(CameraUpdateFactory.zoomTo(5.0f))
        //esto debo meterlo en una funcion en el viewModel

        var sellpoints:List<SellPoint> = listOf<SellPoint>()

        lifecycleScope.launch {
            val sellPoints_response = sellPoint_service.getSellPoints()
            if(sellPoints_response.isSuccessful){
                sellpoints = sellPoints_response.body()!!
                Log.d("jc", "Entra a esta parte")
                runOnUiThread{
                    sellpoints.forEach{
                            item->
                        val location = LatLng(item.latitude, item.longitude)
                        val markerOptions:MarkerOptions = MarkerOptions()
                            .position(location)

                        googleMap.addMarker(MarkerOptions()
                            .position(location))

                    }
                }

            }
            else{
                Log.d("jc", "Entra al else")
                //show error message(definir una vista para errores).
            }
        }



    }
    override fun getInfoWindow(marker: Marker): View? {

        return null

    }

    fun showBioproducts(view: View) {
        Log.d("jc", "entro aqui, no es mongolico")
    }

    private fun navigateToBioproductDialog(bioproduct: Bioproducts) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_bioproduct)

        var bioproduct_dialog_name_tv: TextView = dialog.findViewById(R.id.bioproduct_dialog_name_tv)
        var bioproduct_dialog_summary_tv: TextView = dialog.findViewById(R.id.bioproduct_dialog_summary_tv)
        var bioproduct_dialog_description_tv: TextView = dialog.findViewById(R.id.bioproduct_dialog_description_tv)
        var bioproduct_dialog_iv: ImageView = dialog.findViewById(R.id.bioproduct_dialog_iv)
        var bioproduct_dialog_back_buttom: ImageView = dialog.findViewById((R.id.bioproduct_dialog_back_buttom))

        bioproduct_dialog_name_tv.text = bioproduct.name.toString()
        bioproduct_dialog_description_tv.text = bioproduct.specifications
        bioproduct_dialog_summary_tv.text = bioproduct.summary
        //Glide.with(bioproduct_dialog_iv.context).load(bioproduct.photo).into(bioproduct_dialog_iv)

        bioproduct_dialog_back_buttom.setOnClickListener { dialog.hide() }

        dialog.show()
    }

    private fun navigateToSellPointDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_point)
        dialog.show()
    }
}
