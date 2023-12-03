package com.example.labiofam_android.Services

import com.example.labiofam_android.api_model.Bioproducts
import com.example.labiofam_android.api_model.SellPoint
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface SellPointToBioproductService {
    @GET("ProductPOSFilter/gettype1bytype2")
    suspend fun getBioproductsBySellPoint(@Query("type2_id") sellPoint_id:String): Response<List<Bioproducts>>
}