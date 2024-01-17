package com.example.contactapp.data

import androidx.annotation.StringRes
import com.example.contactapp.R

enum class AddContactErrorMessage(@StringRes val message: Int) {
    EMPTY_NAME(R.string.empty_name),
    EMPTY_NUMBER(R.string.empty_number),

    INVALID_NAME(R.string.invalid_name),
    INVALID_NUMBER(R.string.invalid_number),
    INVALID_EMAIL(R.string.invalid_email),
    INVALID_ADDRESS(R.string.invalid_address),
    INVALID_MBTI(R.string.invalid_mbti),
    INVALID_MEMO(R.string.invalid_memo),

}