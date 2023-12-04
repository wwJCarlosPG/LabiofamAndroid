package com.example.labiofam_android.presenter

import com.example.labiofam_android.apiModel.SellPoint
import com.example.labiofam_android.contract.MapContract

class MapPresenter(mapView: MapContract.MapView,mapModel:MapContract.MapModel): MapContract.MapPresenter {
    var mapView = mapView
    var mapModel = mapModel

    override suspend fun getSellPoints(): List<SellPoint> {
        var response = mapModel.getSellPoints()
        if (response.isSuccessful){
            return response.body()!!
        }
        return listOf()
    }
    var defaultSellPoint = SellPoint(
        "","",0.0,0.0,"","","", "")
    override suspend fun getByGeolocation(latitude:Double, longitude:Double): SellPoint {
        var response = mapModel.getSellPoints()
        if(response.isSuccessful){
            response.body()!!.forEach{ sp->
                if(sp.latitude == latitude && sp.longitude == longitude){
                    return sp
                }
            }
        }
        else{
            return defaultSellPoint
        }
        return defaultSellPoint
    }

    override suspend fun getSellPointByAddress(substring: String): List<SellPoint> {
        var response = mapModel.getSellPointByAddress(substring)
        if(response.isSuccessful){
            return response.body()!!
        }
        return listOf()
    }

}