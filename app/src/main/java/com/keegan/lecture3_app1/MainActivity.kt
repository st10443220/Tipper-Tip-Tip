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
import com.google.android.material.textfield.TextInputLayout

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

        // NEW: Find the Layout wrappers so we can show errors on them
        val totalInputLayout = findViewById<TextInputLayout>(R.id.totalInputLayout)
        val peopleInputLayout = findViewById<TextInputLayout>(R.id.peopleInputLayout)

        // 2. Listen to the Material Slider
        tipSlider.addOnChangeListener { _, value, _ ->
            val currentTip = value.toInt()
            labelTipPercent.text = "Tip Percentage: $currentTip%"
        }

        // 3. Do the math when the Calculate button is clicked
        button.setOnClickListener {
            val billText = totalView.text.toString()
            val peopleText = peopleView.text.toString()

            val billTotal = billText.toDoubleOrNull()
            val peopleCount = peopleText.toIntOrNull()

            // Assume there are no errors to start
            var hasError = false

            // --- ERROR CHECKING ---

            // Check the Bill Total
            if (billTotal == null || billTotal <= 0) {
                totalInputLayout.error = "Please enter a valid bill amount"
                hasError = true
            } else {
                totalInputLayout.error = null // This clears the error and the red color
            }

            // Check the Number of People
            if (peopleCount == null || peopleCount < 1) {
                peopleInputLayout.error = "Must be at least 1 person"
                hasError = true
            } else {
                peopleInputLayout.error = null // Clears the error
            }

            // --- CALCULATE ONLY IF NO ERRORS ---

            if (!hasError && billTotal != null && peopleCount != null) {
                val tipPercentage = tipSlider.value / 100.0

                val totalTip = billTotal * tipPercentage
                val grandTotal = billTotal + totalTip
                val amountPerPerson = grandTotal / peopleCount

                resultsView.text = """
                    Tip Amount: R ${String.format("%.2f", totalTip)}
                    Total Bill: R ${String.format("%.2f", grandTotal)}
                    
                    Each Person Pays:
                    R ${String.format("%.2f", amountPerPerson)}
                """.trimIndent()
            } else {
                // If there's an error, prompt them to fix it
                resultsView.text = "Please fix the errors above."
            }
        }
    }
}