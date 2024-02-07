package com.example.labiofam_android.presenter

import com.example.labiofam_android.contract.FeedbackContract

class FeedbackPresenter(feedbackView: FeedbackContract.FeedbackView,
    feedbackModel: FeedbackContract.FeedbackModel):FeedbackContract.FeedbackPresenter {
    private var feedbackModel = feedbackModel
    private var feedbackView = feedbackView
    override suspend fun sendMail(sender_name: String, sender_mail: String, subject: String, message:String): String {
        var res = feedbackModel.sendMail(sender_name, sender_mail,subject, message)
        return if(res.isSuccessful){
            "Enviado con éxito"
        } else{
            "${res.code()}"
        }
    }

}