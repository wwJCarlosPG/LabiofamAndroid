package com.example.labiofam_android.view.home

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.labiofam_android.R
import com.example.labiofam_android.apiModel.Bioproducts
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.net.ssl.SSLHandshakeException

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
        try {

            lifecycleScope.launch(Dispatchers.IO) {
                    runOnUiThread {
                        Glide.with(random_product_iv1.context).load(bioproducts[0].image)
                            .into(random_product_iv1)
                        Glide.with(random_product_iv2.context).load(bioproducts[1].image)
                            .into(random_product_iv2)
                        Glide.with(random_product_iv3.context).load(bioproducts[2].image)
                            .into(random_product_iv3)
                    }
            }
        }
        catch (e:Exception){
            showError("Error de conexión")
        }
    }



    override fun initUI() {

        lifecycleScope.launch(Dispatchers.IO){
            try {
                var x = 0
                bioproducts = main_presenter.getRandomBioproducts()
                if(bioproducts!= null && bioproducts.isNotEmpty() && bioproducts.size == 3){

                    showRandomBioproducts(bioproducts)
                }
                else{
                    showError("Error de conexión")
                }

            }
            catch (ex:Exception){
                showError("Error de conexion con el servidor")
            }

        }


    }

    override fun initComponents() {
        try{
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
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.nav_bottom_menu)
        bottomNavigation.setOnItemSelectedListener {selectedItem->
            when(selectedItem.itemId){
                R.id.telegram -> {
                    val telegramUsername = R.string.telegram_account
                    val telegramIntent = Intent(Intent.ACTION_VIEW)
                    telegramIntent.data = Uri.parse("https://t.me/${telegramUsername}")
                    telegramIntent.setPackage("org.telegram.messenger")
                    try {
                        startActivity(telegramIntent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(this, "La aplicación de Telegram no está instalada", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.linkedin -> {
                    val linkedinUsername = getString(R.string.linkedin_account)
                    val linkedinIntent = Intent(Intent.ACTION_VIEW)
                    linkedinIntent.data = Uri.parse("https://www.linkedin.com/in/$linkedinUsername")
                    linkedinIntent.setPackage("com.linkedin.android")
                    try {
                        startActivity(linkedinIntent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(this, "La aplicación de LinkedIn no está instalada", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.facebook -> {
                    val facebookUsername = getString(R.string.facebook_account)
                    val facebookIntent = Intent(Intent.ACTION_VIEW)
                    facebookIntent.data = Uri.parse("https://www.facebook.com/$facebookUsername")
                    facebookIntent.setPackage("com.facebook.katana")
                    try {
                        startActivity(facebookIntent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(this, "La aplicación de Facebook no está instalada", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> {false
                }
            }

            }
        }
        catch (e:Exception){
            showError("Error de conexión")
        }
    }
    override fun showError(message: String) {
        runOnUiThread{

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

    }


}
