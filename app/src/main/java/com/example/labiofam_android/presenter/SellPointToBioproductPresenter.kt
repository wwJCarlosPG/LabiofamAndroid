package com.example.labiofam_android.presenter

import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.contract.SellPointToBioproductContract

class SellPointToBioproductPresenter(sellPointToBioproductView: SellPointToBioproductContract.SellPointToBioproductView,
    sellPointToBioproductModel:SellPointToBioproductContract.SellPointToBioproductModel):SellPointToBioproductContract.SellPointToBioproductPresenter {
    var sellPointToBioproductModel = sellPointToBioproductModel
    private var list = listOf<Bioproducts>()
    override suspend fun getBioproductsBySellPoint(id: String): List<Bioproducts> {
        var res = sellPointToBioproductModel.getBioproductsBySellPoint(id)
        if(res.isSuccessful){
            return res.body()!!
        }
        else return list
    }
}