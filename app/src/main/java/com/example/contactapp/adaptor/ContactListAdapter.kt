package com.example.contactapp.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.ContactDatabase.nameSorting
import com.example.contactapp.databinding.LayoutRvUserBinding
import com.example.contactapp.databinding.LayoutRvUserGridBinding
import com.example.contactapp.fragment.ContactListFragment.Companion.listGrid

class ContactListAdapter(private var userDataList:List<ContactData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var holdList:Holder
    private lateinit var holdGrid:Hold

    companion object {
        private const val LINEAR_LAYOUT = 1
        private const val GRID_LAYOUT = -1
    }

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
            LINEAR_LAYOUT -> listGrid = LINEAR_LAYOUT
            GRID_LAYOUT -> listGrid = GRID_LAYOUT
        }
        return listGrid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        return when(viewType) {
            LINEAR_LAYOUT -> Holder(LayoutRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            GRID_LAYOUT -> Hold(LayoutRvUserGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw Exception("Layout Adapter 연결에 실패함")
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
                    userDataList = nameSorting()
                    name.text = userDataList[position].name
                    userDataList[position].profileImage?.let { image.setImageResource(it) }
                    userDataList[position].profilePath?.let { image.setImageURI(it.toUri()) }
                    when(userDataList[position].favorite) {
                        true -> favorite.setImageResource(R.drawable.star_full)
                        false -> favorite.setImageResource(R.drawable.star_empty)
                    }
                }
            }
            -1 -> {
                holdGrid = holder as Hold
                with (holdGrid) {
                    userDataList = nameSorting()
                    name.text = userDataList[position].name
                    userDataList[position].profileImage?.let { image.setImageResource(it) }
                    userDataList[position].profilePath?.let { image.setImageURI(it.toUri()) }
                    when(userDataList[position].favorite) {
                        true -> favorite.setImageResource(R.drawable.star_full)
                        false -> favorite.setImageResource(R.drawable.star_empty)
                    }
                }
            }
            else -> throw Exception("Holder를 Casting 할 수 없습니다.")
        }
    }

    override fun getItemCount(): Int = userDataList.size
}