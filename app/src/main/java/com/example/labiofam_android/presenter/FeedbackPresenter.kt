package com.example.labiofam_android.presenter

import com.example.labiofam_android.contract.FeedbackContract

class FeedbackPresenter(feedbackView: FeedbackContract.FeedbackView,
    feedbackModel: FeedbackContract.FeedbackModel):FeedbackContract.FeedbackPresenter {
    private var feedbackModel = feedbackModel
    private var feedbackView = feedbackView
    override suspend fun sendMail(subject: String, message: String): String {
        var res = feedbackModel.sendMail(subject, message)
        if(res.isSuccessful){
            return "Enviado con éxito"
        }
        else{
            return "Fallo en el envio"
        }
    }

}