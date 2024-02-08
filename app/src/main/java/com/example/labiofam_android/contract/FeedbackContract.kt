package com.example.labiofam_android.contract

import com.google.gson.JsonObject
import retrofit2.Response

interface FeedbackContract {
    interface FeedbackModel{
        suspend fun sendMail(sender_name: String, sender_mail: String, subject: String, message:String): Response<Unit>

    }
    interface  FeedbackPresenter{
        suspend fun sendMail(ender_name: String, sender_mail: String, subject: String, message:String):String

    }
    interface FeedbackView{
        fun showToast(text:String)
    }
}