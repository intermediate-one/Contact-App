package com.example.contactapp.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.activity.ContactActivity
import com.example.contactapp.adaptor.GroupAdapter
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.data.Contacts
import com.example.contactapp.databinding.FragmentGroupBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupFragment : Fragment() {
    private var recyclerView: RecyclerView? = null

    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!
    private val mainPage by lazy { context as ContactActivity }


    private val dataList = arrayListOf<Contacts>() . apply {
        ContactDatabase.groupData.forEach {groupName->
            add(Contacts.Title(groupName))
            ContactDatabase.getContactPerGroup(groupName).forEach{
                add(Contacts.ContactList(it.name, it.profileImage, it.favorite))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = GroupAdapter(dataList)



    }

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



    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}