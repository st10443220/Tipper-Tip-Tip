package com.keegan.lecture3_app1

import android.os.Bundle
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button = findViewById<Button>(R.id.button)
        val totalView = findViewById<EditText>(R.id.totalView)
        val tipView = findViewById<TextView>(R.id.tipView)

        button.setOnClickListener {
            val total = totalView.text.toString().toDoubleOrNull()

            if (total != null) {
                val tipPercent = 0.1
                val tip = total * tipPercent

                tipView.text = (String.format("%.0f", tip))
            } else {
                tipView.text = ""
            }
        }
    }
}