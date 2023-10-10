package com.example.labiofam_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener, InfoWindowAdapter {
    private var mGoogleMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment)
                as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun getInfoContents(marker: Marker):View?
    {
        //convierte la vista en un objeto
        //marker.position.latitude de esta manera puedo identificar
        //marker.id tambien existe pero no me queda claro que tan util sea
        val view = layoutInflater.inflate(R.layout.activity_map, null)
        //obtiene info del marcador que se pasa como parametro
        val info = "aqui recibo la informacion de la api"
        val infoTextView = view.findViewById<TextView>(R.id.pointInfo)
        infoTextView.text = info
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
        //aqui deberia recibir una lista de localizaciones y pintarlas todas
        val location1 = LatLng(20.02083, -75.82667)
        val location2 = LatLng(22.41667, -83.69667)
        val marker1 = googleMap.addMarker(MarkerOptions().position(location1).title("San Francisco"))
        var marker2 = googleMap.addMarker(MarkerOptions().position(location2).title("Another place"))
    //marker.setIcon() aqui puedo cambiar el icono.

    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}
