package com.example.labiofam_android.Services

import com.example.labiofam_android.api_model.Contact
import retrofit2.Response
import retrofit2.http.GET

interface ContactService {
    @GET("Contact/all")
    suspend fun getContacts(): Response<List<Contact>>
}