package com.example.contactapp.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.example.contactapp.data.DialogErrorMessage
import com.example.contactapp.R
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.EditTextValidExtension.includeKorean
import com.example.contactapp.data.EditTextValidExtension.validEmail
import com.example.contactapp.databinding.FragmentAddContactBinding


class AddContactFragment : DialogFragment() {
    private lateinit var binding: FragmentAddContactBinding

    private val editTextArray by lazy {
        // TODO : notification은 아직 적용 아니함..
        with(binding) {
            arrayOf(etAddContactName, etAddContactNumber, etAddContactEmail)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddContactBinding.inflate(layoutInflater)
        val view = binding.root
        dialog?.setContentView(view)

        initView()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_contact, container, false)


    }

    private fun initView() {
        // 레이아웃 배경을 투명하게 해줌 .. (필수 아님)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // 다이얼로그 바깥 부분 클릭 시 뒤로가기
        dialog?.setCancelable(true)
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        setTextChangedListener()
        setOnFocusChangedListener()
        onClickBtnBack()
        onClickBtnAdd()
    }

    private fun setTextChangedListener() {
        editTextArray.forEach { editText ->
            editText.addTextChangedListener {
                editText.setErrorMessage()
                setAddButtonEnable()
            }
        }
    }
    private fun setOnFocusChangedListener() {
        editTextArray.forEach {
            it.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    it.setErrorMessage()
                    setAddButtonEnable()
                }
            }
        }
    }

    private fun EditText.setErrorMessage() {
        when (this) {
            binding.etAddContactName -> error = getMessageValidName()
            binding.etAddContactNumber -> error = getMessageValidNumber()
            binding.etAddContactEmail -> error = getMessageValidEmail()
            // TODO : 알림 아직 추가 안함
//            binding.etAddContactNotification
            else -> Unit
        }
    }

    private fun getMessageValidName(): String? {
        val text = binding.etAddContactName.text.toString()
        val errorCode = when {
            text.isBlank() -> DialogErrorMessage.EMPTY_NAME
            text.includeKorean() -> null
            else -> DialogErrorMessage.INVALID_NAME
        }
        return errorCode?.let {
            getString(it.message)
        }
    }

    private fun getMessageValidNumber(): String? {
        val text = binding.etAddContactNumber.text.toString()
        val errorCode = when {
            text.isBlank() -> DialogErrorMessage.EMPTY_NUMBER
            text.includeKorean() -> null
            else -> DialogErrorMessage.INVALID_NUMBER
        }
        return errorCode?.let {
            getString(it.message)
        }
    }

    private fun getMessageValidEmail(): String? {
        val text = binding.etAddContactEmail.text.toString()
        val errorCode = when {
            text.validEmail() -> null
            else -> DialogErrorMessage.INVALID_EMAIL
        }
        return errorCode?.let {
            getString(it.message)
        }
    }

    private fun setAddButtonEnable() {
        binding.btnAddContactDone.isEnabled =
            getMessageValidName() == null && getMessageValidEmail() == null && getMessageValidNumber() == null

    }

    private fun onClickBtnAdd() {
        binding.btnAddContactDone.setOnClickListener {
            // 데이터 전달
        }
    }

    private fun onClickBtnBack() {
        binding.btnAddContactCancel.setOnClickListener {


        }
    }


}