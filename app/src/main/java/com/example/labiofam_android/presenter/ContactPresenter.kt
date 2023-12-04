package com.example.labiofam_android.presenter

import com.example.labiofam_android.apiModel.Contact
import com.example.labiofam_android.contract.ContactContract

class ContactPresenter(contactView:ContactContract.ContactView, contactModel: ContactContract.ContactModel):ContactContract.ContactPresenter {
    private var contactView = contactView
    private var contactModel = contactModel
    private var defaultContacts = listOf<Contact>()
    override suspend fun getContacts(): List<Contact> {
        var response = contactModel.getContacts()
        if(response.isSuccessful){
            return response.body()!!
        }
        else{
            return defaultContacts
        }
    }
}