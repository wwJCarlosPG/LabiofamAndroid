package com.example.labiofam_android.view.home

import android.provider.Settings.Global
import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import com.example.labiofam_android.Services.BioproductService
import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.api_model.Bioproducts
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import retrofit2.Response


class MainModel:MainContract.Model {
    var bioproductService = RetrofitHelper.getInstance().create(BioproductService::class.java)
    override suspend fun getRandomBioproducts(): Response<List<Bioproducts>>{
        return bioproductService.getBioproducts()
    }

    override suspend fun getBioproductsByName(name: String): Response<Bioproducts> {
        return bioproductService.getByName(name)
    }


}