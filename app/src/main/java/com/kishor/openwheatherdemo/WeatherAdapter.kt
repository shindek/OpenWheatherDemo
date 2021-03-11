package com.kishor.openwheatherdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kishor.openwheatherdemo.model.WeatherData
import com.kishor.openwheatherdemo.model.WeatherInfo
import com.kishor.openwheatherdemo.viewmodel.AppCache

class WeatherAdapter(weatherInfoList: List<WeatherInfo>?): RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    val weatherInfoList = weatherInfoList
    private var itemClickListener : WeatherAdapter.ItemClickListener? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parentView = itemView;
        val header = itemView.findViewById<TextView>(R.id.tv_name)
        val temp_value = itemView.findViewById<TextView>(R.id.tv_temp_value)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wehatherInfo = weatherInfoList!!.get(position)
        holder.header.text = wehatherInfo.weather!!.get(0)!!.main
        holder.temp_value.text = AppCache.convertFromKelvinToFaranhite(wehatherInfo!!.main!!.temp!!).toString()

        if(itemClickListener !=null){
            holder.itemView.setOnClickListener {
                itemClickListener!!.onItemClick(holder.itemView,position)
            }

        }
    }

    override fun getItemCount(): Int {
        return weatherInfoList!!.size
    }
    fun setOnItemClickListener(listner: ItemClickListener) {
        itemClickListener = listner
    }

    interface ItemClickListener{
        fun onItemClick(view: View,position: Int)
    }
}