package com.example.contactapp.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.contactapp.R
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.Contants
import com.example.contactapp.databinding.FragmentDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

///**
// * A simple [Fragment] subclass.
// * Use the [DetailFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
class DetailFragment : Fragment() {
//    private val binding by lazy { FragmentDetailBinding.inflate(layoutInflater) }
    //메모리 누수를 막기 위해
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    //데이터 받아오기
    private val data : ContactData? by lazy {
//        arguments?.getParcelable(Contants.ITEM_DATA,ContactData::class.java)
//        arguments?.getParcelable<ContactData>(Contants.ITEM_DATA)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Contants.ITEM_DATA,ContactData::class.java)
        }else {
            arguments?.getParcelable<ContactData>(Contants.ITEM_DATA)
        }
    }
    private val position : ContactData? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Contants.IEIM_INDEX,ContactData::class.java)
        }else {
            arguments?.getParcelable<ContactData>(Contants.IEIM_INDEX)
        }
    }
//    onCreate 생략가능 -> 데이터를
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
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

        //리스트에서 데이터 받기
        binding.tvDetailNamePerson.text = data?.name
        binding.ivDetailPerson.setImageResource(data?.profileImage as Int)
        binding.tvDetailMobilePerson.text = data?.phoneNumber
        binding.tvDetailEmailPerson.text = data?.email
        //리스트로 데이터 보내기
        binding.layoutDetailBack.setOnClickListener {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}