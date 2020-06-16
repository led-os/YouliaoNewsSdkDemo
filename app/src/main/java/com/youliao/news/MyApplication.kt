package com.youliao.news

import android.app.Application
import android.content.Context
import android.os.Build
import android.support.multidex.MultiDex
import android.webkit.WebView
import com.youliao.sdk.location.AMapLocationProvider
import com.youliao.sdk.msa.MasOaidProvider
import com.youliao.sdk.msa.Msa
import com.youliao.sdk.news.YouliaoNewsSdk
import com.youliao.sdk.news.provider.AnalyticsProvider

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        YouliaoNewsSdk.apply {
            // 此方法不会请求网络，请放在Application中调用
//            init(this@MyApplication, "6346e1a6f5fc82ed", "5c16f8d71854b47601d3a31c87b0e0ab", "Test")
            init(this@MyApplication, "ffc2c212540d1370", "91492c9b2e6ac7d87713c85b83242449", "Test")
//            init(this@MyApplication, "6346e1a6f5fc82ed", "5c16f8d71854b47601d3a31c87b0e0ab", "Test")
//            init(this@MyApplication, "ffc2c212540d1370", "91492c9b2e6ac7d87713c85b83242449", "Test")
            showDebugLog(true)
            setShareAppId("1107990332", "wx8b0b139d1103eaa0")
            // 可以依赖'com.youliao.sdk:msa:1.0.3-beta01'，或者自行实现OaidProvider接口
            setOaidProvider(MasOaidProvider(this@MyApplication))
            // 可以依赖'com.youliao.sdk:amaplocation:1.0.3-beta01'，或者自行实现LocationProvider接口
            setLocationProvider(AMapLocationProvider(this@MyApplication))

            setAnalyticsProvider(object : AnalyticsProvider {
                override fun onEventObject(eventId: String, map: Map<String, Any>, api: Boolean) {
                }
            })
            // 此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
            requestSdkConfig()
            // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
//            initAdroi("a8b3a9047", "有料看看_android")
            // 此方法用于初始化穿山甲sdk，如果已经接入过穿山甲sdk或不需要穿山甲广告，请忽略
            initBytedanceAd("5011189", "有料看看_测试_android")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        // 此方法用户初始化oaid sdk，如果已经接入过msa sdk，请忽略
        // 请放在attachBaseContext中
        Msa.initOaid(base)
    }
}