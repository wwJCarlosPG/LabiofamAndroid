package com.example.labiofam_android.Services

import com.example.labiofam_android.api_model.Bioproducts
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path

interface BioproductService {

    @GET("Product/all")
    suspend fun getBioproducts():Response<List<Bioproducts>>

    @GET("Product/take/{size}")
    suspend fun getBioproductsWithPagination(@Path("size") size:Int)

    @GET("Product/getbysubstring/{substring}")
    suspend fun getBySubstring(@Path("substring") substring:String):Response<List<Bioproducts>>

    @GET("Product/name/{name}")
    suspend fun getByName(@Path("name") name:String):Response<Bioproducts>
}