package com.example.labiofam_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlin.math.min


class ServicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
        makeRequest("https://jsonplaceholder.typicode.com/posts")

    }

    //ORGANIZARTODOESTO
    fun makeRequest(url: String) {
        val listView = findViewById<ListView>(R.id.listView)
        val items = Array(40){""}
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        GlobalScope.launch(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val result = response.body?.string()
            val gson = Gson()
            val jsonArray = JsonParser.parseString(result).asJsonArray

            for (i in 0 until min(jsonArray.size(),40))
                items[i] = jsonArray[i].toString()
            runOnUiThread{
            val adapter = ArrayAdapter(this@ServicesActivity, android.R.layout.simple_list_item_1, items)
            listView.adapter = adapter
            }

        }
    }
}
