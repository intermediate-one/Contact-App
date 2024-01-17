package com.example.contactapp.activity

import android.content.Context
import android.content.DialogInterface
import android.media.ImageReader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.contactapp.R
import com.example.contactapp.data.AddContactErrorMessage
import com.example.contactapp.data.AddContactValidExtension.includeKorean
import com.example.contactapp.data.AddContactValidExtension.includeNumberWithDash
import com.example.contactapp.data.AddContactValidExtension.includeValidAddress
import com.example.contactapp.data.AddContactValidExtension.includeValidEmail
import com.example.contactapp.data.AddContactValidExtension.includeValidMbti
import com.example.contactapp.data.AddContactValidExtension.includeValidMemo
import com.example.contactapp.data.ContactDatabase.addGroup
import com.example.contactapp.data.ContactDatabase.groupData
import com.example.contactapp.databinding.ActivityAddContactBinding

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding
    private lateinit var newContactName: String
    private lateinit var newContactGroup: String

    val editTextArray by lazy {
        arrayOf(
            binding.etAddContactName,
            binding.etAddContactNumber,
            binding.etAddContactAddress,
            binding.etAddContactEmail,
            binding.etAddContactMbti,
            binding.etAddContactMemo
        )
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

        setGroupProvider()
        addGroupBtn()
    }

    private fun addGroupBtn() {
        binding.ibtnAddContactAddGroup.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.dialog_add_group))
            builder.setMessage(getString(R.string.dialog_new_group))
            builder.setIcon(R.mipmap.ic_launcher)

            val v1 = layoutInflater.inflate(R.layout.dialog_add_group, null)
            builder.setView(v1)

            // p0에 해당 AlertDialog가 들어온다. findViewById를 통해 view를 가져와서 사용
            val listener = DialogInterface.OnClickListener { p0, p1 ->
                val alert = p0 as AlertDialog
                val newGroup: EditText? = alert.findViewById<EditText>(R.id.et_dialog_add_group)

                // 새로운 그룹 이름으로 입력 된 값이 비어 있거나 이미 있는 그룹을 입력하고
                // 확인을 누르면 토스트 메세지를 띄우도록 만들어보았습니다.
                // TODO : 아직 작동이 잘 되는지 확인이 안되었습니당.
                if (p1 == DialogInterface.BUTTON_POSITIVE) {
                    groupData.forEach {
                        if (newGroup?.text.toString().isEmpty()) {
                            shortToastMessage(this,R.string.dialog_empty_text_message)
                        } else if (newGroup?.text.toString() == it) {
                            shortToastMessage(this,R.string.dialog_existing_group_message)
                        } else {
                            newContactGroup = newGroup?.text.toString()
                            addGroup(newContactGroup)
                        }
                    }
                }

                newContactGroup = newGroup?.text.toString()
            }

            builder.setPositiveButton(getString(R.string.dialog_confirm_add_group), listener)
            builder.setNegativeButton(getString(R.string.dialog_cancel_add_group), null)

            builder.show()

        }
    }

    private fun shortToastMessage(context: Context, message: Int) {
        Toast.makeText(context,getString(message),Toast.LENGTH_SHORT).show()
    }

    // 스피너에서 현재 있는 그룹 리스트를 표시해주는 함수.
    private fun setGroupProvider() {
        //object클래스 ContactDatabase.kt에 저장되어 있는 groupData에 있는 값들을 추가해준다.
        binding.spAddContactGroup.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item,
            groupData.toList()
        )
        binding.spAddContactGroup.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    newContactGroup = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) = Unit

            }

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