package com.example.contactapp.data

sealed class RecyclerViewTypes {
    data class ViewList(val userDataList: ArrayList<ContactData>):RecyclerViewTypes()
    data class ViewGrid(val userDataList: ArrayList<ContactData>):RecyclerViewTypes()
}