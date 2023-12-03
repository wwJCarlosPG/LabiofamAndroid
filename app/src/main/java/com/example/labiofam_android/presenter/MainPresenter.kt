package com.example.labiofam_android.presenter

import com.example.labiofam_android.api_model.Bioproducts
import com.example.labiofam_android.contract.MainContract

class MainPresenter(mainView: MainContract.View,
                    mainModel: MainContract.Model): MainContract.Presenter {
    var mainView = mainView
    var mainModel = mainModel
    var result:MutableList<Bioproducts> = mutableListOf()
    override suspend fun getRandomBioproducts():MutableList<Bioproducts> {
            var response = mainModel.getRandomBioproducts()
            if(response.isSuccessful){
                var rs1 = mainModel.getBioproductsByName("Aceite Random 1")
                var rs2 = mainModel.getBioproductsByName("Aceite Random 2")
                var rs3 = mainModel.getBioproductsByName("Aceite Random 3")
                result= mutableListOf(rs1.body()!!, rs2.body()!!, rs3.body()!!)
                return (result)
            }
            else{
                mainView.showError("No hay bioproductos que mostrar")
            }
        return result
    }
}
