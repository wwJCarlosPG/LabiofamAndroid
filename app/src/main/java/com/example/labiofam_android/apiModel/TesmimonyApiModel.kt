package com.example.labiofam_android.apiModel

import com.google.gson.annotations.SerializedName

data class Testimony(val id:String, val name:String,
                     val image:String, @SerializedName("video_Url")val videoUrl:String)