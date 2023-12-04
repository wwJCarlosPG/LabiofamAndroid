package com.example.labiofam_android.apiServices

import com.example.labiofam_android.apiModel.Contact
import retrofit2.Response
import retrofit2.http.GET

interface ContactService {
    @GET("Contact/all")
    suspend fun getContacts(): Response<List<Contact>>
}