package com.example.labiofam_android.Services

import android.telecom.Call
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url
import javax.security.auth.Subject

interface FeedbackService {
    @FormUrlEncoded
    @POST("Mail/")
    suspend fun sendEmail(@Field("subject") subject: String,
                          @Field("message") message: String):retrofit2.Call<ResponseBody>
}