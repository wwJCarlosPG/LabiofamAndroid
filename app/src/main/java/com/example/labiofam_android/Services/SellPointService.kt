package com.example.labiofam_android.Services

import com.example.labiofam_android.api_model.SellPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SellPointService {
    @GET("PointOfSales/all")
    suspend fun getSellPoints(): Response<List<SellPoint>>

    @GET("PointOfSales/getbysubstring/")
    suspend fun getSellPointsByAddress(@Query("substring") address:String):Response<List<SellPoint>>
}