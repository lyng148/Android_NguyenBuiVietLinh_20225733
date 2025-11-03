package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView

    private var currentInput = "0"
    private var operator: Char? = null
    private var firstOperand: Int? = null
    private var newOperation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        // Gắn sự kiện cho tất cả các nút số
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener { it ->
                onNumberClick((it as Button).text.toString())
            }
        }

        // Toán tử
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick('+') }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorClick('-') }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClick('x') }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClick('/') }

        // Dấu =
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqualsClick() }

        // Nút xóa các loại
        findViewById<Button>(R.id.btnBS).setOnClickListener { onBackspaceClick() }
        findViewById<Button>(R.id.btnCE).setOnClickListener { onClearEntryClick() }
        findViewById<Button>(R.id.btnC).setOnClickListener { onClearAllClick() }
    }

    private fun onNumberClick(number: String) {
        if (newOperation) {
            currentInput = number
            newOperation = false
        } else {
            if (currentInput == "0") currentInput = number
            else currentInput += number
        }
        tvDisplay.text = currentInput
    }

    private fun onOperatorClick(op: Char) {
        if (firstOperand != null) {
            firstOperand = when (operator) {
                '+' -> firstOperand!! + currentInput.toInt()
                '-' -> firstOperand!! - currentInput.toInt()
                'x' -> firstOperand!! * currentInput.toInt()
                '/' -> if (currentInput.toInt() != 0) firstOperand!! / currentInput.toInt() else 0
                else -> currentInput.toInt()
            }
        }
        else {
            firstOperand = currentInput.toInt()
        }
        operator = op
        newOperation = true
    }


    private fun onEqualsClick() {
        if (firstOperand == null || operator == null) return
        val secondOperand = currentInput.toInt()
        val result = when (operator) {
            '+' -> firstOperand!! + secondOperand
            '-' -> firstOperand!! - secondOperand
            'x' -> firstOperand!! * secondOperand
            '/' -> if (secondOperand != 0) firstOperand!! / secondOperand else 0
            else -> 0
        }
        tvDisplay.text = result.toString()
        currentInput = result.toString()
        firstOperand = null
        operator = null
        newOperation = true
    }

    private fun onBackspaceClick() {
        if (currentInput.length > 1) {
            currentInput = currentInput.dropLast(1)
        } else {
            currentInput = "0"
        }
        tvDisplay.text = currentInput
    }

    private fun onClearEntryClick() {
        currentInput = "0"
        tvDisplay.text = currentInput
    }

    private fun onClearAllClick() {
        currentInput = "0"
        firstOperand = null
        operator = null
        tvDisplay.text = currentInput
    }
}
