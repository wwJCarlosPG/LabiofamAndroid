package com.example.labiofam_android.apiServices
import android.annotation.SuppressLint
import android.content.Context


import com.example.labiofam_android.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import android.content.res.Resources
import androidx.core.content.res.TypedArrayUtils.getString
import kotlinx.coroutines.withContext
//android.icu.util
object RetrofitHelper {


    val baseUrl = "http://10.0.2.2:5263/api/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .callFactory(OkHttpClient.Builder().callTimeout(60,TimeUnit.SECONDS).build())
            .build()

    }
}