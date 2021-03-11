package com.kishor.openwheatherdemo.model

data class WeatherInfo(val dt: Long, val dt_txt: String?, val main: WeatherMain,val weather:List<Weather>,
                       val clouds: Clouds, val wind: Winds, val sys: Sys?)