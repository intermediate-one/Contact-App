package com.example.contactapp.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.R
import com.example.contactapp.activity.AddContactActivity
import com.example.contactapp.activity.ContactActivity
import com.example.contactapp.activity.DetailActivity
import com.example.contactapp.adaptor.ContactListAdapter
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.data.Contants
import com.example.contactapp.databinding.FragmentContactListBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ContactListFragment : Fragment() {
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private var param1: String? = null
    private var param2: String? = null


    private var _binding:FragmentContactListBinding? = null
    private val binding get() = _binding!!
    private val mainPage by lazy { context as ContactActivity }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickFloatingActionButtonAddContact()
        var sortedList = ContactDatabase.nameSorting()
        var clAdapter = ContactListAdapter(sortedList)
        with(binding) {
            recyclerView.adapter = clAdapter
            recyclerView.layoutManager = LinearLayoutManager(mainPage, LinearLayoutManager.VERTICAL, false)
            btnListGrid.setOnClickListener {
                listGrid *= -1
                binding.recyclerView.apply {
                    when (listGrid) {
                        1 -> {
                            layoutManager = LinearLayoutManager(mainPage, LinearLayoutManager.VERTICAL, false)
                            btnListGrid.setImageResource(R.drawable.icon_grid_black)    // 현재가 list니 버튼을 누르면 Grid로 바꿀 수 있다는 것을 미리 보여주기 위해
                            clAdapter.notifyItemRangeChanged(userPosition,sortedList.size)
                        }

                        -1 -> {
                            layoutManager = GridLayoutManager(mainPage, 3, GridLayoutManager.VERTICAL, false)
                            btnListGrid.setImageResource(R.drawable.icon_list_black)
                            clAdapter.notifyItemRangeChanged(userPosition,sortedList.size)
                        }
                    }
                }
            }
            //Detail에서 받았을 때
            activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    val isFavorite = it.data?.getBooleanExtra("isFavorite",false) as Boolean
                    val itemNum = it.data?.getIntExtra(Contants.ITEM_INDEX,0) as Int

                    if (isFavorite) {
                        sortedList[itemNum].favorite = true
                    } else {
                        if (sortedList[itemNum].favorite) {
                            sortedList[itemNum].favorite = false
                        }
                    }
                    //어댑터 갱신해주는 코드
                    clAdapter.notifyItemChanged(itemNum)
                //수정된 값 받아오기
                }else if (it.resultCode == AppCompatActivity.RESULT_FIRST_USER) {
                    val isFavorite = it.data?.getBooleanExtra("isFavorite",false) as Boolean
                    val itemNum = it.data?.getIntExtra(Contants.ITEM_INDEX,0) as Int
                    val data: ContactData? by lazy {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            arguments?.getParcelable(Contants.ITEM_DATA, ContactData::class.java)
                        } else {
                            arguments?.getParcelable<ContactData>(Contants.ITEM_DATA)
                        }
                    }
                    if (isFavorite) {
                        sortedList[itemNum].favorite = true
                    } else {
                        if (sortedList[itemNum].favorite) {
                            sortedList[itemNum].favorite = false
                        }
                    }
                    sortedList[itemNum].name = data?.name ?: "이름"
                    sortedList[itemNum].email = data?.email ?: "이름"
                    sortedList[itemNum].group = data?.group ?: "이름"
                    sortedList[itemNum].address = data?.address ?: "이름"
                    sortedList[itemNum].birthday = data?.birthday ?: "이름"
                    sortedList[itemNum].mbti = data?.mbti ?: "이름"
                    sortedList[itemNum].notification = data?.notification ?: null
                    sortedList[itemNum].phoneNumber = data?.phoneNumber ?: "010-1234-1234"
                    sortedList[itemNum].memo = data?.memo ?: "010-1234-1234"
                    sortedList[itemNum].profileImage = data?.profileImage as Int
                    userPosition = itemNum
                    clAdapter.notifyItemRangeChanged(userPosition,sortedList.size)
                }
            }
            //Detail로 보내고 다시 값 받기
            clAdapter.itemClick = object : ContactListAdapter.ItemClick{
                override fun onClick(view: View, position: Int) {
                    val intent = Intent(activity,DetailActivity::class.java)
                    intent.putExtra(Contants.ITEM_DATA,sortedList[position])
                    intent.putExtra(Contants.ITEM_INDEX,position)
                    activityResultLauncher.launch(intent)
                }
            }
//            clAdapter.favChange = object : ContactListAdapter.FavoriteChange {
//                override fun favChanged(view: View, position: Int) {
//                    sortedList = ContactDatabase.nameSorting()
//                    clAdapter = ContactListAdapter(sortedList)
//                    recyclerView.adapter = clAdapter
//                    when (listGrid) {
//                        1 -> {
//                            binding.recyclerView.layoutManager = LinearLayoutManager(mainPage, LinearLayoutManager.VERTICAL, false)
//                            btnListGrid.setImageResource(R.drawable.icon_grid_black)    // 현재가 list니 버튼을 누르면 Grid로 바꿀 수 있다는 것을 미리 보여주기 위해
//                        }
//                        -1 -> {
//                            binding.recyclerView.layoutManager = GridLayoutManager(mainPage, 3, GridLayoutManager.VERTICAL, false)
//                            btnListGrid.setImageResource(R.drawable.icon_list_black)
//                        }
//                    }
//                }
//            }
        }
    }
    //임시
//    override fun onResume() {
//        super.onResume()
//        binding.recyclerView.adapter?.notifyDataSetChanged()
//    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        var userPosition = 0
        var listGrid = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun toast(s:String) {
        Toast.makeText(mainPage,s,Toast.LENGTH_SHORT).show()
    }


    // 플로팅 액션 버튼 클릭 시 연락처 추가 액티비티 (AddContactActivity.kt)로 이동하는 코드
    private fun onClickFloatingActionButtonAddContact() {
        binding.fbtnContactListAdd.setOnClickListener {
            val intent = Intent(activity, AddContactActivity::class.java)
            startActivity(intent)
        }
    }
}