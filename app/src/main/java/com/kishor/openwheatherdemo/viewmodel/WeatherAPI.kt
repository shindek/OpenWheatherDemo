package com.kishor.openwheatherdemo.viewmodel

import com.kishor.openwheatherdemo.model.WeatherData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

public interface WeatherAPI {
    @GET("data/2.5/forecast")
    fun getWeatherInfo(@Query("q") cityName: String,
                       @Query("appid") appId: String): Observable<Response<WeatherData>>
}