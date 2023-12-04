package com.example.labiofam_android.apiServices

import com.example.labiofam_android.apiModel.SellPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SellPointService {
    @GET("PointOfSales/all")
    suspend fun getSellPoints(): Response<List<SellPoint>>

    @GET("PointOfSales/getbysubstring/{substring}")
    suspend fun getSellPointsByAddress(@Path("substring") address:String):Response<List<SellPoint>>
}