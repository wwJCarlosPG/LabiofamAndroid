package com.example.labiofam_android.view.contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R
import com.example.labiofam_android.Services.ContactService
import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.Services.SellPointService
import com.example.labiofam_android.api_model.Contact
import com.example.labiofam_android.contract.ContactContract
import com.example.labiofam_android.model.ContactModel
import com.example.labiofam_android.presenter.ContactPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactsViewActivity : AppCompatActivity(), ContactContract.ContactView {

    private  lateinit var contactsAdapter: ContactsAdapter

    private lateinit var rvContacts: RecyclerView
    val contact_model = ContactModel()
    var contact_presenter:ContactContract.ContactPresenter = ContactPresenter(this@ContactsViewActivity,
        contact_model)
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
        lifecycleScope.launch(Dispatchers.IO) {
            contacts = contact_presenter.getContacts()
            if(contacts.isNotEmpty()){
                showContacts(contacts)
            }
            else{
                //showError.
            }
        }

    }




    override fun showContacts(contacts:List<Contact>) {
        runOnUiThread {
            contactsAdapter = ContactsAdapter(contacts)
            rvContacts.layoutManager = LinearLayoutManager(this@ContactsViewActivity)
            rvContacts.adapter = contactsAdapter
        }
    }

}