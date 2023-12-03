package com.example.labiofam_android.model

import com.example.labiofam_android.Services.BioproductService
import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.Services.SellPointService
import com.example.labiofam_android.api_model.SellPoint
import com.example.labiofam_android.contract.MapContract
import retrofit2.Response

class MapModel: MapContract.MapModel {
    var sellPoint_service = RetrofitHelper.getInstance().create(SellPointService::class.java)
    override suspend fun getSellPoints(): Response<List<SellPoint>> {
        return sellPoint_service.getSellPoints()
    }
    override suspend fun getSellPointByAddress(substring:String):Response<List<SellPoint>>{
        return sellPoint_service.getSellPointsByAddress(substring)
    }
}