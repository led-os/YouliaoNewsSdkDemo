package com.youliao.news

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.youliao.news.fragment.NewsTabFragment
import com.youliao.news.fragment.VideoTabFragment

class MainActivity : AppCompatActivity() {

    private val fragments = arrayListOf<Fragment>()
    private lateinit var viewpager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPagerFragment()
        initBottomNavigation()
    }

    private fun initViewPagerFragment() {
        viewpager = findViewById(R.id.view_pager)
        fragments.add(NewsTabFragment())
        fragments.add(VideoTabFragment())
        val myFragmentPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager, fragments)
        viewpager.adapter = myFragmentPagerAdapter
        viewpager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                bottomNavigationView.selectedItemId = when (position) {
                    0 -> R.id.navigation_news
                    1 -> R.id.navigation_video
                    else -> R.id.navigation_news
                }
            }

        })
    }

    private fun initBottomNavigation() {
        bottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_news -> {
                    viewpager.currentItem = 0
                    true
                }
                R.id.navigation_video -> {
                    viewpager.currentItem = 1
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}
