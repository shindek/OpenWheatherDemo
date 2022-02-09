package com.kishor.openwheatherdemo.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.kishor.openwheatherdemo.BuildConfig
import com.kishor.openwheatherdemo.R
import com.kishor.openwheatherdemo.model.WeatherData
import com.kishor.openwheatherdemo.viewmodel.AppCache
import com.kishor.openwheatherdemo.viewmodel.WeatherUtils
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainActivity : BaseActivity() {

    override fun getLayoutResourceID(): Int {
        return R.layout.activity_main
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val btn_lookup = findViewById<Button>(R.id.btn_lookup)
        val et_cityName = findViewById<EditText>(R.id.et_city_name)
        btn_lookup.setOnClickListener {
            if(!et_cityName.text.toString().isNullOrEmpty() && et_cityName.text.toString().length >=3){
                getAllMembersListAPILiveData(et_cityName.text.toString())
            }else{
                var toast = Toast.makeText(this,"Please provide at least 3 characters of City Name!",Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            et_cityName.clearFocus()
        }
    }

    /**
     * Fetch Weather data from network.
     */
    fun getAllMembersListAPILiveData(cityName: String){
        showLoader()
        WeatherUtils(BuildConfig.API_URL,90000).getWeahterData(cityName,BuildConfig.API_KEY)
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : Observer<Response<WeatherData>>,
            io.reactivex.Observer<Response<WeatherData>> {
            override fun onSubscribe(d: Disposable?) {

            }

            override fun onNext(value: Response<WeatherData>?) {
                Log.d("MainActivity"," WeatherData: Response : onNext : "+value?.body())
                if(value!!.isSuccessful){
                    AppCache.weatherData = value?.body()
                    val intent = Intent(this@MainActivity, WeatherInfoActivity::class.java)
                    intent.putExtra("CITY",cityName)
                    startActivity(intent)
                }else if(value.code() == 404){
                    Toast.makeText(this@MainActivity,"City : $cityName Not Found!",Toast.LENGTH_LONG).show()
                    Log.d("MainActivity"," WeatherData: Response : Error : "+value?.errorBody()?.string())
                }
                hideLoader()
            }

            override fun onError(e: Throwable?) {
                Log.d("MainActivity"," WeatherData: Error : "+e?.message)
                hideLoader()
            }

            override fun onComplete() {
                Log.d("MainActivity"," WeatherData: onComplete : ")
            }

            override fun onChanged(t: Response<WeatherData>?) {
                Log.d("MainActivity"," WeatherData: onChanged : "+t?.body())

            }

        })

    }

}