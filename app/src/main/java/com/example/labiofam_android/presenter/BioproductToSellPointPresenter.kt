package com.example.labiofam_android.presenter

import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.apiModel.SellPoint
import com.example.labiofam_android.contract.BioproductToSellPointContract

class BioproductToSellPointPresenter(bioproductToSpView: BioproductToSellPointContract.BioproductToSellPointView
, bioproductToSpModel:BioproductToSellPointContract.BioproductToSellPointModel):BioproductToSellPointContract.BioproductToSellPointPresenter {
    var bioproductToSpModel = bioproductToSpModel
    override suspend fun getSellPointsByBioproduct(id: String): List<SellPoint> {
        var res = bioproductToSpModel.getSellPointsByBioproduct(id)
        if (res.isSuccessful){
            return res.body()!!
        }
        else{
            return listOf()
        }
    }
    override suspend fun getSellPointsByBiopSubstring(substring: String):MutableList<SellPoint> {
        var res = bioproductToSpModel.getBioproductsBySubstring(substring)
        if(res.isSuccessful){
            var result = mutableListOf<SellPoint>()
            res.body()!!.forEach{biop->
                var sps_response = bioproductToSpModel.getSellPointsByBioproduct(biop.id)
                if(sps_response.isSuccessful){
                    var sps = sps_response.body()!!
                    sps.forEach{sp->
                        if(!result.contains(sp)){
                            result.add(sp)
                        }

                    }
                }
            }
            return result
        }
        else{
            return mutableListOf()
        }
    }


}