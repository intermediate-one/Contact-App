package com.example.contactapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewpager2.widget.ViewPager2
import com.example.contactapp.R
import com.example.contactapp.adaptor.ContactListAdapter
import com.example.contactapp.fragment.MyPageFragment
import com.example.contactapp.adaptor.ViewPager2Adapter
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.data.Contants
import com.example.contactapp.databinding.ActivityContactBinding
import com.example.contactapp.fragment.ContactListFragment
import com.example.contactapp.fragment.GroupFragment
import com.example.contactapp.fragment.TrashBinFragment
import com.example.contactapp.notification.MyManagers
import com.google.android.material.tabs.TabLayoutMediator

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MyManagers.init(applicationContext)
        initViewPager()
    }

//    override fun onRestart() {
//        super.onRestart()
//        val position : Int by lazy {
//            intent.getIntExtra(Contants.ITEM_INDEX,0)
//        }
//        if (position >=0) {
//            setFragment(passData(ContactListFragment()))
//        }
//    }

    private fun initViewPager() {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adapter = ViewPager2Adapter(this)
        viewPager2Adapter.addFragment(ContactListFragment())
        viewPager2Adapter.addFragment(GroupFragment())
        viewPager2Adapter.addFragment(TrashBinFragment())
        viewPager2Adapter.addFragment(MyPageFragment())

        //Adapter 연결
        binding.viewPagerContactActivitySwipe.apply {
            adapter = viewPager2Adapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.tabLayoutContactActivitySwitch, binding.viewPagerContactActivitySwipe) { tab, position ->
            Log.e("YMC", "ViewPager position: $position")
            when (position) {
                0 -> tab.text = "연락처"
                1 -> tab.text = "그룹"
                2 -> tab.text = "휴지통"
                3 -> tab.text = "마이페이지"
            }
        }.attach()
    }

//    //Detail에서 받은 정보를 ContactListFragment로 전달
//    private fun passData(frag : Fragment) : Fragment {
//        val data = intent.getParcelableExtra<ContactData>(Contants.ITEM_DATA)
//        val favorite = intent.getBooleanExtra("favorite",false)
//        val index = intent.getIntExtra(Contants.ITEM_INDEX,0)
//        Log.d("ContactActivity","받기 : $favorite")
//        val bundle = Bundle()
//        bundle.putInt(Contants.ITEM_INDEX,index)
//        bundle.putParcelable(Contants.ITEM_DATA,data)
//        bundle.putBoolean("favorite",favorite)
//        frag.arguments = bundle
//        return frag
//
//    }
//    private fun setFragment(frag : Fragment) {
//        supportFragmentManager.commit {
//            replace(R.id.viewPager_contact_activity_swipe,frag)
//            setReorderingAllowed(true)
//            addToBackStack("")
//        }
//    }

}