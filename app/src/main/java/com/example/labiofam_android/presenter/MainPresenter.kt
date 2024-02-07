package com.example.labiofam_android.presenter

import com.example.labiofam_android.apiModel.Bioproducts
import com.example.labiofam_android.contract.MainContract
import kotlin.random.Random

class MainPresenter(mainView: MainContract.View,
                    mainModel: MainContract.Model): MainContract.Presenter {
    var mainView = mainView
    var mainModel = mainModel
    var result:MutableList<Bioproducts> = mutableListOf()
    override suspend fun getRandomBioproducts():MutableList<Bioproducts> {
            var response = mainModel.getRandomBioproducts()
            if(response!=null && response.isSuccessful && response.body()!!.size>=3){
                val bioproducts = response.body()
                var random_index1 = Random.nextInt(bioproducts!!.size)
                var random_index2 = Random.nextInt(bioproducts!!.size)
                var random_index3 = Random.nextInt(bioproducts!!.size)
                result= mutableListOf(bioproducts[random_index1], bioproducts[random_index2], bioproducts[random_index3])
            }
        return result
    }
}
