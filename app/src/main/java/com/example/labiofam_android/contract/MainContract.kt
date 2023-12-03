package com.example.labiofam_android.contract

import com.example.labiofam_android.api_model.Bioproducts
import retrofit2.Response

interface MainContract {
    interface  View{
        fun showRandomBioproducts(bioproducts:MutableList<Bioproducts>)
        fun showError(message:String)

    }
    interface Presenter{
        suspend fun getRandomBioproducts():MutableList<Bioproducts>


    }
    interface  Model{
        suspend fun getRandomBioproducts():Response<List<Bioproducts>>
        suspend fun getBioproductsByName(name:String):Response<Bioproducts>
    }
}