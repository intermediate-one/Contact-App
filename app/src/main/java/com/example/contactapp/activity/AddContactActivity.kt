package com.example.contactapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.contactapp.R
import com.example.contactapp.data.AddContactErrorMessage
import com.example.contactapp.data.AddContactValidExtension.includeKorean
import com.example.contactapp.data.AddContactValidExtension.includeNumberWithDash
import com.example.contactapp.data.AddContactValidExtension.includeValidAddress
import com.example.contactapp.data.AddContactValidExtension.includeValidEmail
import com.example.contactapp.data.AddContactValidExtension.includeValidMbti
import com.example.contactapp.data.AddContactValidExtension.includeValidMemo
import com.example.contactapp.databinding.ActivityAddContactBinding

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding

    val editTextArray by lazy {
        arrayOf(binding.etAddContactName,
            binding.etAddContactNumber,
            binding.etAddContactAddress,
            binding.etAddContactEmail,
            binding.etAddContactMbti,
            binding.etAddContactMemo)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        setTextChangedListener()
        setOnFocusChangedListener()
        onClickButtonComplete()
        onClickButtonBack()
    }

    private fun setTextChangedListener() {
        editTextArray.forEach { editText ->
            editText.addTextChangedListener {
                editText.setErrorMessage()
                setConfirmButtonEnable()
            }
        }
    }

    private fun setOnFocusChangedListener() {
        editTextArray.forEach { editText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    editText.setErrorMessage()
                    setConfirmButtonEnable()
                }
            }
        }
    }

    private fun EditText.setErrorMessage() {
        when (this) {
            binding.etAddContactName -> error = getMessageValidName()
            binding.etAddContactNumber -> error = getMessageValidNumber()
            binding.etAddContactEmail -> error = getMessageValidEmail()
            binding.etAddContactAddress -> error = getMessageValidAddress()
            binding.etAddContactMbti -> error = getMessageValidMbti()
            binding.etAddContactMemo -> error = getMessageValidMemo()

            else -> Unit
        }
    }

    private fun getMessageValidName(): String? {
        val text = binding.etAddContactName.text.toString()
        val errorCode = when {
            text.isBlank() -> AddContactErrorMessage.EMPTY_NAME
            text.includeKorean() -> null
            else -> AddContactErrorMessage.INVALID_NAME
        }
        return errorCode?.let {
            getString(it.message)
        }
    }

    private fun getMessageValidNumber(): String? {
        val text = binding.etAddContactName.text.toString()
        val errorCode = when {
            text.isBlank() -> AddContactErrorMessage.EMPTY_NUMBER
            text.includeNumberWithDash() -> null
            else -> AddContactErrorMessage.INVALID_NUMBER
        }
        return errorCode?.let {
            getString(it.message)
        }
    }

    private fun getMessageValidEmail(): String? {
        val text = binding.etAddContactEmail.text.toString()
        val errorCode = when {
            text.isBlank() -> null
            text.includeValidEmail() -> null
            else -> AddContactErrorMessage.INVALID_EMAIL
        }
        return errorCode?.let {
            getString(it.message)
        }
    }

    private fun getMessageValidAddress(): String? {
        val text = binding.etAddContactEmail.text.toString()
        val errorCode = when {
            text.isBlank() -> null
            text.includeValidAddress() -> null
            else -> AddContactErrorMessage.INVALID_ADDRESS
            // TODO : 정규식 정상 작동하는지 확인 필요...
        }
        return errorCode?.let {
            getString(it.message)
        }
    }

    private fun getMessageValidMbti(): String? {
        val text = binding.etAddContactMbti.text.toString()
        val errorCode = when {
            text.isBlank() -> null
            text.includeValidMbti() -> null
            else -> AddContactErrorMessage.INVALID_MBTI
            // TODO : 정규식 정상 작동하는지 확인 필요...
        }
        return errorCode?.let {
            getString(it.message)
        }
    }

    private fun getMessageValidMemo(): String? {
        val text = binding.etAddContactMemo.text.toString()
        val errorCode = when {
            text.isBlank() -> null
            text.includeValidMemo() -> null
            else -> AddContactErrorMessage.INVALID_MEMO
            // TODO : 정규식 정상 작동하는지 확인 필요...
        }
        return errorCode?.let {
            getString(it.message)
        }
    }

    private fun onClickButtonBack() {

    }

    private fun onClickButtonComplete() {
        //TODO : 데이터 저장
    }
    private fun setConfirmButtonEnable() {
        binding.btnAddContactComplete.isEnabled = getMessageValidName() == null
                && getMessageValidNumber() == null
                && getMessageValidEmail() == null
                && getMessageValidAddress() == null
                && getMessageValidMbti() == null
                && getMessageValidMemo() == null
    }

}