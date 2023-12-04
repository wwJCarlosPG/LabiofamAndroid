package com.example.labiofam_android.contract

import retrofit2.Response

interface FeedbackContract {
    interface FeedbackModel{
        suspend fun sendMail(subject: String, message: String): Response<Unit>
    }
    interface  FeedbackPresenter{
        suspend fun sendMail(subject:String, message:String):String

    }
    interface FeedbackView{
        fun showToast(text:String)
    }
}