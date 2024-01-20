package com.example.contactapp.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.data.Contacts
import com.example.contactapp.databinding.HeaderGroupBinding
import com.example.contactapp.databinding.LayoutRvUserBinding


class GroupAdapter(private val contacts: ArrayList<Contacts>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_TITLE = 1
        private const val VIEW_TYPE_ITEM = 2
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    interface FavoriteChange {
        fun favChanged(view: View, position: Int)
    }

    var favChange: FavoriteChange? = null
    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            1 -> {
                val binding =
                    HeaderGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TitleViewHolder(binding)
            }

            2 -> {
                val binding =
                    LayoutRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemViewHolder(binding)
            }

            else -> {
                val binding =
                    LayoutRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemViewHolder(binding)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when (val item = contacts[position]) {
            is Contacts.Title -> {
                (holder as TitleViewHolder).title.text = item.ctitle
            }

            is Contacts.ContactList -> {
                (holder as ItemViewHolder).name.text = item.cname
                when (item.cFavorite) {
                    true -> holder.favorite.setImageResource(R.drawable.star_full)
                    false -> holder.favorite.setImageResource(R.drawable.star_empty)
                }
                holder.image.setImageResource(item.cImage)

                holder.itemView.setOnClickListener {
                    itemClick?.onClick(it, position)
                }

                holder.favorite.setOnClickListener {
                    ContactDatabase.editFavoriteFromNumber(item.cPhoneNumber)
                    item.cFavorite = ContactDatabase.getContact(item.cPhoneNumber)!!.favorite
                    holder.favorite.setImageResource(R.drawable.star_empty)
                    favChange?.favChanged(it,position)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return when (contacts[position]) {
            is Contacts.Title -> VIEW_TYPE_TITLE
            is Contacts.ContactList -> VIEW_TYPE_ITEM
        }
    }


    inner class TitleViewHolder(binding: HeaderGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvHeader
    }

    inner class ItemViewHolder(binding: LayoutRvUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivRvUser
        val name = binding.tvRvUserName
        val favorite = binding.ivRvFavorite
    }


}