package com.example.labiofam_android.model

import com.example.labiofam_android.apiServices.BioproductService
import com.example.labiofam_android.apiServices.RetrofitHelper
import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.contract.BioproductContract
import retrofit2.Response

class BioproductModel:BioproductContract.BioproductModel {
    private var bioproduct_service = RetrofitHelper.getInstance().create(BioproductService::class.java)
    override suspend fun getBioproducts(): Response<List<Bioproducts>> {
        return bioproduct_service.getBioproducts()
    }

    override suspend fun getBioproductsBySubstring(substring: String): Response<List<Bioproducts>> {
        return bioproduct_service.getBySubstring(substring)
    }

}