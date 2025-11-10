package com.example.form

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.view.isGone

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var etBirthday: EditText
    private lateinit var btnSelectBirthday: Button
    private lateinit var calendarView: CalendarView
    private lateinit var etAddress: EditText
    private lateinit var etEmail: EditText
    private lateinit var cbTerms: CheckBox
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ view
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        rgGender = findViewById(R.id.rgGender)
        etBirthday = findViewById(R.id.etBirthday)
        btnSelectBirthday = findViewById(R.id.btnSelectBirthday)
        calendarView = findViewById(R.id.calendarView)
        etAddress = findViewById(R.id.etAddress)
        etEmail = findViewById(R.id.etEmail)
        cbTerms = findViewById(R.id.cbTerms)
        btnRegister = findViewById(R.id.btnRegister)

        // Ẩn Calendar ban đầu
        calendarView.visibility = View.GONE

        // Xử lý hiển thị Calendar
        btnSelectBirthday.setOnClickListener {
            if (calendarView.isGone) {
                calendarView.visibility = View.VISIBLE

            } else {
                calendarView.visibility = View.GONE

            }
        }

        // Chọn ngày sinh
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            etBirthday.setText(sdf.format(calendar.time))
            calendarView.visibility = View.GONE
        }

        // Xử lý khi nhấn Register
        btnRegister.setOnClickListener {
            validateForm()
        }
    }

    private fun validateForm() {
        var isValid = true

        // Reset lại màu trước khi kiểm tra
        val defaultColor = Color.parseColor("#EFEFEF")

        val editTexts = listOf(etFirstName, etLastName, etBirthday, etAddress, etEmail)
        editTexts.forEach { it.setBackgroundColor(defaultColor) }

        // Kiểm tra từng trường
        if (etFirstName.text.isNullOrBlank()) {
            etFirstName.setBackgroundColor(Color.parseColor("#FFCCCC"))
            isValid = false
        }

        if (etLastName.text.isNullOrBlank()) {
            etLastName.setBackgroundColor(Color.parseColor("#FFCCCC"))
            isValid = false
        }

        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (etBirthday.text.isNullOrBlank()) {
            etBirthday.setBackgroundColor(Color.parseColor("#FFCCCC"))
            isValid = false
        }

        if (etAddress.text.isNullOrBlank()) {
            etAddress.setBackgroundColor(Color.parseColor("#FFCCCC"))
            isValid = false
        }

        if (etEmail.text.isNullOrBlank()) {
            etEmail.setBackgroundColor(Color.parseColor("#FFCCCC"))
            isValid = false
        }

        if (!cbTerms.isChecked) {
            Toast.makeText(this, "You must agree to the Terms of Use", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (isValid) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
        }
    }
}
