package com.example.labiofam_android.contract

import com.example.labiofam_android.api_model.Bioproducts
import retrofit2.Response

interface SellPointToBioproductContract {

    interface  SellPointToBioproductView{
    }
    interface  SellPointToBioproductModel{
        suspend fun getBioproductsBySellPoint(id: String):Response<List<Bioproducts>>
    }
    interface  SellPointToBioproductPresenter{
        suspend fun getBioproductsBySellPoint(id:String):List<Bioproducts>
    }
}