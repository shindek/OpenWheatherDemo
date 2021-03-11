package com.kishor.openwheatherdemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.kishor.openwheatherdemo.R
import com.kishor.openwheatherdemo.model.WeatherInfo
import com.kishor.openwheatherdemo.viewmodel.AppCache
import java.lang.Exception

class WeatherDetailsActivity : AppCompatActivity() {
    lateinit var weatherInfo: WeatherInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
        val position = intent.getIntExtra("POSITION",0)
        weatherInfo = AppCache.weatherData!!.list.get(position)
        initUI()
        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }
    private fun initUI(){
        try {
            findViewById<TextView>(R.id.tv_temp).text =
                AppCache.convertFromKelvinToFaranhite(weatherInfo!!.main.temp!!.toDouble())
                    .toString()
            findViewById<TextView>(R.id.tv_feels_like_temp_value).text =
                AppCache.convertFromKelvinToFaranhite(weatherInfo!!.main.feels_like!!.toDouble())
                    .toString()

            findViewById<TextView>(R.id.tv_clouds).text = weatherInfo!!.weather.get(0).main
            findViewById<TextView>(R.id.tv_clouds_value).text =
                weatherInfo!!.weather.get(0).description
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}