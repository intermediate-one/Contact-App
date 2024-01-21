package com.example.contactapp.data

import com.example.contactapp.R

object ContactDatabase {
    // 내 정보
    var myContact = new()
//    var myContact = ContactData("박보영", R.drawable.boyoung, "01099887766", "집주소","boyoung@gmail.com","친구", "2003/03/03", "", "할말 없.",null, false)

    // Dummy Data
    private val contact1 = ContactData("안보현", R.drawable.bohyun, "01011111111", "","bohyun1111@gmail.com", "친구","2000/01/01", "ENFP", "아무말1", null, false)
    private val contact2 = ContactData("박보영", R.drawable.boyoung, "010222222222","", "boyoung22@gmail.com", "친구","1999/01/02", "ISTP", "아무말2", null, true)
    private val contact3 = ContactData("톰", R.drawable.tom, "01033333333", "","tom3333@gmail.com", "친구","1998/02/02","INTP", "아무말3", null, false)
    private val contact4 = ContactData("박은빈", R.drawable.eunbin, "01044444444", "","", "친구","1998/02/05","ESFJ", "아무말4",null , true)
    private val contact5 = ContactData("김수현", R.drawable.suhyun, "01055555555", "","suhyun45@kakao.com","친구","2002/02/07", "", "", null, true)
    private val contact6 = ContactData("젠데야", R.drawable.zendaya, "01066666666", "","zenday.thecool@naver.com", "친구", "2005/10/10","ISFJ", "아무말5", null, false)
    private val contact7 = ContactData("엄마", R.drawable.blank_profile_image_square, "01077777777", "","mum@gmail.com", "가족", "2001/01/01","INFJ", "아무말6", null, true)
    private val contact8 = ContactData("아빠", R.drawable.blank_profile_image_square, "01088888888", "","dad@gmail.com", "가족", "1990/12/12","ENFJ", "아무말7", null, true)
    private val contact9 = ContactData("동생", R.drawable.blank_profile_image_square, "01099999999", "","", "가족", "1995/10/23","ENTP", "아무말8", null, true)
    private val contact10 = ContactData("형", R.drawable.blank_profile_image_square, "01000000000", "","", "가족", "2001/01/03","INFP", "아무말9", null, true)
    private val contact11 = ContactData("직장상사1", R.drawable.blank_profile_image_square, "01012345678", "","workplace@companyName.ac.kr", "회사", "","", "아무말10", null, false)
    private val contact12 = ContactData("김세정", R.drawable.sejeong, "01011112222", "","workplace@companyName.ac.kr", "회사", "","", "아무말10", null, false)
    private val contact13 = ContactData("김연아", R.drawable.yuna, "01011113333", "","workplace@companyName.ac.kr", "회사", "","", "아무말10", null, false)
    private val contact14 = ContactData("이승기", R.drawable.seunggi, "01011114444", "","workplace@companyName.ac.kr", "회사", "","", "아무말10", null, false)
    private val contact15 = ContactData("마블리", R.drawable.mably, "01011115555", "","workplace@companyName.ac.kr", "회사", "","", "아무말10", null, false)
    private val contact16 = ContactData("이효리", R.drawable.hyori, "01011116666", "","workplace@companyName.ac.kr", "회사", "","", "아무말10", null, false)


    var totalContactData: ArrayList<ContactData> = arrayListOf(contact1, contact2, contact3, contact4, contact5, contact6, contact7, contact8, contact9, contact10, contact11, contact12, contact13, contact14, contact15, contact16)

    var mbtiData: List<String> = listOf("선택 안함", "ESTJ", "ESTP", "ESFJ", "ESFP", "ENTJ", "ENTP", "ENFJ", "ENFP","ISTJ", "ISTP", "ISFJ", "ISFP", "INTJ", "INTP", "INFJ", "INFP")

    // 디폴트 그룹 종류 가족 | 친구 | 회사
    var groupData: MutableList<String> = mutableListOf("선택 안함","가족","친구","회사")

    // 그룹 종류를 추가하는 함수
    fun addGroup(group: String) {
        groupData.add(group)
    }

    fun getGroupIndex(group: String): Int {
        return groupData.indexOfFirst {
            it == group
        }
    }

    fun getMbtiIndex(mbti: String) : Int {
        return mbtiData.indexOfFirst {
            it == mbti
        }
    }


    // 그룹 종류를 삭제하는 함수
    fun deleteGroup(group: String) {
        groupData.remove(group)
    }

    // 그룹에 속해 있는 연락처 정보 가져오는 함수
    fun getContactPerGroup(group: String): MutableList<ContactData> {
        var groupData : MutableList<ContactData> = mutableListOf()
        totalContactData.forEach {
            if (it.group == group) groupData.add(it)
        }
        return groupData
    }




    // 새로운 연락처를 저장하는 함수
    fun addContact(contact: ContactData) {
        totalContactData.add(contact)
    }

    // 전체 연락처 정보를 가져오는 함수
    fun getTotalContact(): ArrayList<ContactData> {
        return totalContactData
    }



    // 전화번호를 가지고 있는 해당 연락처 정보 가져오는 함수
    fun getContact(phoneNumber: String): ContactData? {
        return totalContactData.find { it.phoneNumber == phoneNumber }
    }

    /** 인덱스에 해당하는 연락처를 가져오는 함수 */
    fun getContact(index: Int) = totalContactData[index]

    /** 전화번호로 해당 연락처의 인덱스를 가져오는 함수 (없으면 -1) */
    fun getIndex(phoneNumber: String) =
        totalContactData.indexOfFirst { it.phoneNumber == phoneNumber }

    /** 인덱스에 해당하는 연락처를 넘겨준 데이터로 변경 */
    fun editContactData(index: Int, contact: ContactData) {
        totalContactData[index] = contact
    }

    // 연락처 정보를 수정하는 함수
    fun editContactData(contact: ContactData) {
        getContact(contact.phoneNumber)?.let {
            it.name = contact.name
            it.profileImage = contact.profileImage
            it.email = contact.email
            it.phoneNumber = contact.phoneNumber
            it.favorite = contact.favorite
        }
    }

    fun editFavoriteFromNumber(phoneNumber: String) {
        getContact(phoneNumber)?. let {
            when (it.favorite) {
                true -> {
                    it.favorite = false
                }
                false -> it.favorite = true
            }
        }

    }

    // 빈 데이터 생성하는 함수
    fun new() = ContactData("이름", R.drawable.blank_profile_image_square, "01000000000", "", "", "", "", "", "", null, false)

    // 저장된 데이터를 이름순으로 정렬하는 함수
    fun nameSorting() = totalContactData.sortedWith(compareBy({!it.favorite},{it.name}))
}