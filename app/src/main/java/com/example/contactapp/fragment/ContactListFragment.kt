package com.example.contactapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.core.os.bundleOf
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
import com.example.contactapp.data.Contants.ITEM_DATA
import com.example.contactapp.databinding.FragmentContactListBinding



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ContactListFragment : Fragment() {
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
        val sortedList = ContactDatabase.totalContactData
        val clAdapter = ContactListAdapter(sortedList)
        with(binding) {             // 값은 저장되나, RecyclerView가 아이템을 변경하지 못함
            recyclerView.adapter = clAdapter
            recyclerView.layoutManager = LinearLayoutManager(mainPage, LinearLayoutManager.VERTICAL, false)
//            toast("어댑터 첫 연결 position = $userPosition = ${sortedList[userPosition].name}, ${sortedList[userPosition].favorite}")
            btnListGrid.setOnClickListener {
                listGrid *= -1
                binding.recyclerView.apply {
//                    toast("어댑터 2 연결 position = $userPosition = ${sortedList[userPosition].name}, ${sortedList[userPosition].favorite}")
                    when (listGrid) {
                        1 -> {
                            recyclerView.adapter = clAdapter
                            layoutManager = LinearLayoutManager(mainPage, LinearLayoutManager.VERTICAL, false)
                            btnListGrid.setImageResource(R.drawable.icon_grid_black)    // 현재가 list니 버튼을 누르면 Grid로 바꿀 수 있다는 것을 미리 보여주기 위해
                        }

                        -1 -> {
                            recyclerView.adapter = clAdapter
                            layoutManager = GridLayoutManager(mainPage, 3, GridLayoutManager.VERTICAL, false)
                            btnListGrid.setImageResource(R.drawable.icon_list_black)
                        }
                    }
                }
            }
        }


    }

    companion object {
        internal var userId = 0

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