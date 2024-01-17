package com.example.contactapp.data

object EditTextValidExtension {
    /*
    * 한글 이름
    */
    fun String.includeKorean() =
        Regex("^[가-힣]*$").containsMatchIn(this)

    /*
    * 이메일
     */
    fun String.validEmail() =
        Regex("^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$").containsMatchIn(this)
}