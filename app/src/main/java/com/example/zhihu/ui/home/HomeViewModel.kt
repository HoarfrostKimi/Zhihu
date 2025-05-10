package com.example.zhihu.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zhihu.network.bean.BannerItem
import com.example.zhihu.network.bean.TopStory
import com.example.zhihu.network.bean.banner
import com.example.zhihu.network.bean.news
import com.example.zhihu.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {
    private val repository = HomeRepository()
    private val _banners = MutableLiveData<List<BannerItem>>()
    val banners: LiveData<List<BannerItem>> = _banners

    private val _newsList = MutableLiveData<List<news>>()
    val newsList: LiveData<List<news>> = _newsList
    fun loadHomeData() {
        viewModelScope.launch {
            try {
                val (bannerData, newsData) = repository.getHomeData()
                _banners.value = bannerData
                _newsList.value = newsData

            }catch (e: Exception) {
                // 可以加错误处理
                Log.d("HomeViewModel", "Network request error: ${e.message}", e)
                e.printStackTrace()
            }
        }

    }
}