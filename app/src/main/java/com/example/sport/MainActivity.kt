package com.example.sport

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bt_def.BluetoothConstants
import com.example.bt_def.BtConnection
import android.content.SharedPreferences
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothAdapter
import com.example.bt_def.bluetooth.ReceiveThread

class MainActivity : AppCompatActivity(), ReceiveThread.ListenerData {
    private lateinit var btGoToSecond : Button
    private lateinit var etNum1 : EditText
    private lateinit var btConnection: BtConnection
    private var preferences: SharedPreferences? = null
    private var bAdapter: BluetoothAdapter? = null

    private fun initView(){
        btGoToSecond = findViewById(R.id.btGoToSecond)
        etNum1 = findViewById(R.id.etNum1)
    }

    private fun testPreferences() {
        val mac = preferences?.getString(BluetoothConstants.MAC, null)
        Log.d("Debugging", "Test Preferences = $mac")
        if (mac != null) {
            btConnection.connect(mac)
        } else {
            Toast.makeText(this, "Устройство не выбрано!", Toast.LENGTH_LONG).show()
        }
        val myToast = Toast.makeText(this, "MAC: $mac", Toast.LENGTH_SHORT)
        myToast.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBtAdapter()
        setContentView(R.layout.activity_main)
        val mSetting = getSharedPreferences(SettingConst.FILENAME, MODE_PRIVATE)
        val editor = mSetting.edit()

        val button: Button = findViewById(R.id.btTest)
        button.setOnClickListener { testPreferences() }

        initView()

        val intent = Intent(this, MainActivity2::class.java)

        btGoToSecond.setOnClickListener {
            intent.putExtra("height", etNum1.text.toString())
            editor.putString(SettingConst.HEIGHT, etNum1.text.toString())
            editor.apply()
            startActivity(intent)
        }
    }
    private fun initBtAdapter() {
        val bManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bAdapter = bManager.adapter
        btConnection = BtConnection(bAdapter, this)
    }
    @SuppressLint("SetTextI18n")
    override fun onReceive(message: String) {
        runOnUiThread {
        }
    }
}