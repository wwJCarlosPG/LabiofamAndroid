package com.example.labiofam_android.view.service

import com.example.labiofam_android.apiModel.Service

class ServicesProvider {
    companion object{
        val serviceList = mutableListOf(
            Service("Chapeo de Patio", 17, "Bro, cogemos machete y le damos a toa la yerba esa que tengas por ahi", "59221941"),
            Service("Fumigacion de Cultivo", 13, "Te fumigamos la bichera del cultivo", "55814309"),
            Service("Prestamo de Mochila", 33, "Na, eso, te damos mochilon pa que fumigues tu mismo, no cambies piezas ni na de eso", "5845459s")
        )
    }
}