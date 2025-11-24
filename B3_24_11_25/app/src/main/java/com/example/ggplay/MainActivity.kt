package com.example.ggplay

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Setup RecyclerView DỌC (Suggested)
        val rvSuggested = findViewById<RecyclerView>(R.id.rvSuggested)
        val suggestedList = listOf(
            AppModel("Mech Assemble", "Action • RPG", "4.8", Color.RED),
            AppModel("MU: Hồng Hoả Đao", "Role Playing", "4.8", Color.DKGRAY),
            AppModel("War Inc: Rising", "Strategy", "4.9", Color.BLUE),
            AppModel("Mech Assemble", "Action • RPG", "4.8", Color.RED),
        )
        rvSuggested.layoutManager = LinearLayoutManager(this) // Mặc định là Vertical
        rvSuggested.adapter = SuggestedAdapter(suggestedList)

        // 2. Setup RecyclerView NGANG (Recommended)
        val rvRecommended = findViewById<RecyclerView>(R.id.rvRecommended)
        val recommendedList = listOf(
            AppModel("Suno", "Music & AI", "4.5", Color.parseColor("#FF9800")),
            AppModel("Claude", "Productivity", "4.7", Color.parseColor("#D78F5C")),
            AppModel("DramaBox", "Entertainment", "4.2", Color.parseColor("#E91E63")),
            AppModel("Pikachu", "Game", "4.6", Color.YELLOW)
        )
        // Set LayoutManager là HORIZONTAL
        rvRecommended.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvRecommended.adapter = RecommendedAdapter(recommendedList)
    }
}

// --- ADAPTER 1: Dành cho list dọc (Suggested) ---
class SuggestedAdapter(private val list: List<AppModel>) : RecyclerView.Adapter<SuggestedAdapter.ViewHolder>() {
    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvName: TextView = v.findViewById(R.id.tvAppName)
        val tvCat: TextView = v.findViewById(R.id.tvCategory)
        val tvRating: TextView = v.findViewById(R.id.tvRating)
        val imgIcon: ImageView = v.findViewById(R.id.imgIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_suggested, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvName.text = item.name
        holder.tvCat.text = item.category
        holder.tvRating.text = "${item.rating} ★"
        holder.imgIcon.setBackgroundColor(item.imageUrl) // Dùng setBackgroundColor để demo màu
    }

    override fun getItemCount() = list.size
}

// --- ADAPTER 2: Dành cho list ngang (Recommended) ---
class RecommendedAdapter(private val list: List<AppModel>) : RecyclerView.Adapter<RecommendedAdapter.ViewHolder>() {
    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvName: TextView = v.findViewById(R.id.tvRecName)
        val tvCat: TextView = v.findViewById(R.id.tvRecCat)
        val imgThumb: ImageView = v.findViewById(R.id.imgThumb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recommended, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvName.text = item.name
        holder.tvCat.text = item.category
        holder.imgThumb.setBackgroundColor(item.imageUrl)
    }

    override fun getItemCount() = list.size
}