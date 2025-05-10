package com.example.zhihu.network

import com.example.zhihu.network.api.ZhihuApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://news-at.zhihu.com/"

    val api: ZhihuApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ZhihuApi::class.java)
    }
}
