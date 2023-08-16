package com.example.sport

import android.content.Context
import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.sport.R

data class StatMsg(val context: Context, val tvStatus: TextView, val btStart: Button){
    companion object {
        const val IS_CONNECT = "Connected"
        const val CONNECTING = "Connecting..."
        const val LOST = "Connection lost"
        const val IS_NOT_CONNECT = "Can not connect to device"
    }

    fun connected (){
      //  tvStatus.text = context.getString(R.string.status_connected)
        btStart.isEnabled = true
        tvStatus.setTextColor(Color.WHITE)
        Toast.makeText(context, IS_CONNECT, Toast.LENGTH_SHORT).show()
    }

    fun notConnection(){
      //  tvStatus.text = context.getString(R.string.status_cannotConnect)
        tvStatus.setTextColor(Color.RED)
        btStart.isEnabled = false
        Toast.makeText(context, IS_NOT_CONNECT, Toast.LENGTH_LONG).show()
    }

    fun lostConnection(){
       // tvStatus.text = context.getString(R.string.status_connectionLost)
        tvStatus.setTextColor(Color.RED)
        btStart.isEnabled = false
        Toast.makeText(context, LOST, Toast.LENGTH_LONG).show()
    }
}
