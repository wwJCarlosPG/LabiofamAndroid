package com.example.labiofam_android.model

import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.Services.SellPointToBioproductService
import com.example.labiofam_android.api_model.Bioproducts
import com.example.labiofam_android.api_model.SellPoint
import com.example.labiofam_android.contract.SellPointToBioproductContract
import retrofit2.Response

class SellPointToBioproductModel:SellPointToBioproductContract.SellPointToBioproductModel {
    var bioproduct_by_sellPoint_service = RetrofitHelper.getInstance().create(SellPointToBioproductService::class.java)
    override suspend fun getBioproductsBySellPoint(id: String): Response<List<Bioproducts>> {
        return bioproduct_by_sellPoint_service.getBioproductsBySellPoint(id)
    }
}