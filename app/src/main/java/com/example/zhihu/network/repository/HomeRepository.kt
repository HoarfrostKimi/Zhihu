package com.example.zhihu.repository


import com.example.zhihu.network.RetrofitClient
import com.example.zhihu.network.api.ZhihuApi
import com.example.zhihu.network.bean.BannerItem
import com.example.zhihu.network.bean.Story
import com.example.zhihu.network.bean.TopStory
import com.example.zhihu.network.bean.banner
import com.example.zhihu.network.bean.news


class HomeRepository {

    private val api: ZhihuApi = RetrofitClient.api

    suspend fun getHomeData(): Pair<List<BannerItem>, List<news>> {
        val response = api.getLatestNews()

        val banners = response.top_stories.map {
            BannerItem(
                id = it.id,
                title = it.title,
                imageUrl = it.image,
                hint = it.hint,
                url = it.url
            )
        }
        val newsList=response.stories.map {
            news(
                id = it.id,
                title = it.title,
                author = it.hint,
                imageUrl = it.images.firstOrNull() ?: "",
                url = it.url
            )
        }
        return Pair(banners, newsList)
    }
}
