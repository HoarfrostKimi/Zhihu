package com.example.zhihu.ui.home

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.zhihu.R
import com.example.zhihu.network.bean.BannerItem
import com.example.zhihu.network.bean.news

class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val newsList = mutableListOf<news>()
    private val bannerList = mutableListOf<BannerItem>()
    private var bannerAdapter: BannerViewPagerAdapter? = null
    private var handler: Handler? = null
    private var autoScrollRunnable: Runnable? = null

    companion object {
        private const val TYPE_BANNER = 0
        private const val TYPE_NEWS = 1
    }

    fun setNewsList(list: List<news>) {
        newsList.clear()
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    fun setBannerList(list: List<BannerItem>) {
        bannerList.clear()
        bannerList.addAll(list)
        notifyDataSetChanged()

        // 如果banner已经设置过adapter，更新数据
        bannerAdapter?.notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && bannerList.isNotEmpty()) TYPE_BANNER else TYPE_NEWS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_BANNER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_banner_container, parent, false)
            BannerViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_activity_stories_item, parent, false)
            NewsViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_BANNER) {
            val bannerHolder = holder as BannerViewHolder

            // 创建并设置banner适配器
            if (bannerAdapter == null) {
                bannerAdapter = BannerViewPagerAdapter(bannerList)
            }

            bannerHolder.viewPager.adapter = bannerAdapter

            // 设置自动滚动
            setupAutoScroll(bannerHolder.viewPager)

        } else {
            val actualPosition = if (bannerList.isNotEmpty()) position - 1 else position
            val item = newsList[actualPosition]
            val newsHolder = holder as NewsViewHolder

            newsHolder.tvTitle.text = item.title
            newsHolder.tvAuthor.text = item.author

            // 使用正确的图像URL
            Glide.with(newsHolder.ivImage.context)
                .load(item.imageUrl)
                .into(newsHolder.ivImage)
        }
    }

    private fun setupAutoScroll(viewPager: ViewPager2) {
        // 清除旧的回调
        autoScrollRunnable?.let { runnable ->
            handler?.removeCallbacks(runnable)
        }

        // 创建新的Handler和Runnable
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }

        autoScrollRunnable = object : Runnable {
            override fun run() {
                if (bannerList.size > 1) {
                    val nextItem = (viewPager.currentItem + 1) % bannerList.size
                    viewPager.setCurrentItem(nextItem, true)
                    handler?.postDelayed(this, 3000)
                }
            }
        }

        // 开始自动滚动
        handler?.postDelayed(autoScrollRunnable!!, 3000)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is BannerViewHolder) {
            // 当ViewHolder被回收时，停止自动滚动
            autoScrollRunnable?.let { runnable ->
                handler?.removeCallbacks(runnable)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size + if (bannerList.isNotEmpty()) 1 else 0
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.news_title)
        val tvAuthor: TextView = itemView.findViewById(R.id.news_author)
        val ivImage: ImageView = itemView.findViewById(R.id.news_image)
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewPager: ViewPager2 = itemView.findViewById(R.id.viewPager)
    }
}