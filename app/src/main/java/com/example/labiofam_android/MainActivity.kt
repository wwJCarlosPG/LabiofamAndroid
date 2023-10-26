package com.example.labiofam_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContactsProvider.contactsList
        setContentView(R.layout.activity_main)

        val btnOpenActivity = findViewById<Button>(R.id.pv_btn)
        btnOpenActivity.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        val btnOpenActivity2 = findViewById<Button>(R.id.services_btn)
        btnOpenActivity2.setOnClickListener {
            val intent = Intent(this, ServicesActivity::class.java)
            startActivity(intent)
        }

        val btnOpenContactsActivity = findViewById<Button>(R.id.contacts_btn)
        val btnOpenBioproductsActivity = findViewById<Button>(R.id.bioproducts_btn)

        btnOpenBioproductsActivity.setOnClickListener { navigateToBioproductsActivity() }
        btnOpenContactsActivity.setOnClickListener { navigateToContactsActivity() }
    }

    private fun navigateToBioproductsActivity() {
        val intent = Intent(this, BioproductsActivity::class.java)
        startActivity(intent)
    }

   // private fun navigateToServicesActivity() {
       // val intent = Intent(this, ServicesActivity::class.java)
     //   startActivity(intent)
   // }




    private fun navigateToContactsActivity(){
        val intent = Intent(this, ContactsViewActivity::class.java)
        startActivity(intent)
    }

    fun openServices(view: View) {}
}