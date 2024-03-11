package com.example.labiofam_android.view.map

import android.app.Dialog
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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.net.ssl.SSLHandshakeException

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.SupportMapFragment

class MapActivity : ViewInterface,AppCompatActivity(),MapContract.MapView,BioproductToSellPointContract.BioproductToSellPointView, SellPointToBioproductContract.SellPointToBioproductView
{
    // Declare a variable for MapView
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
    val key = "lypgVGvwohDCETMJ0WxP"
    val mapId = "streets-v2"
    private val style_url = "https://api.maptiler.com/maps/$mapId/style.json?key=$key"
    private lateinit var  map_box:MapboxMap
    private lateinit var map:MapView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this)
        val inflater = LayoutInflater.from(this)
        val root_view = inflater.inflate(R.layout.activity_map, null)
        setContentView(root_view)
        map = root_view.findViewById(R.id.mapFragment)
        initComponents()
        initUI()

    }


    override fun initUI() {
        checkBoxBiop.isChecked = true
        checkBoxAddress.isChecked = false
        checkBoxBiop.setOnCheckedChangeListener { buttonView, isChecked ->
            checkBoxAddress.isChecked = !isChecked

        }
        checkBoxAddress.setOnCheckedChangeListener { buttonView, isChecked ->
            checkBoxBiop.isChecked = !isChecked

        }

    }


    override fun initComponents() {
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
        map.getMapAsync { map_ ->
            map_box = map_
            map_.setStyle(style_url)
            map_.cameraPosition = CameraPosition.Builder().target(
                com.mapbox.mapboxsdk.geometry.LatLng(
                    21.5218, -77.7812
                )
            ).zoom(5.0).build()
            lifecycleScope.launch(Dispatchers.IO){
                try {
                    val sellPoints = map_presenter.getSellPoints()
                    if (sellPoints.isNotEmpty()) {
                        runOnUiThread {
                            sellPoints.forEach { sp ->
                                val location = LatLng(sp.latitude, sp.longitude)
                                map_.addMarker(
                                    MarkerOptions()
                                        .position(location)
                                )

                            }
                        }
                    } else {
                        showError("No hay puntos de ventas que mostrar")
                    }
                }
                catch (ex:Exception){
                    showError("Error de conexión")
                }

            }

            map_.setOnMarkerClickListener { marker ->
                onMarkerClick(marker)
                true
            }

        }
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
    fun getAllSellPointsAddress(){

        lifecycleScope.launch(Dispatchers.IO) {
            val sellPoints_response = map_presenter.getSellPoints()
            if(sellPoints_response.isNotEmpty()){
                var sellpoints = sellPoints_response
                runOnUiThread{
                    map_box.clear()
                }
                sellpoints.forEach{
                        item->
                    val location = LatLng(item.latitude, item.longitude)
                    runOnUiThread {
                        val marker_options:MarkerOptions = MarkerOptions()
                            .position(location)
                        map_box.addMarker(marker_options)
                    }

                }


            }
            else{
                runOnUiThread{
                    map_box.clear()
                    showError("No se encuentra")
                }
            }
        }


    }
    override fun onStart() {
        super.onStart()
        map.onStart()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map.onSaveInstanceState(outState)
    }



    override fun showError(message: String) {
        runOnUiThread{

            Toast.makeText(this@MapActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getBySubstringByBioproduct(bioproduct: String){
        lifecycleScope.launch(Dispatchers.IO) {
            var sellPoint_by_biops = bioproductToSellpoint_presenter.getSellPointsByBiopSubstring(bioproduct)
            if(sellPoint_by_biops.isNotEmpty()){
                runOnUiThread{
                    map_box.clear()
                }
                sellPoint_by_biops.forEach { item->
                    runOnUiThread{
                        map_box.addMarker(MarkerOptions().position(LatLng(item.latitude, item.longitude)))
                    }
                }
            }
            else{
                runOnUiThread{
                    map_box.clear()

                }
                Log.d("NO HAY POR SUBSTRING", "NO HAY")
                showError("No se encuentra")
            }
        }
    }
    fun getBySubstringAddress(location:String){
        lifecycleScope.launch(Dispatchers.IO) {
            var x = 0
            var sellPoints_by_address = map_presenter.getSellPointByAddress(location)
            if(sellPoints_by_address.isNotEmpty()){
                runOnUiThread{
                    map_box.clear()
                }
                sellPoints_by_address.forEach { item->
                    runOnUiThread{
                        map_box.addMarker(MarkerOptions().position(LatLng(item.latitude, item.longitude)))
                    }
                }
            }
            else{
                runOnUiThread {
                    map_box.clear()
                }
                Log.d("Entra a que no hay", "NO HAY")
                showError("No se encuentra")

            }
        }
    }

    fun onMarkerClick(marker: Marker): Boolean {
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
                    items.add("Productos")
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
}
