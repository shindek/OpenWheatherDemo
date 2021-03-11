package com.kishor.openwheatherdemo.model

data class WeatherData(val cod:String, val message: String, val cnt:Double?, val list: List<WeatherInfo>)
