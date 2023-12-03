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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.labiofam_android.R
import com.example.labiofam_android.api_model.Bioproducts
import com.example.labiofam_android.contract.MainContract
import com.example.labiofam_android.model.MainModel
import com.example.labiofam_android.presenter.MainPresenter
import com.example.labiofam_android.view.map.MapActivity
import com.example.labiofam_android.view.testimony.TestimonialsActivity
import com.example.labiofam_android.view.bioproduct.BioproductsActivity
import com.example.labiofam_android.view.contact.ContactsViewActivity
import com.example.labiofam_android.view.feedback.FeedbackActivity
import com.example.labiofam_android.view.service.ServicesActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.labiofam_android.view_interface.ViewInterface

class MainActivity : AppCompatActivity(), MainContract.View,ViewInterface, NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var random_product_iv1:ImageView
    private lateinit var random_product_iv2:ImageView
    private lateinit var random_product_iv3:ImageView
    private lateinit var bioproducts:MutableList<Bioproducts>
    private var mainModel: MainModel = MainModel()
    private var main_presenter = MainPresenter(this, mainModel)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initUI()
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



    override fun showRandomBioproducts(bioproducts: MutableList<Bioproducts>) {
        lifecycleScope.launch(Dispatchers.IO){
            runOnUiThread{
                Glide.with(random_product_iv1.context).load(bioproducts[0].image).into(random_product_iv1)
                Glide.with(random_product_iv2.context).load(bioproducts[1].image).into(random_product_iv2)
                Glide.with(random_product_iv3.context).load(bioproducts[2].image).into(random_product_iv3)
            }
        }
    }

    override fun showError(message: String) {
        runOnUiThread{
            Toast.makeText(this@MainActivity, bioproducts[0].name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun initUI() {

        lifecycleScope.launch(Dispatchers.IO){
            bioproducts = main_presenter.getRandomBioproducts()
            showRandomBioproducts(bioproducts)

        }
        //val dialog = Dialog(this@MainActivity)
                //dialog.setContentView(R.layout.dialog_bioproduct)
                //var error_dialog_tv: TextView = dialog.findViewById(R.id.error_dialog_tv)
                //var error_dialog_backbtn = dialog.findViewById<ImageView>(R.id.error_dialog_back_btn)
                //error_dialog_tv.text = "ERROR MADANFAKA"
                //error_dialog_backbtn.setOnClickListener { dialog.hide() }

                //dialog.show()

    }

    override fun initComponents() {
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

    }


}
