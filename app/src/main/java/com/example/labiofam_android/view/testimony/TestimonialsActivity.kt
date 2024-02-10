package com.example.labiofam_android.view.testimony

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R
import com.example.labiofam_android.VideoTestimony
import com.example.labiofam_android.apiModel.Testimony
import com.example.labiofam_android.contract.MainContract
import com.example.labiofam_android.contract.TestimonyContract
import com.example.labiofam_android.model.MainModel
import com.example.labiofam_android.model.TestimonyModel
import com.example.labiofam_android.presenter.MainPresenter
import com.example.labiofam_android.presenter.TestimonyPresenter
import com.example.labiofam_android.view_interface.ViewInterface
import kotlinx.coroutines.launch

class TestimonialsActivity:AppCompatActivity(), TestimonyContract.TestimonyView, ViewInterface {

    private lateinit var testimonials_adapter: TestimonialsAdapter
    private lateinit var links_adapter: VideoLinksAdapter
    //private lateinit var mediaController: MediaController
    //private lateinit var testimony_video: VideoView
    private lateinit var images:List<Testimony>
    private lateinit var videos:List<Testimony>
    private lateinit var rv_testimonials: RecyclerView
    private lateinit var rv_Testimonials_video:RecyclerView
    private lateinit var testimonials: List<Testimony>
    //private var current_index: Int = 0
    private var testimonyModel: TestimonyModel = TestimonyModel()
    private var testimonyPresenter = TestimonyPresenter(this, testimonyModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testimonials)
        initComponents()
        initUI()
        //initVideoPlayer()

    }


    override fun initComponents() {
        rv_Testimonials_video = findViewById(R.id.rv_Testimonials_video)
        rv_testimonials = findViewById(R.id.rv_Testimonials)
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


    override  fun initUI() {

        lifecycleScope.launch{
            try{
                videos = testimonyPresenter.getVideos()
                images = testimonyPresenter.getImages()
                var x =0
                testimonials_adapter = TestimonialsAdapter(images)
                rv_testimonials.layoutManager =
                    LinearLayoutManager(this@TestimonialsActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_testimonials.adapter = testimonials_adapter

                links_adapter = VideoLinksAdapter(videos)
                rv_Testimonials_video.layoutManager =
                    LinearLayoutManager(this@TestimonialsActivity, LinearLayoutManager.VERTICAL, false)
                rv_Testimonials_video.adapter = links_adapter
            }
            catch (ex:Exception){
                    showError("Error de conexión")
            }
        }



    }


    /* private fun initVideoPlayer() {
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
     */}


     /*   fun nextVideo(view: View) {
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
*/



