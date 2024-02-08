package com.example.labiofam_android.apiServices

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedbackService {

    @POST("Mail")
    @FormUrlEncoded
    suspend fun sendEmail(
        @Field("sender_name") sender_name: String,
        @Field("sender_email") sender_email: String,
        @Field("subject") subject: String,
        @Field("message") message: String
    ): Response<Unit>

    @POST("Registration/login")
    suspend fun logIn(@Body request:JsonObject)



}