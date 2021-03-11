package com.kishor.openwheatherdemo.view

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kishor.openwheatherdemo.R

abstract class BaseActivity() : AppCompatActivity() {
    public abstract fun getLayoutResourceID(): Int
    protected lateinit var mHandler: Handler
    protected lateinit var builder : AlertDialog.Builder
    protected lateinit var dialog: AlertDialog
    protected lateinit var loadingView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceID())
        createAlertDialog()
    }

    protected fun createAlertDialog(){
        loadingView = layoutInflater.inflate(R.layout.loading_view, null)
        builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(loadingView)
        dialog = builder.create()
    }

    fun showLoader() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    fun hideLoader() {
        dialog.dismiss()
    }
}