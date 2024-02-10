package com.example.labiofam_android.apiModel

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.JsonObject

//creo que deberia tener el costo...
data class Bioproducts (val id: String, val name: String, val image:String,
                        @SerializedName("type_of_Product")val type_of_product: String?, val description: String,
                        var advantages:String, val diseases:String,
                        @SerializedName("summary")var summary:JsonObject)