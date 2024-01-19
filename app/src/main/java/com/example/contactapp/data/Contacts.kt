package com.example.contactapp.data

sealed class Contacts {
    data class Title(val ctitle: String) : Contacts()
    data class ContactList (val cname: String, val cImage: Int, val cFavorite: Boolean) : Contacts()
}