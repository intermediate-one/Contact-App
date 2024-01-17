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
import androidx.viewbinding.ViewBinding
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.databinding.ActivityContactBinding
import com.example.contactapp.databinding.LayoutRvUserGridBinding
import com.example.contactapp.fragment.ContactListFragment.Companion.listGrid
import java.lang.Exception


private const val LINEAR_LAYOUT = 0
private const val GRID_LAYOUT = 1

class ContactListAdapter(private val userDataList:ArrayList<ContactData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class Holder(binding: LayoutRvUserBinding):RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivRvUser
        val name = binding.tvRvUserName
        val favorite = binding.ivRvFavorite
    }

    inner class Hold(binding: LayoutRvUserGridBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivRvUserGrid
        val name = binding.tvUserNameGrid
    }

    override fun getItemViewType(position: Int) = listGrid

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        when(listGrid) {
            0 -> return Holder(LayoutRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            1 -> return Hold(LayoutRvUserGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw Exception("Adapter 연결에 실패함")
        }
    }

    // 홀더부터 다시
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var hold = holder as Holder
            when(listGrid) {
            0 -> holder as Holder
            1 -> holder as Hold
            else -> throw Exception("Holder를 Casting 할 수 없습니다.")
        }

        with(hold) {
            hold.name.text = userDataList[position].name
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
        with (hold) {
            name.text = userDataList[position].name
            image.setImageResource(R.drawable.user_profile_empty)
//            favorite.setOnClickListener {
//                when (userDataList[position].favorite) {
//                    true -> {
//                        userDataList[position].favorite = false
//                        favorite.setImageResource(R.drawable.star_empty)
//                    }
//
//                    false -> {
//                        userDataList[position].favorite = true
//                        favorite.setImageResource(R.drawable.star_full)
//                    }
//                }
//                userId = position
//                ContactListAdapter(userDataList).notifyItemChanged(position)
//            }
        }
    }

    override fun getItemCount(): Int = userDataList.size
}

//class ContactListAdapter : ListAdapter<ContactData, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContactData>() {
//            override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
//                // item의 구성요소가 같은지
//                return oldItem.phoneNumber == newItem.phoneNumber
//            }
//
//            override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
//                // item의 모든 필드가 같은지
//                return oldItem == newItem
//            }
//        }
//
//        private const val LINEAR_LAYOUT = 0
//        private const val GRID_LAYOUT = 1
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when(listGrid) {
//            0 -> GRID_LAYOUT
//            else -> LINEAR_LAYOUT
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        // ViewHolder 생성 로직
//        // 예시: return VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.video_view_holder, parent, false))
//        return when(viewType){
//            LINEAR_LAYOUT -> ViewHolderLinear(LayoutInflater.from(parent.context).inflate(R.layout.layout_rv_user, parent, false))
//            GRID_LAYOUT -> ViewHolderGrid(LayoutInflater.from(parent.context).inflate(R.layout.layout_rv_user_grid, parent, false))
//            else -> throw Exception("ViewType Error")
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        // ViewHolder 바인딩 로직
//        val userItem = getItem(position)
//    }
//
//    inner class ViewHolderLinear(v:View): RecyclerView.ViewHolder(v) {
//        // ViewHolder 구현
//        // fun bind(video: Video) { /* 아이템 바인딩 */ }
//        fun bind(item: ContactData) {
//
//        }
//        val image = binding.ivRvUser
//        val name = binding.tvRvUserName
//        val favorite = binding.ivRvFavorite
//    }
//
//    inner class ViewHolderGrid(v:View):RecyclerView.ViewHolder(v) {
//
//    }
//}

