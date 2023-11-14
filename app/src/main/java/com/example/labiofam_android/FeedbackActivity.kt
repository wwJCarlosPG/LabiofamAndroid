package com.example.labiofam_android

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class FeedbackActivity: AppCompatActivity(){


    private  lateinit var editText_message: EditText

    private lateinit var editText_mail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        initComponents()
        //initUI()
    }

    private fun initComponents() {
        editText_message = findViewById(R.id.editTextMessage)
        editText_mail = findViewById(R.id.editTextMail)
    }

    private fun initUI() {
        //editText_mail.mes
    }
}