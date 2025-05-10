package com.example.zhihu

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var webview:WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)
        webview=findViewById(R.id.webView)
        webview.settings.javaScriptEnabled=true
        webview.webViewClient = WebViewClient()
        webview.loadUrl("https://daily.zhihu.com/story/9781036")

    }

}