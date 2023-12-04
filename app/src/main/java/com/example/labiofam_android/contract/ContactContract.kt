package com.example.labiofam_android.contract

import com.example.labiofam_android.api_model.Contact
import retrofit2.Response

interface ContactContract {
    interface  ContactModel{
        suspend fun getContacts(): Response<List<Contact>>
    }
    interface  ContactView{
        fun showContacts(contacts:List<Contact>)
    }
    interface  ContactPresenter{
        suspend fun getContacts():List<Contact>
    }

}