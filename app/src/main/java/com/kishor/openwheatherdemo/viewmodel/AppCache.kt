package com.kishor.openwheatherdemo.viewmodel

import com.kishor.openwheatherdemo.model.WeatherData
import java.text.DecimalFormat

object AppCache {
    var weatherData: WeatherData? = null

    /**
     * Converts Kelvin to No decimal fahrenheit.
     */
    fun convertFromKelvinToFaranhite(temp: Double): Long {
        val df2 = DecimalFormat("#.##")
        return df2.format((temp - 273.5) * 9 / 5 + 32).toDouble().toLong()
    }
}