package com.example.zhihu.ui.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zhihu.R

class HomeActivity : AppCompatActivity() {
    // 注册控件
    private lateinit var tvHomeDay: TextView
    private lateinit var tvHomeMonth: TextView
    private lateinit var tvHomeRiBao: TextView
    private lateinit var ivHomePicture: ImageView
    private lateinit var rvHomeList: RecyclerView
    private val viewmodel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        initView()
        initObserver()
        viewmodel.loadHomeData()
    }

    private fun initObserver() {
        viewmodel.banners.observe(this) { bannerList ->
            homeAdapter.setBannerList(bannerList)
        }

        viewmodel.newsList.observe(this) { newsList ->
            homeAdapter.setNewsList(newsList)
        }
    }

    private fun initView() {
        ivHomePicture = findViewById(R.id.home_im_picture)
        tvHomeRiBao = findViewById(R.id.home_tx_ribao)
        tvHomeMonth = findViewById(R.id.home_tx_month)
        tvHomeDay = findViewById(R.id.home_tx_day)
        rvHomeList = findViewById(R.id.home_rv)

        rvHomeList.layoutManager = LinearLayoutManager(this)
        homeAdapter = HomeAdapter()
        rvHomeList.adapter = homeAdapter
    }
}