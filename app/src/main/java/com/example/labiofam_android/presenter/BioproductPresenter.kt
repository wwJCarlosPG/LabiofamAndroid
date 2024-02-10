package com.example.labiofam_android.presenter

import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.contract.BioproductContract
import com.example.labiofam_android.model.BioproductModel
import com.google.gson.Gson

class BioproductPresenter(bioproductView:BioproductContract.BioproductView,
bioproductModel:BioproductModel):BioproductContract.BioproductPresenter {
    private var bioproductModel = bioproductModel
    private var bioproductView = bioproductView
    private var defaultBiop = listOf<Bioproducts>()
    override suspend fun getBioproducts(): List<Bioproducts> {
        var res = bioproductModel.getBioproducts()
        if(res.isSuccessful){
            return res.body()!!
        }
        else{
            return defaultBiop
        }
    }

    override suspend fun getBioproductBySubstring(substring: String):List<Bioproducts> {
        var res = bioproductModel.getBioproductsBySubstring(substring)
        if(res.isSuccessful){
            return res.body()!!
        }
        else{
            return defaultBiop
        }
    }

}