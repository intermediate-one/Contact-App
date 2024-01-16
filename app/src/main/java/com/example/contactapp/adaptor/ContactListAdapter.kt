package com.example.contactapp.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.data.ContactData
import com.example.contactapp.databinding.LayoutRvUserBinding
import com.example.contactapp.fragment.ContactListFragment.Companion.userId
import androidx.recyclerview.widget.DiffUtil

class ContactListAdapter(private val userDataList:ArrayList<ContactData>):RecyclerView.Adapter<ContactListAdapter.Holder>() {
    inner class Holder(binding: LayoutRvUserBinding):RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivRvUser
        val name = binding.tvRvUserName
        val favorite = binding.ivRvFavorite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = LayoutRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder) {
            name.text = userDataList[position].name
            image.setImageResource(R.drawable.user_profile_empty)
            favorite.setOnClickListener {
                when (userDataList[position].favorite) {
                    true -> {
                        userDataList[position].favorite = false
                        favorite.setImageResource(R.drawable.star_empty)
                    }

                    false -> {
                        userDataList[position].favorite = true
                        favorite.setImageResource(R.drawable.star_full)
                    }
                }
                userId = position
                ContactListAdapter(userDataList).notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int = userDataList.size
}

//class ContactListAdapter : ListAdapter<ContactData, ContactListAdapter.ViewHolder>(
//    object : DiffUtil.ItemCallback<ContactData>() {
//        override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
//            // item의 구성요소가 같은지
//            return oldItem.phoneNumber == newItem.phoneNumber
//        }
//
//        override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
//            // item의 모든 필드가 같은지
//            return oldItem == newItem
//        }
//    }) {
//    inner class ViewHolder(binding: LayoutRvUserBinding): RecyclerView.ViewHolder(binding.root) {
//        // ViewHolder 구현
//        // fun bind(video: Video) { /* 아이템 바인딩 */ }
//        val image = binding.ivRvUser
//        val name = binding.tvRvUserName
//        val favorite = binding.ivRvFavorite
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListAdapter.ViewHolder {
//        // ViewHolder 생성 로직
//        // 예시: return VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.video_view_holder, parent, false))
//        val binding = LayoutRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        // ViewHolder 바인딩 로직
//        val userItem = getItem(position)
//    }
//}