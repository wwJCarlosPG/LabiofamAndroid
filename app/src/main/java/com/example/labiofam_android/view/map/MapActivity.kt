package com.example.labiofam_android.view.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.labiofam_android.R
import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.Services.SellPointService
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

        GlobalScope.launch {
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
        //Aqui retorno lo de los bioproductos en el punto de venta
        //convierte la vista en un objeto
        //marker.position.latitude de esta manera puedo identificar
        //marker.id tambien existe pero no me queda claro que tan util sea

        val view = layoutInflater.inflate(R.layout.dialog_point, null)
        //obtiene info del marcador que se pasa como parametro
        val info = "aqui la api"
        val infoTextView = view.findViewById<TextView>(R.id.sell_point_name_tv)
        val infoTextView2 = view.findViewById<TextView>(R.id.sell_point_province_tv)
        infoTextView.text = info
        infoTextView2.text = "Valencia"

        return view
    }

    override fun onMarkerClick(marker:Marker):Boolean
    {
        marker.showInfoWindow()
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
        GlobalScope.launch {
            val sellPoints_response = sellPoint_service.getSellPoints()
            if(sellPoints_response.isSuccessful){
                sellpoints = sellPoints_response.body()!!
                Log.d("jc", "Entra a esta parte")
                runOnUiThread{
                    sellpoints.forEach{
                            item->
                        val location = LatLng(item.latitude, item.longitude)
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
}
