package com.example.contactapp.data

object AddContactValidExtension {
    /*
    * 한글 이름
    */
    fun String.includeKorean() =
        Regex("^[가-힣]*$").containsMatchIn(this)

    /*
    * 휴대폰 번호
     */
    fun String.includeNumberWithDash() =
        Regex("^(?:(\\+82|0)(?:-?)(?:2|10)-?)?(\\d{3,4})(?:-?)(\\d{4})\$").containsMatchIn(this)


    /*
    * 이메일
     */
    fun String.includeValidEmail() =
        Regex("^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$").containsMatchIn(this)

    /*
    * 이메일
     */
    fun String.includeValidAddress() =
        Regex("^[A-Za-z0-9가-힣]{0,50}\$").containsMatchIn(this)

    /*
    * mbti
     */
    fun String.includeValidMbti() =
        Regex("^([E|I])([N|S])([T|F])([P|J]){4}\$").containsMatchIn(this)

    /*
    * memo
     */
    fun String.includeValidMemo() =
        Regex("^{0,100}\$").containsMatchIn(this)
    /*
    * overlapping group
     */
    fun String.overlappingGroup(): Boolean {
        var answer = 0
        ContactDatabase.groupData.forEach {
            when {
                (this == it) -> answer = 1
            }
        }
        return when (answer) {
            1 -> false
            else -> true
        }
    }



}