package com.example.contactapp.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.contactapp.R
import com.example.contactapp.adaptor.ContactListAdapter
import com.example.contactapp.data.ActType
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.data.Contants
import com.example.contactapp.databinding.ActivityDetailBinding
import com.example.contactapp.fragment.ContactListFragment


class DetailActivity : AppCompatActivity() {

    //데이터 받기
    private val data: ContactData? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Contants.ITEM_DATA, ContactData::class.java)
        } else {
            intent?.getParcelableExtra<ContactData>(Contants.ITEM_DATA)
        }
    }
    private val position: Int by lazy {
        intent.getIntExtra(Contants.ITEM_INDEX, 0)
    }

    //즐겨찾기 상태
    private var isFavorite = false

    //수정해서 다시 돌아오기
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //리스트에서 데이터 받기
        data?.profileImage?.let { binding.ivDetailPerson.setImageResource(it) }
        binding.tvDetailName.text = data?.name
        binding.tvDetailMobilePerson.text = data?.phoneNumber
        binding.tvDetailEmailPerson.text = data?.email
        binding.tvDetailGroupPerson.text = data?.group
        binding.tvDetailMbtiPerson.text = data?.mbti
        binding.tvDetailMemoPerson.text = data?.memo
        binding.tvDetailLocationPerson.text = data?.address
        binding.tvDetailBirthPerson.text = data?.birthday
        binding.tvDetailNotifyPerson.text = data?.notification?.toChar().toString()

        isFavorite = data?.favorite == true


        //즐겨찾기 눌렀을 때와 안 눌렀을 때
        binding.ivDetailStar.setImageResource(if (isFavorite) R.drawable.star_full else R.drawable.star_empty)

        //즐겨찾기 눌렀을 때
        binding.ivDetailStar.setOnClickListener {
            val list = ContactDatabase.totalContactData
            if (!isFavorite) {
                binding.ivDetailStar.setImageResource(R.drawable.star_full)
                Toast.makeText(this, R.string.detail_favorite, Toast.LENGTH_SHORT).show()
//                list.find { it.phoneNumber == data?.phoneNumber }?.favorite = true
                isFavorite = true
                //그 외
            } else {
                binding.ivDetailStar.setImageResource(R.drawable.star_empty)
                Toast.makeText(this, R.string.detail_favorite_del, Toast.LENGTH_SHORT).show()
//                list.find { it.phoneNumber == data?.phoneNumber }?.favorite = false
                isFavorite = false
            }
        }

        //메세지보내기
        binding.btnDetailMessage.setOnClickListener {
            val mobileNumber = binding.tvDetailMobilePerson.text
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("sms:${mobileNumber}")))
        }
        //전화하기
        binding.btnDetailCall.setOnClickListener {
            val mobileNumber = binding.tvDetailMobilePerson.text
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("tel:${mobileNumber}")))
        }

        //뒤로가기(리스트로)
        binding.layoutDetailBack.setOnClickListener {
            //리스트로 즐겨찾기 변한 값 넘기기
            val intent = Intent(this, ContactListFragment::class.java)
            intent.putExtra(Contants.ITEM_INDEX, position)
            intent.putExtra("isFavorite", isFavorite)
            setResult(AppCompatActivity.RESULT_OK, intent)
            finish()
        }

        //수정하기
        binding.layoutDetailEdit.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            intent.putExtra(Contants.ITEM_DATA, data)
            intent.putExtra(Contants.ITEM_INDEX, position)
            intent.putExtra(Contants.ActType, ActType.EDIT_MY_PAGE)
            activityResultLauncher.launch(intent)

        }

        //수정해서 받기
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == RESULT_OK) {
//                val data = intent?.getParcelableExtra<ContactData>(Contants.ITEM_DATA)
//                val indexNum = intent?.getIntExtra(Contants.ITEM_INDEX,0)
                    val data: ContactData? by lazy {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            intent?.getParcelableExtra(Contants.ITEM_DATA, ContactData::class.java)
                        } else {
                            intent?.getParcelableExtra<ContactData>(Contants.ITEM_DATA)
                        }
                    }
                    data?.profileImage?.let { binding.ivDetailPerson.setImageResource(it) }
                    binding.tvDetailName.text = data?.name
                    binding.tvDetailMobilePerson.text = data?.phoneNumber
                    binding.tvDetailEmailPerson.text = data?.email
                    binding.tvDetailGroupPerson.text = data?.group
                    binding.tvDetailMbtiPerson.text = data?.mbti
                    binding.tvDetailMemoPerson.text = data?.memo
                    binding.tvDetailLocationPerson.text = data?.address
                    binding.tvDetailBirthPerson.text = data?.birthday
                    binding.tvDetailNotifyPerson.text = data?.notification?.toChar().toString()

                    //뒤로가기(리스트로)
                    binding.layoutDetailBack.setOnClickListener {
                        //리스트로 즐겨찾기 변한 값 넘기기
                        val intent = Intent(this, ContactListFragment::class.java)
                        intent.putExtra(Contants.ITEM_INDEX, position)
                        intent.putExtra(Contants.ITEM_DATA, data)
                        intent.putExtra("isFavorite", isFavorite)
                        setResult(AppCompatActivity.RESULT_FIRST_USER, intent)
                        finish()

                    }
                }

            }
    }
}


