package com.example.labiofam_android.Services

import com.example.labiofam_android.SellPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface SellPointService {
    @GET
    fun getSellPoints(@Url url:String):Response<SellPointResponse>
}