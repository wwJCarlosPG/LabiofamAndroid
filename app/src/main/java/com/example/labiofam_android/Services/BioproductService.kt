package com.example.labiofam_android.Services

import com.example.labiofam_android.api_model.Bioproducts
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path

interface BioproductService {

    @GET("Product/all")
    suspend fun getBioproducts()

    @GET("Product/take/")
    suspend fun getBioproductsWithPagination(@Field("size") size:Int)

    @FormUrlEncoded
    @GET("Product/getbysubstring/")
    suspend fun getBySubstring(@Field("substring") substring:String):Response<List<Bioproducts>>
}