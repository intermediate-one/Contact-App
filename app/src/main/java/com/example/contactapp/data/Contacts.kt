package com.example.contactapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Contacts {
    data class Title(var ctitle: String) : Contacts()
    @Parcelize
    data class ContactList(
        var cname: String,
        var cPhoneNumber: String,
        var cImage: Int,
        var cFavorite: Boolean
    ) : Contacts() ,Parcelable
}