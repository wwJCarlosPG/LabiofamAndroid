package com.example.labiofam_android.view.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labiofam_android.R


class ServicesActivity : AppCompatActivity() {


    private  lateinit var servicesAdapter: ServicesAdapter

    private lateinit var rv_Services: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
        initComponents()
        initUI()
    }

    private fun initComponents(){
        rv_Services = findViewById(R.id.rv_Services)
    }

    private fun initUI() {

        servicesAdapter = ServicesAdapter(ServicesProvider.serviceList)//aqui va la lista que me den desde la api
        rv_Services.layoutManager = LinearLayoutManager(this)
        rv_Services.adapter = servicesAdapter
    }

    //override fun onCreate(savedInstanceState: Bundle?) {
      //  super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_services)
        //makeRequest("https://jsonplaceholder.typicode.com/posts")

    //}

    //ORGANIZARTODOESTO
    //fun makeRequest(url: String) {
    //    val listView = findViewById<ListView>(R.id.listView)
    //    val items = Array(40){""}
    //    val client = OkHttpClient()
    //    val request = Request.Builder().url(url).build()
   //     GlobalScope.launch(Dispatchers.IO) {
   //         val response = client.newCall(request).execute()
    //        val result = response.body?.string()
    //        val gson = Gson()
    //        val jsonArray = JsonParser.parseString(result).asJsonArray

    //        for (i in 0 until min(jsonArray.size(),40))
      //          items[i] = jsonArray[i].toString()
        //    runOnUiThread{
          //  val adapter = ArrayAdapter(this@ServicesActivity, android.R.layout.simple_list_item_1, items)
            //listView.adapter = adapter
            //}

        //}
}

