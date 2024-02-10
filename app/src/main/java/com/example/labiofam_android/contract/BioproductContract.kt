package com.example.labiofam_android.contract

import com.example.labiofam_android.apiModel.Bioproducts
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.http.Body

interface BioproductContract {
    interface  BioproductView{
        fun showBioproducts(bioproducts: List<Bioproducts>)

    }
    interface BioproductPresenter{
        suspend fun getBioproducts():List<Bioproducts>
        suspend fun getBioproductBySubstring(substring:String):List<Bioproducts>
    }
    interface BioproductModel{
        suspend fun getBioproducts(): Response<List<Bioproducts>>
        suspend fun getBioproductsBySubstring(substring:String):Response<List<Bioproducts>>
    }
}