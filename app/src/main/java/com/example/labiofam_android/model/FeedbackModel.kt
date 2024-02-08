package com.example.labiofam_android.model

import com.example.labiofam_android.apiServices.FeedbackService
import com.example.labiofam_android.apiServices.RetrofitHelper
import com.example.labiofam_android.contract.FeedbackContract
import com.google.gson.JsonObject
import retrofit2.Response

class FeedbackModel:FeedbackContract.FeedbackModel {
    var feedback_service = RetrofitHelper.getInstance().create(FeedbackService::class.java)
    override suspend fun sendMail(sender_name: String, sender_mail: String, subject: String, message:String):Response<Unit>{

        var res = feedback_service.sendEmail(sender_name, sender_mail, subject, message)
        return res

    }
}