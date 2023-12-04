package com.example.labiofam_android.apiServices

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedbackService {

    @POST("Mail/{subject}/{message}")
    suspend fun sendEmail(@Path("subject") subject: String,
                          @Path("message") message: String): Response<Unit>
}