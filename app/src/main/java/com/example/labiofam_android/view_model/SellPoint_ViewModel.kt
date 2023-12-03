package com.example.labiofam_android.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.labiofam_android.api_model.SellPoint


class SellPoint_ViewModel:ViewModel() {
    val sellPointModel = MutableLiveData<SellPoint>()

}