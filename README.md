# 有料信息流sdk接入文档

## 一、增加依赖

1. 在主project的`allprojects` -> `repositories`添加
    `注意这里的url有修改`
   ```groovy
   allprojects {
       repositories {
           google()
           jcenter()
         	// 增加下面一行
           maven { url "https://gitlab.droi.cn/youliao/Sdk/raw/master/"}
       }
   }
   ```

2. 在app工程的`dependencies`添加
    `注意这里的版本号有修改`
   ```groovy
   dependencies {
       // 增加下面依赖
       implementation 'com.youliao.sdk:news:1.0.3-beta05'
       // 如果使用glide3.x，增加依赖
       implementation 'com.youliao.sdk:glide3:1.0.3-beta05'
       // 如果使用glide4.x，增加依赖
       implementation 'com.youliao.sdk:glide4:1.0.3-beta05'
   }
   ```

3. 如果要使用adroi，并且之前没有接入过adroi sdk，请按照adroi sdk文档进行接入

4. 如果要使用穿山甲，并且之前没有接入过穿山甲 sdk，请按照穿山甲 sdk文档进行接入

## 二、初始化及基本配置

1. 在Application中的`onCreate`添加

   ```java
   // java
   // 此方法不会请求网络，请放在Application中调用，appid和secret参数会在之后提供
   YouliaoNewsSdk.init(this, "6346e1a6f5fc82ed", "5c16f8d71854b47601d3a31c87b0e0ab")
   	.setShareAppId("1107937097", "wx83f749fd20846f7f"); // qqappid，wxappid
    // 可以依赖'com.youliao.sdk:msa:1.0.3-beta05'，或者自行实现OaidProvider接口
    .setOaidProvider(new MasOaidProvider(this))
    // 可以依赖'com.youliao.sdk:amaplocation:1.0.3-beta05'，或者自行实现LocationProvider接口
    .setLocationProvider(new AMapLocationProvider(this));
   
   // 注意：此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
   YouliaoNewsSdk.requestSdkConfig();
   // 注意：此方法用于获取用户所在城市，请在获取定位权限后调用
   YouliaoNewsSdk.requestLocation();
   
   // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
   YouliaoNewsSdk.initAdroi("a8b3a9047", "ADroi广告demo")
   // 此方法用于初始化穿山甲sdk，如果已经接入过穿山甲sdk或不需要穿山甲广告，请忽略
   YouliaoNewsSdk.initBytedanceAd("5011189", "有料看看_测试_android");
   ```

   ```kotlin
   // kotlin
   YouliaoNewsSdk.apply {
     // 此方法不会请求网络，请放在Application中调用，appid和secret参数会在之后提供
     init(this@MyApplication, "6346e1a6f5fc82ed", "5c16f8d71854b47601d3a31c87b0e0ab")
     setShareAppId("1107937097","wx83f749fd20846f7f")
     // 可以依赖'com.youliao.sdk:msa:1.0.3-beta05'，或者自行实现OaidProvider接口
     setOaidProvider(MasOaidProvider(this@MyApplication))
     // 可以依赖'com.youliao.sdk:amaplocation:1.0.3-beta05'，或者自行实现LocationProvider接口
     setLocationProvider(AMapLocationProvider(this@MyApplication))
     // 注意：此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
     requestSdkConfig()
     // 注意：此方法用于获取用户所在城市，请在获取定位权限后调用
     requestLocation()
     
     // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
     initAdroi("a8b3a9047", "ADroi广告demo")
     // 此方法用于初始化穿山甲sdk，如果已经接入过穿山甲sdk或不需要穿山甲广告，请忽略
     initBytedanceAd("5011189", "有料看看_测试_android")
   }
   ```

2. 在Application中的`attachBaseContext`添加

   ```java
   // java
   @Override
   protected void attachBaseContext(Context base) {
     super.attachBaseContext(base);
     MultiDex.install(this);
     // 此方法用于初始化oaid sdk，如果已经接入过oaid sdk，请忽略
     // 请放在attachBaseContext中
     YouliaoNewsSdk.initOaid(base);
   }
   ```

   ```kotlin
   // kotlin
   override fun attachBaseContext(base: Context) {
     super.attachBaseContext(base)
     MultiDex.install(this)
     // 此方法用户初始化oaid sdk，如果已经接入过oaid sdk，请忽略
     // 请放在attachBaseContext中
     YouliaoNewsSdk.initOaid(base)
   }
   ```

## 三、使用

1. 创建NewsFragment并显示

   ```java
   // java
   FragmentManager fragmentManager = getSupportFragmentManager();
   FragmentTransaction transaction = fragmentManager.beginTransaction();
   fragment = NewsFragment.newInstance();
   // tab 类型配置 start==
   Bundle bundle = new Bundle();
   bundle.putString(NewsFragment.ARGUMENT_TYPE, "news"); // 默认为news，只有一个信息流页面时可以不设置
   bundle.putBoolean(NewsFragment.ARGUMENT_SWITCH, true); // 是否显示右下角的刷新按钮
   fragment.setArguments(bundle);
   // tab 类型配置 end==
   transaction.replace(R.id.container, fragment);
   transaction.commit();
   ```

   ```kotlin
   // kotlin
   val transaction = supportFragmentManager.beginTransaction()
   val fragment = NewsFragment.newInstance()
   // tab 类型配置 start==
   val bundle = Bundle()
   bundle.putString(NewsFragment.ARGUMENT_TYPE, "news") // 默认为news，只有一个信息流页面时可以不设置
   bundle.putBoolean(NewsFragment.ARGUMENT_SWITCH, true) // 是否显示右下角的刷新按钮
   fragment.arguments = bundle
   // tab 类型配置 end==
   transaction.replace(R.id.container, fragment)
   transaction.commit()
   ```

2. 设置主题色
  在`res`->`values`->`colors.xml`中覆盖以下字段
  ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <resources>
      <!--recyclerview顶部、底部提示背景色，负反馈文字边框颜色 等-->
      <color name="youliao_primary">#6FCCFF</color>
      <!--频道tab选中颜色，按钮颜色，详情页progressbar颜色等-->
      <color name="youliao_primary_dark">#005DFF</color>
   </resources>
  ```

3. 更换loading图
   在`res`->`mipmap`下覆盖`youliao_loading_logo.png`文件
   `注意`,每个dpi文件夹下都要覆盖

## 四、其他
1. for 最美天气

   由于最美天气接入的特殊性，提供简单的示例。需要最美的同学根据实际情况，进行简单的调整。

   示例请见demo下的java文件夹

2. 关于混淆
    混淆规则已经

