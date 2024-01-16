package com.example.contactapp.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.data.ContactData
import com.example.contactapp.databinding.LayoutRvUserBinding

class ContactListAdapter(private val userDataList:ArrayList<ContactData>):RecyclerView.Adapter<ContactListAdapter.Holder>() {
    inner class Holder(binding: LayoutRvUserBinding):RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivRvUser
        val name = binding.tvRvUserName
        val favorite = binding.ivRvFavorite
        var favOnOff = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = LayoutRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder) {
            name.text = userDataList[position].name
            image.setImageResource(R.drawable.user_profile_empty)
            favorite.setImageResource(R.drawable.star_empty)
        }
    }

    override fun getItemCount(): Int = 10
}