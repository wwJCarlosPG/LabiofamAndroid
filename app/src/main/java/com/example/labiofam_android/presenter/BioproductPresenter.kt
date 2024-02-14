package com.example.labiofam_android.presenter

import android.util.Log
import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.contract.BioproductContract
import com.example.labiofam_android.model.BioproductModel
import com.example.labiofam_android.view.bioproduct.BioproductsCategories
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

    override fun getCategories(bioproducts: List<Bioproducts>): List<BioproductsCategories> {
        var aux:MutableList<String> = mutableListOf()
        var almost_res :MutableList<BioproductsCategories> = mutableListOf()
        var result: List<BioproductsCategories> = listOf()
        bioproducts.forEach{biop->
            if(biop.type_of_product!=null && !aux.contains(biop.type_of_product)){
                aux.add(biop.type_of_product)
            }
        }
        aux.forEach {
            almost_res.add(BioproductsCategories(false, it))
        }
        result = almost_res
        return result

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