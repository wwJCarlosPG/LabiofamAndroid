package com.example.labiofam_android.apiModel

import java.util.Date

data class LogResponse(val name:String, val token:String,
                       val expirationDate:Date,
                       val refreshToken:String,
                       val refreshTokenExpirationDate:Date)
