package com.example.gmail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var rvEmails: RecyclerView
    private lateinit var fabCompose: FloatingActionButton
    private lateinit var emailList: ArrayList<Email>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvEmails = findViewById(R.id.rvEmails)
        fabCompose = findViewById(R.id.fabCompose)

        // 1. Tạo dữ liệu giả lập
        emailList = arrayListOf(
            Email("Edurila.com", "$19 Only (First 10 spots)", "Are you looking to Learn Web Designing...", "12:34 PM", Color.parseColor("#4285F4")), // Blue
            Email("Chris Abad", "Help make Campaign Monitor better", "Let us know your thoughts! No Images...", "11:22 AM", Color.parseColor("#DB4437")), // Red
            Email("Tuto.com", "8h de formation gratuite", "Photoshop, SEO, Blender, CSS, WordPre...", "11:04 AM", Color.parseColor("#0F9D58")), // Green
            Email("Support", "Société Ovh : suivi de vos services", "SAS OVH - http://www.ovh.com 2 rue K...", "10:26 AM", Color.parseColor("#607D8B")), // Grey
            Email("Matt from Ionic", "The New Ionic Creator Is Here!", "Announcing the all-new Creator, build...", "9:45 AM", Color.parseColor("#F4B400")) // Yellow
        )

        // 2. Setup Adapter
        val adapter = EmailAdapter(emailList)
        rvEmails.layoutManager = LinearLayoutManager(this)
        rvEmails.adapter = adapter

        // 3. Sự kiện click FAB
        fabCompose.setOnClickListener {
            Toast.makeText(this, "Compose new email", Toast.LENGTH_SHORT).show()
        }
    }
}

// --- ADAPTER CLASS ---
class EmailAdapter(private val list: List<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    inner class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSender: TextView = itemView.findViewById(R.id.tvSender)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvAvatarLetter: TextView = itemView.findViewById(R.id.tvAvatarLetter)
        val cvAvatar: CardView = itemView.findViewById(R.id.cvAvatar)

        fun bind(email: Email) {
            tvSender.text = email.name
            // Nối Subject và Content để hiển thị giống Gmail
            tvContent.text = "${email.subject} - ${email.content}"
            tvTime.text = email.time

            // Lấy chữ cái đầu tiên của tên để đưa vào avatar
            tvAvatarLetter.text = email.name.first().toString().uppercase()

            // Set màu nền cho avatar
            cvAvatar.setCardBackgroundColor(email.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}