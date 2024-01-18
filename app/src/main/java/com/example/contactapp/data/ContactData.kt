package com.example.contactapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactData (
    var name: String,
    var profileImage: Int,
    var phoneNumber: String,
    var address: String?,
    var email: String?,
    var group: String?,
    var birthday: Int?,
    var mbti: String?,
    var memo: String?,
    var notification: Int?,
    var favorite: Boolean
):Parcelable