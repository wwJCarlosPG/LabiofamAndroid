package com.example.labiofam_android.model

import android.util.Log
import com.example.labiofam_android.apiServices.BioproductService
import com.example.labiofam_android.apiServices.RetrofitHelper
import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.contract.MainContract
import kotlin.random.Random
import retrofit2.Response
import javax.net.ssl.SSLHandshakeException


class MainModel: MainContract.Model {
    var bioproductService = RetrofitHelper.getInstance().create(BioproductService::class.java)
    override suspend fun getRandomBioproducts(): Response<List<Bioproducts>>{
           return  bioproductService.getBioproducts()
    }

    override suspend fun getBioproductsByName(name: String): Response<Bioproducts> {
        return bioproductService.getByName(name)
    }


}