package com.example.labiofam_android.contract

import com.example.labiofam_android.api_model.Bioproducts
import retrofit2.Response

interface BioproductContract {
    interface  BioproductView{
        fun showBioproducts(bioproducts: List<Bioproducts>)

    }
    interface BioproductPresenter{
        suspend fun getBioproducts():List<Bioproducts>
        suspend fun getBioproductBySubstring(substring:String)
    }
    interface BioproductModel{
        suspend fun getBioproducts(): Response<List<Bioproducts>>
        suspend fun getBioproductsBySubstring(substring:String):Response<List<Bioproducts>>

    }
}