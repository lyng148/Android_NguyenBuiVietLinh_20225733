package com.example.danhsachsinhvien // Đổi package theo project của bạn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etMSSV: EditText
    private lateinit var etName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView: RecyclerView

    private val studentList = ArrayList<Student>()
    private lateinit var adapter: StudentAdapter

    // Biến lưu vị trí phần tử đang được chọn để sửa
    private var currentEditingIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Ánh xạ View
        etMSSV = findViewById(R.id.etMSSV)
        etName = findViewById(R.id.etName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

        // 2. Dữ liệu mẫu (nếu cần)
        studentList.add(Student("20200001", "Nguyễn Văn A"))
        studentList.add(Student("20200002", "Trần Thị B"))

        // 3. Cấu hình RecyclerView và Adapter
        adapter = StudentAdapter(studentList,
            onDeleteClick = { position -> deleteStudent(position) },
            onItemClick = { position -> showStudentDetail(position) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 4. Xử lý nút ADD
        btnAdd.setOnClickListener {
            val mssv = etMSSV.text.toString()
            val name = etName.text.toString()

            if (mssv.isNotEmpty() && name.isNotEmpty()) {
                val newStudent = Student(mssv, name)
                studentList.add(newStudent)
                adapter.notifyItemInserted(studentList.size - 1)
                clearInput()
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        // 5. Xử lý nút UPDATE
        btnUpdate.setOnClickListener {
            if (currentEditingIndex != -1) {
                val mssv = etMSSV.text.toString()
                val name = etName.text.toString()

                if (mssv.isNotEmpty() && name.isNotEmpty()) {
                    // Cập nhật dữ liệu trong list
                    studentList[currentEditingIndex].mssv = mssv
                    studentList[currentEditingIndex].hoTen = name

                    // Thông báo adapter cập nhật lại dòng đó
                    adapter.notifyItemChanged(currentEditingIndex)

                    clearInput()
                    currentEditingIndex = -1 // Reset trạng thái
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Chưa chọn sinh viên để sửa", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hàm xóa sinh viên
    private fun deleteStudent(position: Int) {
        studentList.removeAt(position)
        adapter.notifyItemRemoved(position)
        // Cập nhật lại range để tránh lỗi index khi xóa tiếp
        adapter.notifyItemRangeChanged(position, studentList.size)
        clearInput()
        currentEditingIndex = -1
    }

    // Hàm hiển thị thông tin lên EditText khi nhấn vào dòng
    private fun showStudentDetail(position: Int) {
        val student = studentList[position]
        etMSSV.setText(student.mssv)
        etName.setText(student.hoTen)
        currentEditingIndex = position
    }

    // Hàm xóa trắng ô nhập
    private fun clearInput() {
        etMSSV.setText("")
        etName.setText("")
        etMSSV.requestFocus()
    }
}

// --- ADAPTER CLASS (Bạn có thể tách ra file riêng StudentAdapter.kt) ---
class StudentAdapter(
    private val list: List<Student>,
    private val onDeleteClick: (Int) -> Unit,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvMSSV: TextView = itemView.findViewById(R.id.tvMSSV)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(student: Student, position: Int) {
            tvName.text = student.hoTen
            tvMSSV.text = student.mssv

            // Xử lý sự kiện xóa
            btnDelete.setOnClickListener {
                onDeleteClick(position)
            }

            // Xử lý sự kiện nhấn vào dòng (Item view)
            itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}