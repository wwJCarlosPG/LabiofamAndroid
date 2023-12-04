package com.example.labiofam_android.view.map

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.labiofam_android.R
import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.apiModel.SellPoint
import com.example.labiofam_android.contract.BioproductToSellPointContract
import com.example.labiofam_android.contract.MapContract
import com.example.labiofam_android.contract.SellPointToBioproductContract
import com.example.labiofam_android.model.BioproductToSellPointModel
import com.example.labiofam_android.model.MapModel
import com.example.labiofam_android.model.SellPointToBioproductModel
import com.example.labiofam_android.presenter.BioproductToSellPointPresenter
import com.example.labiofam_android.presenter.MapPresenter
import com.example.labiofam_android.presenter.SellPointToBioproductPresenter
import com.example.labiofam_android.view_interface.ViewInterface
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapActivity : ViewInterface,AppCompatActivity(),MapContract.MapView,BioproductToSellPointContract.BioproductToSellPointView, SellPointToBioproductContract.SellPointToBioproductView, OnMapReadyCallback, OnMarkerClickListener, InfoWindowAdapter {
    private lateinit var mGoogleMap: GoogleMap
    private lateinit var searchView:SearchView
    private lateinit var toolbar:Toolbar
    val map_model:MapContract.MapModel = MapModel()
    val sellPointToBioproductModel:SellPointToBioproductContract.SellPointToBioproductModel = SellPointToBioproductModel()
    val map_presenter = MapPresenter(this@MapActivity, map_model)
    val sellPointToBioproduct_presenter = SellPointToBioproductPresenter(this@MapActivity,sellPointToBioproductModel )
    val bioproductToSellPoint_model:BioproductToSellPointContract.BioproductToSellPointModel=BioproductToSellPointModel()
    val bioproductToSellpoint_presenter = BioproductToSellPointPresenter(this@MapActivity,bioproductToSellPoint_model)
    private lateinit var checkBoxAddress:CheckBox
    private lateinit var checkBoxBiop:CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        initComponents()
        initUI()
    }

    override fun initUI(){
        checkBoxBiop.isChecked = true
        checkBoxAddress.isChecked = false
        checkBoxBiop.setOnCheckedChangeListener { buttonView, isChecked ->
            checkBoxAddress.isChecked = !isChecked

        }
        checkBoxAddress.setOnCheckedChangeListener { buttonView, isChecked ->
            checkBoxBiop.isChecked = !isChecked

        }

    }


    override fun initComponents(){
        searchView = findViewById(R.id.search_map)
        toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        checkBoxBiop = findViewById(R.id.checkBox_bioproduct)
        checkBoxAddress = findViewById(R.id.checkBox_address)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment)
                as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchView!!.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                var query = newText.toString()
                if(query==""){
                    getAllSellPointsAddress()
                }
                else{
                    if(!checkBoxBiop.isChecked) {
                        getBySubstringAddress(query)
                    }
                    else{
                        getBySubstringByBioproduct(query)
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                var query = query.toString()
                if(query==""){
                    getAllSellPointsAddress()
                }
                else{
                        if(!checkBoxBiop.isChecked) {
                            getBySubstringAddress(query)
                        }
                        else{
                            getBySubstringByBioproduct(query)
                        }
                }
                return true
            }
        })

    }

    override fun showError(message: String) {
        Toast.makeText(this, "${message}", Toast.LENGTH_SHORT).show()
    }


    fun getAllSellPointsAddress(){

        lifecycleScope.launch(Dispatchers.IO) {
            val sellPoints_response = map_presenter.getSellPoints()
            if(sellPoints_response.isNotEmpty()){
                var sellpoints = sellPoints_response
                runOnUiThread{
                    mGoogleMap.clear()
                }
                    sellpoints.forEach{
                            item->
                        val location = LatLng(item.latitude, item.longitude)
                        runOnUiThread {
                        mGoogleMap.addMarker(MarkerOptions()
                            .position(location))
                        }

                    }


            }
            else{
                runOnUiThread{
                    mGoogleMap.clear()
                    showError("No se encuentra")
                }
            }
        }


    }
    fun getBySubstringByBioproduct(bioproduct: String){
        lifecycleScope.launch(Dispatchers.IO) {
            var sellPoint_by_biops = bioproductToSellpoint_presenter.getSellPointsByBiopSubstring(bioproduct)
            if(sellPoint_by_biops.isNotEmpty()){
                runOnUiThread{
                    mGoogleMap.clear()
                }
                sellPoint_by_biops.forEach { item->
                    runOnUiThread{
                        mGoogleMap.addMarker(MarkerOptions().position(LatLng(item.latitude, item.longitude)))
                    }
                }
            }
            else{
                runOnUiThread{
                    mGoogleMap.clear()
                    showError("No se encuentra")
                }
            }
        }
    }
    fun getBySubstringAddress(location:String){
        lifecycleScope.launch(Dispatchers.IO) {
            var sellPoints_by_address = map_presenter.getSellPointByAddress(location)
            if(sellPoints_by_address.isNotEmpty()){
                runOnUiThread{
                    mGoogleMap.clear()
                }
                sellPoints_by_address.forEach { item->
                    runOnUiThread{
                        mGoogleMap.addMarker(MarkerOptions().position(LatLng(item.latitude, item.longitude)))
                    }
                }
            }
            else{
                runOnUiThread{
                    mGoogleMap.clear()
                    showError("No se encuentra")
                }
            }
        }
    }
    override fun getInfoContents(marker: Marker):View?
    {
        return null
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        try{
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_point)
        var latitude = marker.position.latitude
        var longitude = marker.position.longitude
        lifecycleScope.launch(Dispatchers.IO) {
            var sellPoint = map_presenter.getByGeolocation(
                latitude, longitude
            )
            var bioproductsBySellPoint = sellPointToBioproduct_presenter.getBioproductsBySellPoint(sellPoint.id)
            withContext(Dispatchers.Main) {
                initSellPointDialog(dialog, sellPoint)
                var spinner: Spinner = dialog.findViewById(R.id.bioproducts_spinner)
                var items = mutableListOf<String>()
                items.add("Productos disponibles")
                bioproductsBySellPoint.forEach{biop->
                     items.add(biop.name)
                }
                val adapter = ArrayAdapter(
                    this@MapActivity,
                    android.R.layout.simple_spinner_item,
                    items
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedItem = items[position]
                        if (position != 0) {
                            Toast.makeText(
                                this@MapActivity,
                                "$selectedItem",
                                Toast.LENGTH_SHORT
                            ).show()
                            lifecycleScope.launch(Dispatchers.IO) {
                                var selectedBioproduct = bioproductsBySellPoint.firstOrNull { it.name==selectedItem }//aqui busco un bioproducto por el nombre
                                runOnUiThread {
                                    navigateToBioproductDialog(selectedBioproduct!!)
                                }
                            }
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
                dialog.show()
            }
        }
        }
        catch (e:Exception){
            showError("Error de conexión")
        }
        return true
    }
    fun initSellPointDialog(dialog:Dialog, sellPoint:SellPoint){
        var sellpoint_name_tv:TextView = dialog.findViewById(R.id.sellpoint_name_tv)
        sellpoint_name_tv.text = sellPoint.name
        var sellpoint_addrress_tv:TextView = dialog.findViewById(R.id.sellpoint_address)
        sellpoint_addrress_tv.text = sellPoint.address
        var sellpoint_province_tv:TextView = dialog.findViewById(R.id.sellpoint_province_tv)
        sellpoint_province_tv.text = sellPoint.province
        var sellpoint_municipality_tv:TextView = dialog.findViewById(R.id.sellpoint_municipality_tv)
        sellpoint_municipality_tv.text = sellPoint.province
    }
    override fun onMapReady(googleMap: GoogleMap) {

        mGoogleMap = googleMap
        mGoogleMap?.setOnMarkerClickListener(this)
        mGoogleMap?.setInfoWindowAdapter(this)
        val initialPosition = LatLng(21.5218, -77.7812)
        mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(initialPosition))
        mGoogleMap!!.animateCamera(CameraUpdateFactory.zoomTo(5.0f))
        lifecycleScope.launch(Dispatchers.IO){
                val sellPoints = map_presenter.getSellPoints()
                if(sellPoints.isNotEmpty()){
                    runOnUiThread{
                        sellPoints.forEach{
                                sp->
                            val location = LatLng(sp.latitude, sp.longitude)
                            mGoogleMap.addMarker(MarkerOptions()
                                .position(location))

                        }
                    }
                }
            else{
                showError("Error de conexión")
            }


            }





    }
    override fun getInfoWindow(marker: Marker): View? {

        return null

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
}
