package com.example.labiofam_android.Services

import android.telecom.Call
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url
import javax.security.auth.Subject

interface FeedbackService {

    @POST("Mail/{subject}/{message}")
    suspend fun sendEmail(@Path("subject") subject: String,
                          @Path("message") message: String): Response<Unit>
}