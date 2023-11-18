package com.example.labiofam_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TestimonialsActivity:AppCompatActivity() {

    private  lateinit var testimonials_adapter: TestimonialsAdapter

    private lateinit var rv_testimonials: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testimonials)
        initComponents()
        initUI()
    }


    private fun initComponents(){
        rv_testimonials = findViewById(R.id.rv_Testimonials)
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

        testimonials_adapter = TestimonialsAdapter(TestimonialsProvider.imageTestimonials)
        rv_testimonials.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_testimonials.adapter = testimonials_adapter
    }
}