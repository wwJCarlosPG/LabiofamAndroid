package com.example.labiofam_android.apiServices

import com.example.labiofam_android.apiModel.Testimony
import retrofit2.Response
import retrofit2.http.GET

interface TestimonyService {
    @GET("Testimonie/all")
    suspend fun getTestimonies(): Response<List<Testimony>>
}