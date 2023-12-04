package com.example.labiofam_android.apiServices

import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.apiModel.SellPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BioproductToSellPointService {
    @GET("ProductPOSFilter/gettype2bytype1")
    suspend fun getSellPointsByBioproduct(@Query("type1_id") sellPoint_id:String): Response<List<SellPoint>>

}