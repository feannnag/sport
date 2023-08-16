package com.example.myfirstapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btSum: Button = findViewById(R.id.btSum)
        val tvResult: TextView = findViewById(R.id.tvResult)
        val etNum1: EditText = findViewById(R.id.etNum1)
        val etNum2: EditText = findViewById(R.id.etNum2)

        Log.d("Debug", "Hello")

        btSum.setOnClickListener {
            tvResult.text = String.format((etNum1.text.toString().toInt() + etNum2.text.toString().toInt()).toString())
            btSum.text = "СУММА${(0..10).random()}"
            Toast.makeText(this, "Toast", Toast.LENGTH_LONG).show()

        }
    }
}