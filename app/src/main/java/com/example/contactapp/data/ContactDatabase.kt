package com.example.contactapp.data

import com.example.contactapp.R

object ContactDatabase {

    // Dummy Data
    private val contact1 = ContactData("김은이", R.drawable.star_empty, "01012345678", "euny1234@gmail.com")
    private val contact2 = ContactData("김은이", R.drawable.call_logo, "010129292929", "euny12314@gmail.com")
    private val contact3 = ContactData("김은이", R.drawable.star_empty, "01012345448", "euny123411@gmail.com")
    private val contact4 = ContactData("김은이", R.drawable.star_empty, "01013883838", "euny1234111@gmail.com")
    private val contact5 = ContactData("김은이", R.drawable.star_empty, "01012345678", "euny12341111@gmail.com")
    private val contact6 = ContactData("김은이", R.drawable.star_empty, "01012345678", "euny113413234@gmail.com")
    private val contact7 = ContactData("김은이", R.drawable.star_empty, "01012345678", "euny1234111111@gmail.com")
    private val contact8 = ContactData("김은이", R.drawable.star_empty, "01012345678", "euny12312314@gmail.com")
    private val contact9 = ContactData("김은이", R.drawable.star_empty, "01012345678", "euny1234134@gmail.com")
    private val contact10 = ContactData("김은이", R.drawable.star_empty, "01012345678", "euny1231341344@gmail.com")


    var totalContactData: ArrayList<ContactData> = arrayListOf(contact1, contact2, contact3, contact4, contact5, contact6, contact7, contact8, contact9, contact10)

    // 회원가입한 사용자를 저장하는 함수
    fun addContact(contact: ContactData) {
        totalContactData.add(contact)
    }

    // 모든 사용자 정보를 가져오는 함수
    fun getTotalContact(): ArrayList<ContactData> {
        return totalContactData
    }

    // 아이디를 가지고 해당 사용자 정보 가져오는 함수
    fun getContact(phoneNumber: String): ContactData? {
        return totalContactData.find { it.phoneNumber == phoneNumber }
    }

    // 사용자 정보를 수정하는 함수
    fun editContactData(contact: ContactData) {
        getContact(contact.phoneNumber)?.let {
            it.name = contact.name
            it.profileImage = contact.profileImage
            it.email = contact.email
            it.phoneNumber = contact.phoneNumber
        }
    }
}