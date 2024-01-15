package com.example.contactapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.databinding.LayoutFragmentContactListBinding
import com.example.contactapp.databinding.LayoutRvUserBinding

class ContactListAdapter:RecyclerView.Adapter<ContactListAdapter.Holder>() {
    inner class Holder(binding: LayoutRvUserBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListAdapter.Holder {
        val binding = LayoutRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: ContactListAdapter.Holder, position: Int) {

    }

    override fun getItemCount(): Int = 0
}