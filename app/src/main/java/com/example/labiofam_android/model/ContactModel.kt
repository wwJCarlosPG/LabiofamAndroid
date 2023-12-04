package com.example.labiofam_android.model

import com.example.labiofam_android.Services.ContactService
import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.api_model.Contact
import com.example.labiofam_android.contract.ContactContract
import retrofit2.Response
import retrofit2.create

class ContactModel:ContactContract.ContactModel {
    private var contact_service = RetrofitHelper.getInstance().create(ContactService::class.java)
    override suspend fun getContacts(): Response<List<Contact>> {
        return contact_service.getContacts()
    }
}