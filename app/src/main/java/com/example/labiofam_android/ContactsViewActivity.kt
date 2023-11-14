package com.example.labiofam_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactsViewActivity : AppCompatActivity() {

    private  lateinit var contactsAdapter: ContactsAdapter

    private lateinit var rvContacts: RecyclerView
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

        contactsAdapter = ContactsAdapter(ContactsProvider.contactsList)
        rvContacts.layoutManager = LinearLayoutManager(this)
        rvContacts.adapter = contactsAdapter
    }
}