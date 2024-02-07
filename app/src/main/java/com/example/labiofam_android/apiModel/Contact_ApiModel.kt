package com.example.labiofam_android.apiModel

import com.google.gson.annotations.SerializedName

//clase modelo de los contactos
data class Contact(val id:String, val name: String,
                   val phone:String,
                   val occupation:String,
                   var image:String)