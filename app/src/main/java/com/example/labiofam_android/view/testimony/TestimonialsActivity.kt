package com.example.labiofam_android.view.testimony

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R
import com.example.labiofam_android.VideoTestimony

class TestimonialsActivity:AppCompatActivity() {

    private lateinit var testimonials_adapter: TestimonialsAdapter
    private lateinit var mediaController: MediaController
    private lateinit var testimony_video: VideoView
    private lateinit var rv_testimonials: RecyclerView
    private lateinit var videoTestimonials: List<VideoTestimony>
    private var current_index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testimonials)
        initComponents()
        initUI()
        initVideoPlayer()

    }


    private fun initComponents() {
        testimony_video = findViewById(R.id.testimony_video)
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
        videoTestimonials = mutableListOf(
            //VideoTestimony("android.resource://$packageName/${R.raw.video2}"),
            //VideoTestimony("android.resource://$packageName/${R.raw.video2}")
        )
        testimonials_adapter = TestimonialsAdapter(TestimonialsProvider.imageTestimonials)
        rv_testimonials.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_testimonials.adapter = testimonials_adapter

    }

    private fun initVideoPlayer() {
        //mediaController = MediaController(this)
        if(videoTestimonials.isNotEmpty()) {
            mediaController = MediaController(this@TestimonialsActivity)
            mediaController.setAnchorView(testimony_video)
            val uri: Uri = Uri.parse("${videoTestimonials[current_index].path}")
            testimony_video.setMediaController(mediaController)
            testimony_video.setVideoURI(uri)
            testimony_video.requestFocus()
            testimony_video.start()
        }
    }


        fun nextVideo(view: View) {
            current_index += 1
            if (current_index < videoTestimonials.size && current_index >= 0) {
                if (mediaController == null) {
                    mediaController = MediaController(this)
                    mediaController.setAnchorView(testimony_video)
                }
                runOnUiThread {
                    val uri: Uri = Uri.parse("${videoTestimonials[current_index].path}")
                    testimony_video.setMediaController(mediaController)
                    testimony_video.setVideoURI(uri)
                    testimony_video.requestFocus()
                    testimony_video.start()
                }

            }


        }

        fun prevVideo(view: View) {
            current_index -= 1
            if (current_index < videoTestimonials.size && current_index >= 0) {
                if (mediaController == null) {
                    mediaController = MediaController(this)
                    mediaController.setAnchorView(testimony_video)
                }
                runOnUiThread {
                    val uri: Uri = Uri.parse("${videoTestimonials[current_index].path}")
                    testimony_video.setMediaController(mediaController)
                    testimony_video.setVideoURI(uri)
                    testimony_video.requestFocus()
                    testimony_video.start()
                }

            }
        }



}
