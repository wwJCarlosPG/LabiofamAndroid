package com.example.labiofam_android.contract

import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.apiModel.Testimony
import retrofit2.Response

interface TestimonyContract {
    interface  TestimonyView{

    }
    interface  TestimonyModel{
        suspend fun getTestimonies(): Response<List<Testimony>>
    }
    interface  TestimonyPresenter{
        suspend fun getVideos():List<Testimony>
        suspend fun getImages():List<Testimony>
    }
}