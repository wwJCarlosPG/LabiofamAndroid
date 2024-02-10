package com.example.labiofam_android.view.feedback

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.labiofam_android.R
import com.example.labiofam_android.apiServices.FeedbackService
import com.example.labiofam_android.apiServices.RetrofitHelper
import com.example.labiofam_android.contract.FeedbackContract
import com.example.labiofam_android.model.FeedbackModel
import com.example.labiofam_android.presenter.FeedbackPresenter
import com.example.labiofam_android.view_interface.ViewInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedbackActivity: ViewInterface,AppCompatActivity(),FeedbackContract.FeedbackView{


    private  lateinit var editText_message: EditText
    private lateinit var editText_mail: EditText
    private lateinit var editText_name: EditText
    private lateinit var editText_phone: EditText
    private lateinit var send_btn:Button
    val feedback_model = FeedbackModel()
    val feedback_presenter = FeedbackPresenter(this@FeedbackActivity, feedback_model)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        initComponents()
    }

    override fun initComponents() {
        editText_message = findViewById(R.id.editTextMessage)
        editText_mail = findViewById(R.id.editTextMail)
        editText_name = findViewById(R.id.editTextName)
        editText_phone = findViewById(R.id.editTextNumber)
        send_btn = findViewById(R.id.send_btn)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun showError(message: String) {
        runOnUiThread{
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun initUI() {

    }

    fun sendEmail(view: View) {

        if (editText_name.text != null && editText_mail.text != null &&
                editText_phone.text != null && editText_message.text != null
            ) {
                GlobalScope.launch {
                    showToast("No salga de la vista, espere unos segundos...")
                    delay(15000)
                    var sender_name = editText_name.text.toString()
                    val sender_email = editText_mail.text.toString()
                    val message = editText_message.text.toString()
                    val phone = editText_phone.text.toString()
                    sender_name = sender_name+" "+phone
                    val subject = "Quejas y sugerencias desde la apk"
                    var response = feedback_presenter.sendMail(sender_name, sender_email,
                        subject, message
                    )
                    showToast(response)

                }


            }
    }

    override fun showToast(text:String) {
        runOnUiThread{
            Toast.makeText(this, "$text", Toast.LENGTH_SHORT).show()
        }
    }
}