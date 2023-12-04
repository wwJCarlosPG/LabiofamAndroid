package com.example.labiofam_android.apiServices

import com.example.labiofam_android.apiModel.Bioproducts
import retrofit2.Response
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