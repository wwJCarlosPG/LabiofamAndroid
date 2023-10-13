package com.example.labiofam_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactsViewActivity : AppCompatActivity() {

    private val contactsCategories = listOf(
        ContactsCategories.Comercial,
        ContactsCategories.CorreoInstitucional,
        ContactsCategories.Other
    )

    private val contacts = mutableListOf(
        Contact("Paco Miranda", "45", "Inspector", ContactsCategories.Comercial, 59221941),
        Contact("Mariano Moreno", "43", "Inspector", ContactsCategories.CorreoInstitucional, 55814309),
        Contact("Lucas Fernández", "33", "Inspector", ContactsCategories.Other, 58454599)
    )

    private lateinit var rvCategories: RecyclerView
    private lateinit var contactsCategoriesAdapter: ContactsCategoriesAdapter

    private  lateinit var contactsAdapter: ContactsAdapter

    private lateinit var rvContacts: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_view)
        initComponents()
        initUI()
    }


    private fun initComponents(){
        rvCategories = findViewById(R.id.rv_Categories)
        rvContacts = findViewById(R.id.rv_Contacts)
    }

    private fun initUI() {
        contactsCategoriesAdapter = ContactsCategoriesAdapter(contactsCategories)
        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = contactsCategoriesAdapter

        contactsAdapter = ContactsAdapter(contacts)
        rvContacts.layoutManager = LinearLayoutManager(this)
        rvContacts.adapter = contactsAdapter
    }
}