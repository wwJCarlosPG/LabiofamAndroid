package com.example.labiofam_android.presenter

import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.contract.MainContract

class MainPresenter(mainView: MainContract.View,
                    mainModel: MainContract.Model): MainContract.Presenter {
    var mainView = mainView
    var mainModel = mainModel
    var result:MutableList<Bioproducts> = mutableListOf()
    override suspend fun getRandomBioproducts():MutableList<Bioproducts> {
            var response = mainModel.getRandomBioproducts()
            if(response.isSuccessful){
                var rs1 = response.body()!![0]
                var rs2 = response.body()!![1]
                var rs3 = response.body()!![2]
                result= mutableListOf(rs1,rs2,rs3)
                return (result)
            }
            else{
                mainView.showError("No hay bioproductos que mostrar")
            }
        return result
    }
}
