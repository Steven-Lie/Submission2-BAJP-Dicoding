package com.dicoding.moviecatalogue.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.moviecatalogue.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        val sectionPageAdapter = SectionPageAdapter(this)
        val viewPager: ViewPager2 = activityHomeBinding.viewPager
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = activityHomeBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getString(SectionPageAdapter.TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }
}