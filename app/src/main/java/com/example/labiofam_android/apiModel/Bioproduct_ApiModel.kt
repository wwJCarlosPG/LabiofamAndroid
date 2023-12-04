package com.example.labiofam_android.apiModel

//creo que deberia tener el costo...
data class Bioproducts (val id: String, val name: String, val image:String,
                        var type: String, val summary: String, val specifications: String)