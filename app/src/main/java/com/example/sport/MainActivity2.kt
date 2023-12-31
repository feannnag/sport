package com.example.sport

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.sport.StatMsg.Companion.CONNECTING
import com.example.sport.StatMsg.Companion.IS_CONNECT
import com.example.sport.StatMsg.Companion.LOST
import com.example.sport.StatMsg.Companion.IS_NOT_CONNECT
import com.example.bt_def.BaseActivity
import com.example.bt_def.BluetoothConstants
import com.example.bt_def.BtConnection
import com.example.bt_def.bluetooth.ReceiveThread

class MainActivity2 : AppCompatActivity(), ReceiveThread.ListenerData {
    private lateinit var tView: TextView
    private var preferences: SharedPreferences? = null
    private var bAdapter: BluetoothAdapter? = null
    private lateinit var btConnection: BtConnection
    private lateinit var statusConnect: StatMsg
    private lateinit var ibReconnection: Button
    private lateinit var tvResult: TextView
    private lateinit var tvStatus: TextView

    private fun initBtAdapter() {
        val bManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bAdapter = bManager.adapter
        btConnection = BtConnection(bAdapter, this)
    }

    private fun initView() {
        tView = findViewById(R.id.textView)
        tvStatus = findViewById(R.id.textView)
        ibReconnection = findViewById(R.id.connectButt)
        statusConnect = StatMsg(context = this, tvStatus = tvStatus, btStart = ibReconnection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        initView()
        initBtAdapter()

        preferences = this.getSharedPreferences(BluetoothConstants.PREFERENCES, Context.MODE_PRIVATE)
        tView.text = intent.getStringExtra("height")
        val mSetting = getSharedPreferences(SettingConst.FILENAME, MODE_PRIVATE)
        Toast.makeText(this, mSetting.getString(SettingConst.HEIGHT, ""), Toast.LENGTH_SHORT).show()

        val goToBlSettings = findViewById<Button>(R.id.btBluetooth)
        goToBlSettings.setOnClickListener {startActivity(Intent(this, BaseActivity::class.java))}
        ibReconnection.setOnClickListener {connectToDevice(); ibReconnection.isEnabled = true}

    }



    override fun onReceive(message: String) {
        runOnUiThread {
            with(statusConnect) {
                when (message) {
                    IS_CONNECT -> connected()
                    IS_NOT_CONNECT -> notConnection()
                    LOST -> lostConnection()
                    CONNECTING -> Toast.makeText(context, CONNECTING, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    private fun connectToDevice() {
        val mac = preferences?.getString(BluetoothConstants.MAC, null)
        Log.d("Debugging", "Test Preferences = $mac")
        if (mac != null) {
            btConnection.connect(mac)
            ibReconnection.isEnabled = true
        } else {
            tvStatus.text = "No device"
            tvStatus.setTextColor(Color.RED)
        }
        val myToast = Toast.makeText(this, "MAC: $mac", Toast.LENGTH_SHORT)
        myToast.show()
        ibReconnection.isEnabled = true
    }
}

