package com.example.labiofam_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenActivity = findViewById<Button>(R.id.pv_btn)
        btnOpenActivity.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        val btnOpenContactsActivity = findViewById<Button>(R.id.contacts_btn)
        val btnOpenByproductsActivity = findViewById<Button>(R.id.byProducts_btn)
        val btnOpenServicesActivity = findViewById<Button>(R.id.services_btn)

        btnOpenContactsActivity.setOnClickListener { navigateToContactsActivity() }
        btnOpenByproductsActivity.setOnClickListener { navigateToByproductsActivity() }
        btnOpenServicesActivity.setOnClickListener { navigateToServicesActivity() }
    }

    private fun navigateToServicesActivity() {
        val intent = Intent(this, ServicesActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToByproductsActivity() {
        val intent = Intent(this, ByProductsActivity::class.java)
        startActivity(intent)
    }


    private fun navigateToContactsActivity(){
        val intent = Intent(this, ContactsViewActivity::class.java)
        startActivity(intent)
    }
}