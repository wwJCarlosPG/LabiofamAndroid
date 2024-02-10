package com.example.labiofam_android.contract

import com.example.labiofam_android.apiModel.LogResponse
import com.google.gson.JsonObject
import retrofit2.Response

interface FeedbackContract {
    interface FeedbackModel{
        suspend fun sendMail(sender_name: String, sender_mail: String, subject: String, message:String): Response<Unit>
        suspend fun login(jsonObject: JsonObject):Response<LogResponse>
    }
    interface  FeedbackPresenter{
        suspend fun sendMail(ender_name: String, sender_mail: String, subject: String, message:String):String

    }
    interface FeedbackView{
        fun showToast(text:String)
    }
}