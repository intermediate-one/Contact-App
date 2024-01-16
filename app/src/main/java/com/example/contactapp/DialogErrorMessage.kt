package com.example.contactapp

import androidx.annotation.StringRes

enum class DialogErrorMessage(@StringRes val message: Int) {
    EMPTY_NAME(R.string.empty_name),
    EMPTY_NUMBER(R.string.empty_number),

    INVALID_NAME(R.string.invalid_name),
    INVALID_NUMBER(R.string.invalid_number),
    INVALID_EMAIL(R.string.invalid_email),

}