# 有料信息流sdk接入文档

## 一、增加依赖

1. 在主project的`allprojects` -> `repositories`添加
   ```groovy
   allprojects {
       repositories {
           google()
           jcenter()
         	// 增加下面一行
           maven { url "https://github.com/YouliaoSdk/Sdk/raw/master/"}
       }
   }
   ```

2. 在app工程的`dependencies`添加
   ```groovy
   dependencies {
       // 增加下面依赖
       implementation 'com.youliao.sdk:news:1.0.5-rc01'
       // 如果使用glide3.x，增加依赖
       implementation 'com.youliao.sdk:glide3:1.0.4'
       // 如果使用glide4.x，增加依赖
       implementation 'com.youliao.sdk:glide4:1.0.4'
   }
   ```

3. 如果要使用adroi，并且之前没有接入过adroi sdk，请按照adroi sdk文档进行接入
**注意**
1.0.5版本对应的adroi sdk版本为`3.3.4`，请尽量保持一致，以免有兼容性问题

4. 如果要使用穿山甲，并且之前没有接入过穿山甲 sdk，请按照穿山甲 sdk文档进行接入
**注意**
由于穿山甲sdk最新版本3004存在问题，请先保持现有版本不变

5. 如果要使用穿山甲小视频sdk，并且之前没有接入过穿山甲 sdk，请按照穿山甲 sdk文档进行接入
    接入文档在本demo的顶层目录下`内容合作Sdk_Android_1.9.0.0.zip`
**注意**
只需要添加相应的sdk接口，初始化方法的封装在下文中提供

## 二、初始化及基本配置

1. 在Application中的`onCreate`添加

   ```java
   // java
   // 此方法不会请求网络，请放在Application中调用，appid和secret参数由有料提供，channel由接入方填入
   YouliaoNewsSdk.init(this, "appid", "apikey", "channel")
   	.setShareAppId("qqappid", "wxappid"); // qqappid，wxappid
    // 可以依赖'com.youliao.sdk:msa:1.0.4'，或者自行实现OaidProvider接口
    // 当前有料sdk接入的oaid sdk版本是 1.0.13
    .setOaidProvider(new MasOaidProvider(this))
    // 可以依赖'com.youliao.sdk:amaplocation:1.0.4'，或者自行实现LocationProvider接口
    // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
    .setLocationProvider(new AMapLocationProvider(this));
   
   // 注意：此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
   YouliaoNewsSdk.requestSdkConfig();
   // 注意：此方法用于获取用户所在城市，请在获取定位权限后调用。
   // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
   YouliaoNewsSdk.requestLocation();
   
   // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
   YouliaoNewsSdk.initAdroi("adroi-appid", "ADroi广告demo")
   // 此方法用于初始化穿山甲sdk，如果已经接入过穿山甲sdk或不需要穿山甲广告，请忽略
   YouliaoNewsSdk.initBytedanceAd("bytedance-appid", "有料看看_测试_android");

    // 此方法用于初始化穿山甲小视频sdk，如果接入穿山甲小视频sdk必须调用此方法，参数有料会提供
    YouliaoNewsSdk.initBytedanceDp("appId", "secureKey", "partner", "appLogId");
   ```

   ```kotlin
   // kotlin
   YouliaoNewsSdk.apply {
     // 此方法不会请求网络，请放在Application中调用，appid和secret参数会在之后提供，channel由接入方填入
     init(this@MyApplication, "appid", "apikey", "channel")
     setShareAppId("qqappid","wxappid")
     // 可以依赖'com.youliao.sdk:msa:1.0.4'，或者自行实现OaidProvider接口
     // 当前有料sdk接入的oaid sdk版本是 1.0.13
     setOaidProvider(MasOaidProvider(this@MyApplication))
     // 可以依赖'com.youliao.sdk:amaplocation:1.0.4'，或者自行实现LocationProvider接口
     // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
     setLocationProvider(AMapLocationProvider(this@MyApplication))
     // 注意：此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
     requestSdkConfig()
     // 注意：此方法用于获取用户所在城市，请在获取定位权限后调用
     // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
     requestLocation()
     
     // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
     initAdroi("adroi-appid", "ADroi广告demo")
     // 此方法用于初始化穿山甲sdk，如果已经接入过穿山甲sdk或不需要穿山甲广告，请忽略
     initBytedanceAd("bytedance-appid", "有料看看_测试_android")

     // 此方法用于初始化穿山甲小视频sdk，如果接入穿山甲小视频sdk必须调用此方法，参数有料会提供
     initBytedanceDp("appId", "secureKey", "partner", "appLogId")
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
     Msa.initOaid(base);
   }
   ```

   ```kotlin
   // kotlin
   override fun attachBaseContext(base: Context) {
     super.attachBaseContext(base)
     MultiDex.install(this)
     // 此方法用户初始化oaid sdk，如果已经接入过oaid sdk，请忽略
     // 请放在attachBaseContext中
     Msa.initOaid(base)
   }
   ```

## 三、使用

1. 创建NewsFragment并显示

   ```java
   // java
   FragmentManager fragmentManager = getSupportFragmentManager();
   FragmentTransaction transaction = fragmentManager.beginTransaction();
   fragment = NewsFragment.newInstance("news", false, "上海");
   // 第一次参数是 tab类型，默认为news，只有一个信息流页面时可以不设置
   // 第二个参数是 是否显示右下角的刷新按钮，默认false
   // 第三个参数是 城市名称，当在这里传入城市时，不要再调用requestLocation也不要调用setLocationProvider
   transaction.replace(R.id.container, fragment);
   transaction.commit();
   ```

   ```kotlin
   // kotlin
   val transaction = supportFragmentManager.beginTransaction()
   val fragment = NewsFragment.newInstance("news", false, "上海")
   // 第一次参数是 tab类型，默认为news，只有一个信息流页面时可以不设置
   // 第二个参数是 是否显示右下角的刷新按钮，默认false
   // 第三个参数是 城市名称，当在这里传入城市时，不要再调用requestLocation也不要调用setLocationProvider
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

4. 更换部分尺寸
  在`res`->`values`->`dimens.xml`中覆盖以下字段
  ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <resources>
    <!--顶部频道文字size-->
    <dimen name="youliao_tab_title">17sp</dimen>
    <!--新闻标题文字size-->
    <dimen name="youliao_news_title">18sp</dimen>
    <!--新闻左右边距-->
    <dimen name="youliao_news_margin_horizontal">15dp</dimen>
   </resources>
  ```

## 四、其他
1. for 最美天气

   由于最美天气接入的特殊性，提供简单的示例。需要最美的同学根据实际情况，进行简单的调整。

   示例请见demo下的java文件夹

2. for 浅言
    获取金币示例：app/src/main/java/com/youliao/news/view
    app/src/main/java/com/youliao/news/WebViewDemoActivity.kt
    ``` kotlin
    // 以下是设置新闻点击回调
    YouliaoNewsSdk.setClickActionProvider(object : ClickActionProvider {
        override fun onItemClick(newsBean: NewsBean, type: String, tabBean: TabBean?) {
            val intent = Intent(this@MainActivity, WebViewDemoActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("bean", newsBean)
            startActivity(intent)
        }
    })
    ```

3. 关于混淆
    混淆规则已经打在aar包里

