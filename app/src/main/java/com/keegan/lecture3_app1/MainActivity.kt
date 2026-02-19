package com.keegan.lecture3_app1

import android.os.Bundle
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.widget.SeekBar
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

        val totalView = findViewById<EditText>(R.id.totalView)
        val tipSeekBar = findViewById<SeekBar>(R.id.tipSeekBar)
        val labelTipPercent = findViewById<TextView>(R.id.labelTipPercent)
        val peopleView = findViewById<EditText>(R.id.peopleView)
        val button = findViewById<Button>(R.id.button)
        val resultsView = findViewById<TextView>(R.id.resultsView)

        tipSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                labelTipPercent.text = "Tip: $progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        button.setOnClickListener {
            val billTotal = totalView.text.toString().toDoubleOrNull()
            val peopleCount = peopleView.text.toString().toIntOrNull() ?: 1

            if (billTotal != null) {
                val tipPercentage = tipSeekBar.progress / 100.0

                val totalTip = billTotal * tipPercentage
                val grandTotal = billTotal + totalTip
                val amountPerPerson = grandTotal / peopleCount

                resultsView.text = """
                    Tip: R ${String.format("%.2f", totalTip)}
                    Total: R ${String.format("%.2f", grandTotal)}
                    Per Person: R ${String.format("%.2f", amountPerPerson)}
                """.trimIndent()
            } else {
                resultsView.text = "Please enter a valid bill amount"
            }
        }
    }
}