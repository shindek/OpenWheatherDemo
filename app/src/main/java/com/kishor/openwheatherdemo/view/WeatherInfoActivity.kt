package com.kishor.openwheatherdemo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kishor.openwheatherdemo.R
import com.kishor.openwheatherdemo.WeatherAdapter
import com.kishor.openwheatherdemo.viewmodel.AppCache

class WeatherInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_info)
        // uncomment this, in case we want to display cityname on header.
        /*val cityName = intent.getStringExtra("CITY")
        if(cityName !=null) {
            findViewById<TextView>(R.id.tv_header).setText(cityName)
        }*/
        initRecycleView()
        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }
    private fun initRecycleView(){
        if(AppCache.weatherData !=null && AppCache.weatherData!!.list.isNotEmpty()) {
            val weather_listview = findViewById<RecyclerView>(R.id.weather_listview)
            val weatherAdapter = WeatherAdapter(AppCache.weatherData!!.list!!)
            weather_listview.layoutManager = LinearLayoutManager(this)
            weather_listview.setAdapter(weatherAdapter)
            weatherAdapter!!.setOnItemClickListener(object : WeatherAdapter.ItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val intent = Intent(this@WeatherInfoActivity, WeatherDetailsActivity::class.java)
                    intent.putExtra("POSITION",position)
                    startActivity(intent)
                }
            })
        }
    }
}