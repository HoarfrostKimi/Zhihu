package com.example.zhihu.network.api

import com.example.zhihu.network.bean.banner
import retrofit2.http.GET

interface ZhihuApi {
    @GET("api/4/news/latest")
    suspend fun getLatestNews(): banner
}