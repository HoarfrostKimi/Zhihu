package com.example.zhihu.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zhihu.R
import com.example.zhihu.network.bean.BannerItem

class BannerViewPagerAdapter(private val list: List<BannerItem>) :
    RecyclerView.Adapter<BannerViewPagerAdapter.BannerViewHolder>() {

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivBanner: ImageView = itemView.findViewById(R.id.banner_image)
        val tvTitle: TextView = itemView.findViewById(R.id.banner_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_activity_banner_item, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val item = list[position]

        // 使用正确的图像URL
        Glide.with(holder.ivBanner.context)
            .load(item.imageUrl)
            .into(holder.ivBanner)

        // 设置标题
        holder.tvTitle.text = item.title

        // 设置点击事件（可选）
        holder.itemView.setOnClickListener {
            // 处理点击事件，例如打开详情页
        }
    }

    override fun getItemCount(): Int = list.size
}