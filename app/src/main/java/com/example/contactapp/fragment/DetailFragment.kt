package com.example.contactapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.contactapp.R
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.Contants
import com.example.contactapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    //메모리 누수를 막기 위해
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    //즐겨찾기 상태
    private var isFavorite = false

//    //데이터 받아오기
//    private val data : ContactData? by lazy {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            arguments?.getParcelable(Contants.ITEM_DATA,ContactData::class.java)
//        }else {
//            arguments?.getParcelable<ContactData>(Contants.ITEM_DATA)
//        }
//    }
//    private val position : ContactData? by lazy {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            arguments?.getParcelable(Contants.ITEM_INDEX,ContactData::class.java)
//        }else {
//            arguments?.getParcelable<ContactData>(Contants.ITEM_INDEX)
//        }
//    }
//    onCreate 생략가능 -> 데이터를
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        //리스트에서 데이터 받기
//        binding.tvDetailName.text = data?.name
//        binding.ivDetailPerson.setImageResource(data?.profileImage as Int)
//        binding.tvDetailMobilePerson.text = data?.phoneNumber
//        binding.tvDetailEmailPerson.text = data?.email
//
//        isFavorite = data?.favorite == true
        //즐겨찾기 눌렀을 때와 안 눌렀을 때
        binding.ivDetailStar.setImageResource(if(isFavorite)R.drawable.star_full else R.drawable.star_empty)

        //즐겨찾기 눌렀을 때
        binding.ivDetailStar.setOnClickListener {
            if (!isFavorite) {
                binding.ivDetailStar.setImageResource(R.drawable.star_full)
                Toast.makeText(context,R.string.detail_favorite,Toast.LENGTH_SHORT).show()
                isFavorite = true
                //그 외
            }else {
                binding.ivDetailStar.setImageResource(R.drawable.star_empty)
                Toast.makeText(context,R.string.detail_favorite_del,Toast.LENGTH_SHORT).show()
                isFavorite = false
            }
        }



        //뒤로가기
//        binding.layoutDetailBack.setOnClickListener {
//            val fragmentList = ContactListFragment()
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.viewPager_contact_activity_swipe,fragmentList)
//                .addToBackStack(null)
//                .commit()
//        }
        //리스트로 데이터 보내기
//        binding.layoutDetailBack.setOnClickListener {
//            val bundle = Bundle()
////            bundle.putParcelable(Contants.ITEM_DATA,data)
//            bundle.putBoolean(Contants.ITEM_LIKE,isFavorite)
//            bundle.putParcelable(Contants.ITEM_INDEX,position)
//            val fragmentList = ContactListFragment()
//            fragmentList.arguments = bundle
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.viewPager_contact_activity_swipe,fragmentList)
//                .addToBackStack(null)
//                .commit()
//        }

        //문자보내기
        binding.btnDetailMessage.setOnClickListener {
            val mobileNumber = binding.tvDetailMobilePerson.text
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:${mobileNumber}")))
        }
        //전화하기
        binding.btnDetailCall.setOnClickListener {
            val mobileNumber = binding.tvDetailMobilePerson.text
            startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${mobileNumber}")))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}