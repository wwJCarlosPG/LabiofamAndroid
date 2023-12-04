package com.example.labiofam_android.model


import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.apiModel.SellPoint
import com.example.labiofam_android.apiServices.BioproductService
import com.example.labiofam_android.apiServices.BioproductToSellPointService
import com.example.labiofam_android.apiServices.RetrofitHelper
import com.example.labiofam_android.contract.BioproductToSellPointContract
import retrofit2.Response
class BioproductToSellPointModel: BioproductToSellPointContract.BioproductToSellPointModel {
    var bioproduct_by_sellPoint_service = RetrofitHelper.getInstance().create(
        BioproductToSellPointService::class.java)
    var bioproduct_service = RetrofitHelper.getInstance().create(
        BioproductService::class.java)

    override suspend fun getSellPointsByBioproduct(id: String): Response<List<SellPoint>> {
        return bioproduct_by_sellPoint_service.getSellPointsByBioproduct(id)
    }
    override suspend fun getBioproductsBySubstring(substring: String): Response<List<Bioproducts>> {
        return bioproduct_service.getBySubstring(substring)
    }
}