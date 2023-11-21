package com.example.labiofam_android.view.contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R
import com.example.labiofam_android.Services.ContactService
import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.Services.SellPointService
import com.example.labiofam_android.api_model.Contact
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactsViewActivity : AppCompatActivity() {

    private  lateinit var contactsAdapter: ContactsAdapter

    private lateinit var rvContacts: RecyclerView
    val contact_service = RetrofitHelper.getInstance().create(ContactService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_view)
        initComponents()
        initUI()
    }


    private fun initComponents(){
        rvContacts = findViewById(R.id.rv_Contacts)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.leftarrow)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initUI() {

        var contacts:List<Contact> = listOf()
        GlobalScope.launch {
            if (contact_service.getContacts().isSuccessful){
                contacts = contact_service.getContacts().body()!!
                runOnUiThread {
                    contactsAdapter = ContactsAdapter(contacts)
                    rvContacts.layoutManager = LinearLayoutManager(this@ContactsViewActivity)
                    rvContacts.adapter = contactsAdapter
                }
            }
            else{
                //show error message.
            }
        }
    }
}