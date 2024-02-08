package com.example.labiofam_android.model

import com.example.labiofam_android.apiModel.Testimony
import com.example.labiofam_android.apiServices.BioproductService
import com.example.labiofam_android.apiServices.RetrofitHelper
import com.example.labiofam_android.apiServices.TestimonyService
import com.example.labiofam_android.contract.MainContract
import com.example.labiofam_android.contract.TestimonyContract
import retrofit2.Response

class TestimonyModel:TestimonyContract.TestimonyModel {
    var testimonyService = RetrofitHelper.getInstance().create(TestimonyService::class.java)
    override suspend fun getTestimonies(): Response<List<Testimony>> {
        return testimonyService.getTestimonies()
    }

}