package com.youliao.news

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.youliao.sdk.news.YouliaoNewsSdk

class  MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        YouliaoNewsSdk.apply {
            // 此方法不会请求网络，请放在Application中调用
            init(this@MyApplication, "6346e1a6f5fc82ed", "5c16f8d71854b47601d3a31c87b0e0ab")
            showDebugLog(true)
            setShareAppId("1107990332","wx8b0b139d1103eaa0")
            // 此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
            requestSdkConfig()
            // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
            initAdroi("a8b3a9047", "ADroi广告demo")
            // 此方法用于初始化穿山甲sdk，如果已经接入过穿山甲sdk或不需要穿山甲广告，请忽略
            initBytedanceAd("5011189", "有料看看_测试_android")
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        // 此方法用户初始化oaid sdk，如果已经接入过msa sdk，请忽略
        // 请放在attachBaseContext中
        YouliaoNewsSdk.initOaid(base)
    }
}