package com.example.labiofam_android.contract

import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.apiModel.SellPoint
import retrofit2.Response

interface BioproductToSellPointContract {
    interface  BioproductToSellPointView{
    }
    interface  BioproductToSellPointModel{
        suspend fun getBioproductsBySubstring(substring:String):Response<List<Bioproducts>>
        suspend fun getSellPointsByBioproduct(id: String): Response<List<SellPoint>>
    }
    interface  BioproductToSellPointPresenter{
        suspend fun getSellPointsByBioproduct(id:String):List<SellPoint>
        suspend fun getSellPointsByBiopSubstring(substring:String):MutableList<SellPoint>
    }
}