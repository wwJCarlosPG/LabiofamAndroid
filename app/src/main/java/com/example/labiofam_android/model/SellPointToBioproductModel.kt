package com.example.labiofam_android.model

import com.example.labiofam_android.apiServices.RetrofitHelper
import com.example.labiofam_android.apiServices.SellPointToBioproductService
import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.contract.SellPointToBioproductContract
import retrofit2.Response

class SellPointToBioproductModel:SellPointToBioproductContract.SellPointToBioproductModel {
    var bioproduct_by_sellPoint_service = RetrofitHelper.getInstance().create(SellPointToBioproductService::class.java)
    override suspend fun getBioproductsBySellPoint(id: String): Response<List<Bioproducts>> {
        return bioproduct_by_sellPoint_service.getBioproductsBySellPoint(id)
    }
}