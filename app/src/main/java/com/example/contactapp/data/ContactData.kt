package com.example.contactapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactData (
    var name: String,
    var profileImage: Int,
    var phoneNumber: String,
    var email: String,
    var isLike : Boolean
):Parcelable