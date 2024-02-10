package com.example.labiofam_android.presenter

import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.apiModel.Testimony
import com.example.labiofam_android.contract.MainContract
import com.example.labiofam_android.contract.TestimonyContract

class TestimonyPresenter(testimonyView: TestimonyContract.TestimonyView,
                         testimonyModel: TestimonyContract.TestimonyModel): TestimonyContract.TestimonyPresenter {
    var testimonyView = testimonyView
    var testimonyModel = testimonyModel
    var result:List<Testimony> = listOf()
    override suspend fun getVideos(): List<Testimony> {
        var res = testimonyModel.getTestimonies()
        if(res.isSuccessful){
            result = res.body()!!
            result = result.filter { it.videoUrl!=null }
        }
        return result
    }
    override suspend fun getImages(): List<Testimony>{
        var res = testimonyModel.getTestimonies()
        if(res.isSuccessful){
            result = res.body()!!
            result = result.filter { it.image!=null }
        }
        return result
    }


}