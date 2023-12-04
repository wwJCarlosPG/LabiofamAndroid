package com.example.labiofam_android.model

import com.example.labiofam_android.Services.FeedbackService
import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.contract.FeedbackContract
import retrofit2.Response
import retrofit2.create

class FeedbackModel:FeedbackContract.FeedbackModel {
    var feedback_service = RetrofitHelper.getInstance().create(FeedbackService::class.java)
    override suspend fun sendMail(subject: String, message: String):Response<Unit>{
        var res = feedback_service.sendEmail(subject,message)
        return res

    }

}