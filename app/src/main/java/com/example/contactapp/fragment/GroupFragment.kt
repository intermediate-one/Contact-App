package com.example.contactapp.fragment


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.activity.DetailActivity
import com.example.contactapp.adaptor.ContactListAdapter
import com.example.contactapp.adaptor.GroupAdapter
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.data.Contacts
import com.example.contactapp.data.Contants
import com.example.contactapp.data.StickyHeaderItemDecoration
import com.example.contactapp.databinding.FragmentGroupBinding


class GroupFragment : Fragment() {


    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = arrayListOf<Contacts>() . apply {
            ContactDatabase.groupData.forEach {groupName->
                add(Contacts.Title(groupName))
                ContactDatabase.getContactPerGroup(groupName).forEach{
                    add(Contacts.ContactList(it.name, it.phoneNumber, it.profileImage, it.favorite))
                }
                remove(Contacts.Title(ContactDatabase.groupData[0]))
            }
        }



        val sortedList = arrayListOf<ContactData>() . apply {
            ContactDatabase.groupData.forEach {groupName->
                ContactDatabase.getContactPerGroup(groupName).forEach{
                    add(it)
                }
            }
        }
        val adapter = GroupAdapter(dataList)


        binding.rvGroup.layoutManager = LinearLayoutManager(context)
        binding.rvGroup.adapter = adapter
        adapter.itemClick = object : GroupAdapter.ItemClick  {
            override fun onClick(view: View, position: Int) {
                //TODO : 행동
            }
        }
        Log.d("GroupFragment","Data List : $dataList")
        binding.rvGroup.addItemDecoration(
            StickyHeaderItemDecoration(
                binding.rvGroup
            ) { itemPosition: Int ->
                dataList[itemPosition] is Contacts.Title

            }
        )
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
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
                adapter.notifyItemChanged(itemNum)
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
//                GroupFragment.userPosition = itemNum
                adapter.notifyItemRangeChanged(ContactListFragment.userPosition,sortedList.size)
            }
        }
        //Detail로 보내고 다시 값 받기
        adapter.itemClick = object : GroupAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
//                sortedList = ContactDatabase.nameSorting()  // 정렬된 Object를 다시 받아오는 작업
                when (val item = dataList[position]) {
                    is Contacts.Title -> Unit
                    is Contacts.ContactList -> {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra(Contants.ITEM_DATA, ContactDatabase.getContact(item.cPhoneNumber))
                        intent.putExtra(Contants.ITEM_INDEX,ContactDatabase.getIndex(item.cPhoneNumber))
                        activityResultLauncher.launch(intent)
                    }
                }
//                val intent = Intent(activity, DetailActivity::class.java)
//                intent.putExtra(Contants.ITEM_DATA, ContactDatabase.getContact(groupList[position-groupIndex[position]]))
//                intent.putExtra(Contants.ITEM_INDEX,ContactDatabase.getIndex(groupList[position-groupIndex[position]]))
//                activityResultLauncher.launch(intent)


            }
        }
        adapter.favChange = object : GroupAdapter.FavoriteChange {
            override fun favChanged(view: View, position: Int) {
                binding.rvGroup.adapter = adapter
                binding.rvGroup.layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val dataList = arrayListOf<Contacts>() . apply {
            ContactDatabase.groupData.forEach {groupName->
                add(Contacts.Title(groupName))
                ContactDatabase.getContactPerGroup(groupName).forEach{
                    add(Contacts.ContactList(it.name,it.phoneNumber, it.profileImage, it.favorite))
                }
                remove(Contacts.Title(ContactDatabase.groupData[0]))
            }
        }
        val adapter = GroupAdapter(dataList)
//        adapter.notifyItemInserted()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var userPosition = 0
    }
}