package com.example.labiofam_android.apiServices
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
//android.icu.util
object RetrofitHelper {

    val baseUrl = "http://10.0.2.2:5263/api/"
    //val baseUrl = "http://192.168.153.205:5263/api/"
    //val baseUrl = "http://localhost:5263/api/"
    //cuado se esta probando en emulador se pone esa direccion ip en vez de localhost
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .callFactory(OkHttpClient.Builder().callTimeout(30,TimeUnit.SECONDS).build())
            .build()
        // we need to add converter factory to
        // convert JSON object to Java object

    }
}