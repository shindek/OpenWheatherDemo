package com.kishor.openwheatherdemo.viewmodel

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kishor.openwheatherdemo.model.WeatherData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WeatherUtils {

    private var baseURL : String

    enum class HTTP_VERB{
        GET,
        POST
    }
    /**
     * Default timeout is set at 90000 milli seconds
     */
    private var DEFAULT_CONNECT_TIMEOUT_IN_MS: Long = 90000
    private var DEFAULT_WRITE_TIMEOUT_IN_MS: Long = 90000
    private var DEFAULT_READ_TIMEOUT_IN_MS: Long = 90000

    constructor(baseURL: String){
        this.baseURL = baseURL
    }
    constructor(baseURL: String, timeout_In_MilliSecond: Long){
        this.baseURL = baseURL
        DEFAULT_CONNECT_TIMEOUT_IN_MS = timeout_In_MilliSecond
        DEFAULT_WRITE_TIMEOUT_IN_MS = timeout_In_MilliSecond
        DEFAULT_READ_TIMEOUT_IN_MS = timeout_In_MilliSecond
    }

    private fun getGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }
    private fun getRetrofit(): Retrofit? {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val oktHttpClient = OkHttpClient.Builder()
            .connectTimeout(
                DEFAULT_CONNECT_TIMEOUT_IN_MS,
                TimeUnit.MILLISECONDS
            )
            .writeTimeout(
                DEFAULT_WRITE_TIMEOUT_IN_MS,
                TimeUnit.MILLISECONDS
            )
            .readTimeout(
                DEFAULT_READ_TIMEOUT_IN_MS,
                TimeUnit.MILLISECONDS
            )
        oktHttpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(oktHttpClient.build())
            .build()
    }

    fun getWeahterData(cityName : String, apiKey: String): Observable<Response<WeatherData>>{
        val mWeatherAPI: WeatherAPI = getRetrofit()!!.create(WeatherAPI::class.java)
        val call: Observable<Response<WeatherData>> = mWeatherAPI.getWeatherInfo(cityName,apiKey)
        return call.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}