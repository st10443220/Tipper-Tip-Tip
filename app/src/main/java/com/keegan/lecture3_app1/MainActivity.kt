package com.keegan.lecture3_app1

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText

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

        // 1. Find all our modern views
        val totalView = findViewById<TextInputEditText>(R.id.totalView)
        val tipSlider = findViewById<Slider>(R.id.tipSlider)
        val labelTipPercent = findViewById<TextView>(R.id.labelTipPercent)
        val peopleView = findViewById<TextInputEditText>(R.id.peopleView)
        val button = findViewById<Button>(R.id.button)
        val resultsView = findViewById<TextView>(R.id.resultsView)

        // 2. Listen to the Material Slider
        tipSlider.addOnChangeListener { _, value, _ ->
            // The value comes back as a Float, so we convert it to an Int for a clean display
            val currentTip = value.toInt()
            labelTipPercent.text = "Tip Percentage: $currentTip%"
        }

        // 3. Do the math when the Calculate button is clicked
        button.setOnClickListener {
            val billTotal = totalView.text.toString().toDoubleOrNull()
            val peopleCount = peopleView.text.toString().toIntOrNull() ?: 1

            if (billTotal != null) {
                // Get the current number from the slider
                val tipPercentage = tipSlider.value / 100.0

                // The Math
                val totalTip = billTotal * tipPercentage
                val grandTotal = billTotal + totalTip
                val amountPerPerson = grandTotal / peopleCount

                // Update the results card
                resultsView.text = """
                    Tip Amount: R ${String.format("%.2f", totalTip)}
                    Total Bill: R ${String.format("%.2f", grandTotal)}
                    
                    Each Person Pays:
                    R ${String.format("%.2f", amountPerPerson)}
                """.trimIndent()

            } else {
                resultsView.text = "Please enter a valid bill amount above."
            }
        }
    }
}