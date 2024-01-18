package com.example.contactapp.data

sealed class RecyclerViewTypes {
    data class ViewHeader(val str:String): RecyclerViewTypes()
    data class ViewContent(val userDataList: ArrayList<ContactData>):RecyclerViewTypes()
}