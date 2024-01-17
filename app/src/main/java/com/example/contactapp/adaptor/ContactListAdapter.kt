package com.example.contactapp.adaptor

import android.view.LayoutInflater


import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.data.ContactData
import com.example.contactapp.databinding.LayoutRvUserBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.databinding.ActivityContactBinding
import com.example.contactapp.databinding.FragmentContactListBinding
import com.example.contactapp.databinding.LayoutRvUserGridBinding
import com.example.contactapp.fragment.ContactListFragment.Companion.listGrid
<<<<<<< HEAD
import com.example.contactapp.fragment.DetailFragment
=======
import com.example.contactapp.fragment.ContactListFragment.Companion.userPosition
>>>>>>> b4f8f55c8c05f528e2559db8054313923130efda
import java.lang.Exception


private const val LINEAR_LAYOUT = 1
private const val GRID_LAYOUT = -1

class ContactListAdapter(private val userDataList:ArrayList<ContactData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var holdList:Holder
    private lateinit var holdGrid:Hold

    interface ItemClick {
        fun onClick(view : View, position:Int)
    }
    var itemClick : ItemClick? = null

    inner class Holder(binding: LayoutRvUserBinding):RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivRvUser
        val name = binding.tvRvUserName
        val favorite = binding.ivRvFavorite
    }

    inner class Hold(binding: LayoutRvUserGridBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivRvUserGrid
        val name = binding.tvUserNameGrid
        val favorite = binding.ivRvFavoriteGrid
    }

    override fun getItemViewType(position: Int):Int {   // 필요한지?
        when(listGrid) {
            LINEAR_LAYOUT -> {
                listGrid = LINEAR_LAYOUT
                return LINEAR_LAYOUT
            }
            GRID_LAYOUT -> {
                listGrid = GRID_LAYOUT
                return GRID_LAYOUT
            }
        }
        return listGrid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        return when(listGrid) {
            1 -> Holder(LayoutRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            -1 -> Hold(LayoutRvUserGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw Exception("Adapter 연결에 실패함")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it,position)
        }


        when(listGrid) {
            1 -> {
                holdList = holder as Holder
                with(holdList) {
                    name.text = userDataList[position].name
                    image.setImageResource(userDataList[position].profileImage)
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
                        userPosition = position
                    }
                }
            }
            -1 -> {
                holdGrid = holder as Hold
                with (holdGrid) {
                    name.text = userDataList[position].name
                    image.setImageResource(userDataList[position].profileImage)
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
                        userPosition = position
                    }
                }
            }
            else -> throw Exception("Holder를 Casting 할 수 없습니다.")
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

