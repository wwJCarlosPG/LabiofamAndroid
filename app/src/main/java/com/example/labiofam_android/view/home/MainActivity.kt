package com.example.labiofam_android.view.home

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.labiofam_android.R
import com.example.labiofam_android.Services.BioproductService
import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.view.map.MapActivity
import com.example.labiofam_android.view.testimony.TestimonialsActivity
import com.example.labiofam_android.view.bioproduct.BioproductsActivity
import com.example.labiofam_android.view.contact.ContactsViewActivity
import com.example.labiofam_android.view.feedback.FeedbackActivity
import com.example.labiofam_android.view.service.ServicesActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var random_product_iv1:ImageView
    private lateinit var random_product_iv2:ImageView
    private lateinit var random_product_iv3:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        random_product_iv1 = findViewById(R.id.random_iv1)
        random_product_iv2 = findViewById(R.id.random_iv2)
        random_product_iv3 = findViewById(R.id.random_iv3)
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView:NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val bioproduct_service = RetrofitHelper.getInstance().create(BioproductService::class.java)

        /*GlobalScope.launch {
            try {

                var response = bioproduct_service.getBioproducts()
                if(response.isSuccessful){
                    val random1:Int = Random.nextInt(1,response.body()!!.size-2)
                    val random2 = 0
                    val random3:Int = response.body()!!.size-1
                    runOnUiThread{
                        Glide.with(random_product_iv1.context).load(response.body()!![random1].image).into(random_product_iv1)
                        Glide.with(random_product_iv2.context).load(response.body()!![random2].image).into(random_product_iv2)
                        Glide.with(random_product_iv3.context).load(response.body()!![random3].image).into(random_product_iv3)
                    }

                }
                else{
                    //show dialog
                }
            }
            catch (e: Exception){
                    //show dialog
            }
        }*/
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_item_biop ->{
                Toast.makeText(this, "Bioproductos", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, BioproductsActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_item_pv -> {
                Toast.makeText(this, "Puntos de Venta", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_item_contacts -> {
                Toast.makeText(this, "Contactos", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ContactsViewActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_item_services -> {
                Toast.makeText(this, "Servicios", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ServicesActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_item_feedback -> {
                Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, FeedbackActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_item_testimonials -> {
                Toast.makeText(this, "Testimonios", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TestimonialsActivity::class.java)
                startActivity(intent)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
