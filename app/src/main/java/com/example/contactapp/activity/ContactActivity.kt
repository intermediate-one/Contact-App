package com.example.contactapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.contactapp.ContactListFragmentActivity
import com.example.contactapp.adaptor.ViewPager2Adapter
import com.example.contactapp.databinding.ActivityContactBinding
import com.google.android.material.tabs.TabLayoutMediator

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

    }

    private fun initViewPager() {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adatper = ViewPager2Adapter(this)
        viewPager2Adatper.addFragment(ContactListFragmentActivity())
//        viewPager2Adatper.addFragment(Tab2Fragment())
//        viewPager2Adatper.addFragment(Tab3Fragment())

        //Adapter 연결
        binding.viewPagerContactActivitySwipe.apply {
            adapter = viewPager2Adatper

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.tabLayoutContactActivitySwitch, binding.viewPagerContactActivitySwipe) { tab, position ->
            Log.e("YMC", "ViewPager position: ${position}")
            when (position) {
                0 -> tab.text = "연락처"
                1 -> tab.text = "마이페이지"
            }
        }.attach()
    }
}