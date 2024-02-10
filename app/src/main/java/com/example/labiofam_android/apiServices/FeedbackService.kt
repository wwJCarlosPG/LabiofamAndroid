package com.example.labiofam_android.apiServices

import com.example.labiofam_android.apiModel.LogResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedbackService {

    @POST("Mail")
    suspend fun sendEmail(
        @Query("sender_name") sender_name: String,
        @Query("sender_email") sender_email: String,
        @Query("subject") subject: String,
        @Query("message") message: String,
        @Header("Authorization") auth:String
    ): Response<Unit>

    @POST("Registration/login")
    suspend fun logIn(@Body request:JsonObject):Response<LogResponse>



}