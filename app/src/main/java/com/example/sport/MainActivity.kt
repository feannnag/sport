package com.example.sport

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bt_def.BluetoothConstants

class MainActivity : AppCompatActivity() {
    private lateinit var btGoToSecond : Button
    private lateinit var etNum1 : EditText

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
}