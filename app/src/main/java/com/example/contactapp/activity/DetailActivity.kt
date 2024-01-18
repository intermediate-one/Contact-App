package com.example.contactapp.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.contactapp.R
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.Contants
import com.example.contactapp.databinding.ActivityDetailBinding
import com.example.contactapp.fragment.ContactListFragment

class DetailActivity : AppCompatActivity() {

    private val data : ContactData? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Contants.ITEM_DATA, ContactData::class.java)
        }else {
            intent?.getParcelableExtra<ContactData>(Contants.ITEM_DATA)
        }
    }
    private val position by lazy {
        intent.getIntExtra(Contants.ITEM_INDEX,0)
    }
    //즐겨찾기 상태
    private var isFavorite = false

    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //리스트에서 데이터 받기
        binding.tvDetailName.text = data?.name
        binding.ivDetailPerson.setImageResource(data?.profileImage as Int)
        binding.tvDetailMobilePerson.text = data?.phoneNumber
        binding.tvDetailEmailPerson.text = data?.email

        isFavorite = data?.favorite == true
        //즐겨찾기 눌렀을 때와 안 눌렀을 때
        binding.ivDetailStar.setImageResource(if(isFavorite)R.drawable.star_full else R.drawable.star_empty)

        //즐겨찾기 눌렀을 때
        binding.ivDetailStar.setOnClickListener {
            if (!isFavorite) {
                binding.ivDetailStar.setImageResource(R.drawable.star_full)
                Toast.makeText(this ,R.string.detail_favorite, Toast.LENGTH_SHORT).show()
                isFavorite = true
                //그 외
            }else {
                binding.ivDetailStar.setImageResource(R.drawable.star_empty)
                Toast.makeText(this,R.string.detail_favorite_del, Toast.LENGTH_SHORT).show()
                isFavorite = false
            }
        }

        //뒤로가기(리스트로)
        binding.layoutDetailBack.setOnClickListener {
            val fragment = ContactListFragment()
            setFragment(fragment)
        }


    }
    private fun setFragment(frag : Fragment) {
        supportFragmentManager.commit {
            replace(R.id.viewPager_contact_activity_swipe,frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

}