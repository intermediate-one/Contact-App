package com.example.contactapp.adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.activity.DetailActivity
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.Contants
import com.example.contactapp.databinding.LayoutRvUserBinding
import com.example.contactapp.databinding.LayoutRvUserGridBinding
import com.example.contactapp.fragment.ContactListFragment.Companion.listGrid


import com.example.contactapp.fragment.ContactListFragment.Companion.userPosition

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

    override fun getItemViewType(position: Int):Int {   // Holder나 Hold를 Casting하기 위해 사용
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
                    when (userDataList[position].favorite) {
                        true -> {
                            favorite.setImageResource(R.drawable.star_full)
                            favorite.setOnClickListener {
                                userDataList[position].favorite = false
                                favorite.setImageResource(R.drawable.star_empty)
                            }
                        }
                        false -> {
                            favorite.setImageResource(R.drawable.star_empty)
                            favorite.setOnClickListener {
                                userDataList[position].favorite = true
                                favorite.setImageResource(R.drawable.star_full)
                            }
                        }
                    }
                    userPosition = position     // 현재 선택된 아이템의 Position을 userPosition이라는 companion object에 저장
                }
            }
            -1 -> {
                holdGrid = holder as Hold
                with (holdGrid) {
                    name.text = userDataList[position].name
                    image.setImageResource(userDataList[position].profileImage)
                    when (userDataList[position].favorite) {
                        true -> {
                            favorite.setImageResource(R.drawable.star_full)
                            favorite.setOnClickListener {
                                userDataList[position].favorite = false
                                favorite.setImageResource(R.drawable.star_empty)
                            }
                        }
                        false -> {
                            favorite.setImageResource(R.drawable.star_empty)
                            favorite.setOnClickListener {
                                userDataList[position].favorite = true
                                favorite.setImageResource(R.drawable.star_full)
                            }
                        }
                    }
                    userPosition = position
                }
            }
            else -> throw Exception("Holder를 Casting 할 수 없습니다.")
        }
    }

    override fun getItemCount(): Int = userDataList.size

}