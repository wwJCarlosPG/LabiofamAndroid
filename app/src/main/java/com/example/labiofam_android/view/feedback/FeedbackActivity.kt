package com.example.labiofam_android.view.feedback

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.labiofam_android.R
import com.example.labiofam_android.Services.FeedbackService
import com.example.labiofam_android.Services.RetrofitHelper
import com.example.labiofam_android.Services.SellPointService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedbackActivity: AppCompatActivity(){


    private  lateinit var editText_message: EditText
    private lateinit var editText_mail: EditText
    private lateinit var editText_name: EditText
    private lateinit var editText_phone: EditText
    private lateinit var send_btn:Button
    val feedback_service = RetrofitHelper.getInstance().create(FeedbackService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        initComponents()
        //initUI()
    }

    private fun initComponents() {
        editText_message = findViewById(R.id.editTextMessage)
        editText_mail = findViewById(R.id.editTextMail)
        editText_name = findViewById(R.id.editTextName)
        editText_phone = findViewById(R.id.editTextNumber)
        send_btn = findViewById(R.id.send_btn)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.leftarrow)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }


    private fun initUI() {

    }

    fun sendEmail(view: View) {
        Log.d("jc","Entro al metodo")
        if(editText_name.text!=null && editText_mail.text != null &&
            editText_phone.text != null && editText_message.text!=null){
            //tengo que usar global scope aqui al parecer.
            GlobalScope.launch {
                Log.d("jc","Entro a la corrutina")
                delay(7000)
                var response = feedback_service.sendEmail("Quejas y Suegerencias",
                    "Mensaje: ${editText_message.text}, Nombre: ${editText_name.text}, " +
                            "Telefono: ${editText_phone.text}, Correo: ${editText_mail.text}")
                if(response.isSuccessful){
                    Log.d("jc", "is successful")
                }
            }

            Log.d("jc", "Sirvio esta talla")

        }
    }
}