package com.example.contactapp.data

enum class ContactUserFirstName {
    FIRST_NAME
}

fun getFirstName(s: String):Char {
    return when(s[0]) {
        in '가' until '나' -> 'ㄱ'
        in '나' until '다' -> 'ㄴ'
        in '다' until '라' -> 'ㄷ'
        in '라' until '마' -> 'ㄹ'
        in '마' until '바' -> 'ㅁ'
        in '바' until '사' -> 'ㅂ'
        in '사' until '아' -> 'ㅅ'
        in '아' until '자' -> 'ㅇ'
        in '자' until '차' -> 'ㅈ'
        in '차' until '카' -> 'ㅊ'
        in '카' until '타' -> 'ㅋ'
        in '타' until '파' -> 'ㅌ'
        in '파' until '하' -> 'ㅍ'
        in '하' .. '힣' -> 'ㅎ'
        else -> throw Exception("가 ~ 힣 사이의 문자가 아닙니다.")
    }
}