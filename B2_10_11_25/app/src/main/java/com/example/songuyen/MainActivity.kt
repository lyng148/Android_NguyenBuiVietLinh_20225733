package com.example.songuyen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var edtNumber: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioGroup2: RadioGroup
    private lateinit var listView: ListView
    private lateinit var tvMessage: TextView
    private lateinit var adapter: ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtNumber = findViewById(R.id.edtNumber)
        radioGroup = findViewById(R.id.radioGroup)
        radioGroup2 = findViewById(R.id.radioGroup2)
        listView = findViewById(R.id.listView)
        tvMessage = findViewById(R.id.tvMessage)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter

        // Đảm bảo chỉ chọn 1 radio trong 6 cái
        val allGroups = listOf(radioGroup, radioGroup2)
        for (group in allGroups) {
            group.setOnCheckedChangeListener { g, checkedId ->
                if (checkedId != -1) {
                    allGroups.filter { it != g }.forEach { it.clearCheck() }
                    updateList()
                }
            }
        }

        edtNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = updateList()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateList() {
        val input = edtNumber.text.toString()
        if (input.isEmpty()) {
            adapter.clear()
            tvMessage.text = ""
            return
        }

        val n = input.toIntOrNull() ?: return
        val selectedId = listOf(radioGroup, radioGroup2).map { it.checkedRadioButtonId }.firstOrNull { it != -1 }

        val numbers = (1 until n).filter { num ->
            when (selectedId) {
                R.id.rbOdd -> num % 2 != 0
                R.id.rbEven -> num % 2 == 0
                R.id.rbPrime -> isPrime(num)
                R.id.rbPerfect -> isPerfect(num)
                R.id.rbSquare -> isSquare(num)
                R.id.rbFibo -> isFibo(num)
                else -> false
            }
        }

        if (numbers.isEmpty()) {
            adapter.clear()
            tvMessage.text = "Không có số nào thỏa mãn"
        } else {
            tvMessage.text = ""
            adapter.clear()
            adapter.addAll(numbers)
        }
    }

    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        for (i in 2..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return false
        }
        return true
    }

    private fun isPerfect(n: Int): Boolean {
        if (n < 2) return false
        val sum = (1 until n).filter { n % it == 0 }.sum()
        return sum == n
    }

    private fun isSquare(n: Int): Boolean {
        val r = sqrt(n.toDouble()).toInt()
        return r * r == n
    }

    private fun isFibo(n: Int): Boolean {
        val check1 = 5 * n * n + 4
        val check2 = 5 * n * n - 4
        return isSquare(check1) || isSquare(check2)
    }
}
