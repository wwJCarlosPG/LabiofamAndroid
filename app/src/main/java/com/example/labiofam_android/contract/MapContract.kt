package com.example.labiofam_android.contract

import com.example.labiofam_android.api_model.SellPoint
import retrofit2.Response

interface MapContract {

    interface  MapView{

    }
    interface  MapModel{
        suspend fun getSellPoints(): Response<List<SellPoint>>
        suspend fun getSellPointByAddress(substring: String):Response<List<SellPoint>>
    }
    interface  MapPresenter{
        suspend fun getSellPoints():List<SellPoint>
        suspend fun getByGeolocation(latitude:Double, longitude:Double):SellPoint
        suspend fun  getSellPointByAddress(substring:String):List<SellPoint>

    }
}