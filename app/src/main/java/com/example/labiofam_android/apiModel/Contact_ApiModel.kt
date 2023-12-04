package com.example.labiofam_android.apiModel

import com.google.gson.annotations.SerializedName

//clase modelo de los contactos
data class Contact(@SerializedName("contact_ID") val id:String, val name: String,
                   @SerializedName("contact_Info") val info:String,
                   val occupation:String,
                   var image:String)