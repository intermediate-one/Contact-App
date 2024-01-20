package com.example.contactapp.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.adaptor.GroupAdapter
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.data.Contacts
import com.example.contactapp.data.StickyHeaderItemDecoration
import com.example.contactapp.databinding.FragmentGroupBinding


class GroupFragment : Fragment() {


    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!


    private val dataList = arrayListOf<Contacts>() . apply {
        ContactDatabase.groupData.forEach {groupName->
            add(Contacts.Title(groupName))
            ContactDatabase.getContactPerGroup(groupName).forEach{
                add(Contacts.ContactList(it.name, it.profileImage, it.favorite))
            }
            remove(Contacts.Title(ContactDatabase.groupData[0]))
        }
    }
    private val adapter = GroupAdapter(dataList)


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
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}