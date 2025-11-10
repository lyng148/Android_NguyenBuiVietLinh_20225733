package com.example.currency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editFrom: EditText
    private lateinit var editTo: EditText
    private var isEditing = false

    // Tỷ giá cố định so với USD
    private val rates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.92,
        "GBP" to 0.79,
        "JPY" to 151.0,
        "CNY" to 7.2,
        "KRW" to 1370.0,
        "AUD" to 1.53,
        "CAD" to 1.37,
        "CHF" to 0.88,
        "VND" to 25400.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        editFrom = findViewById(R.id.editFrom)
        editTo = findViewById(R.id.editTo)

        val currencies = rates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        spinnerFrom.setSelection(currencies.indexOf("USD"))
        spinnerTo.setSelection(currencies.indexOf("VND"))

        editFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return
                isEditing = true
                convertCurrency(editFrom, editTo, true)
                isEditing = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        editTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return
                isEditing = true
                convertCurrency(editTo, editFrom, false)
                isEditing = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                if (!isEditing) editFrom.text?.let { convertCurrency(editFrom, editTo, true) }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                if (!isEditing) editFrom.text?.let { convertCurrency(editFrom, editTo, true) }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun convertCurrency(sourceEdit: EditText, targetEdit: EditText, fromFirst: Boolean) {
        val fromCurrency = if (fromFirst) spinnerFrom.selectedItem.toString() else spinnerTo.selectedItem.toString()
        val toCurrency = if (fromFirst) spinnerTo.selectedItem.toString() else spinnerFrom.selectedItem.toString()

        val input = sourceEdit.text.toString().toDoubleOrNull()
        if (input == null) {
            targetEdit.setText("")
            return
        }

        val result = if (fromFirst)
            input * (rates[toCurrency]!! / rates[fromCurrency]!!)
        else
            input * (rates[toCurrency]!! / rates[fromCurrency]!!)

        targetEdit.setText(String.format("%.4f", result))
    }
}
