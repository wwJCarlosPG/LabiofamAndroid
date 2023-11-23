package com.example.labiofam_android.api_model
//clase modelo de los puntos de venta.
data class SellPoint (val id:String,val name:String, val latitude:Double,
                      val longitude:Double, val address:String,
                      val province:String, val municipality:String,
                      val image:String)