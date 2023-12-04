package com.example.labiofam_android.model

import com.example.labiofam_android.apiServices.ContactService
import com.example.labiofam_android.apiServices.RetrofitHelper
import com.example.labiofam_android.apiModel.Contact
import com.example.labiofam_android.contract.ContactContract
import retrofit2.Response

class ContactModel:ContactContract.ContactModel {
    private var contact_service = RetrofitHelper.getInstance().create(ContactService::class.java)
    override suspend fun getContacts(): Response<List<Contact>> {
        return contact_service.getContacts()
    }
}