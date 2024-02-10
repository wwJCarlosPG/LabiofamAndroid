package com.example.labiofam_android.model

import android.util.Log
import com.example.labiofam_android.apiModel.LogResponse
import com.example.labiofam_android.apiServices.FeedbackService
import com.example.labiofam_android.apiServices.RetrofitHelper
import com.example.labiofam_android.contract.FeedbackContract
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import retrofit2.Response

class FeedbackModel:FeedbackContract.FeedbackModel {
    var feedback_service = RetrofitHelper.getInstance().create(FeedbackService::class.java)
    var user:String = "superusuario"
    var password:String = "Superusuario123"
    override suspend fun sendMail(sender_name: String, sender_email: String, subject: String, message:String):Response<Unit>{
        var jsonObject:JsonObject = JsonObject()
        jsonObject.addProperty("name", user)
        jsonObject.addProperty("password",password)
        val logRes = login(jsonObject)
        if(logRes.isSuccessful){
            var response = logRes.body()
            var res = feedback_service.sendEmail(sender_name, sender_email, subject, message, response!!.token)
            return res
        }
        return feedback_service.sendEmail(sender_name, sender_email, subject, message,"")

    }

    override suspend fun login(jsonObject: JsonObject):Response<LogResponse> {
        return feedback_service.logIn(jsonObject)
    }
}