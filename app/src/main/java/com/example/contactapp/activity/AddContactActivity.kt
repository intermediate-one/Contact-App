package com.example.contactapp.activity

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.example.contactapp.data.AddContactValidExtension.includeValidMemo
import com.example.contactapp.data.AddContactValidExtension.overlappingGroup
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.data.ContactDatabase.groupData
import com.example.contactapp.data.ContactDatabase.mbtiData
import com.example.contactapp.data.ContactDatabase.totalContactData
import com.example.contactapp.databinding.ActivityAddContactBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding
    private val calendar = Calendar.getInstance()

    private var newContactGroup: String = ""
    private var newContactMbti: String = ""
    private var newContactBirthday: String = ""

    private val editTextArray by lazy {
        arrayOf(
            binding.etAddContactName,
            binding.etAddContactNumber,
            binding.etAddContactAddress,
            binding.etAddContactEmail,
            binding.etAddContactMemo
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    override fun onResume() {
        super.onResume()
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initView() {

        setTextChangedListener()
        setOnFocusChangedListener()
        onClickButtonComplete()
        onClickButtonBack()
        onClickDatePicker()

        setGroupProvider()
        setMbtiProvider()
        addGroupBtn()

    }


    private fun onClickDatePicker() {

        binding.btnAddContactDatePicker.setOnClickListener {
            showDatePicker()
        }

    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Create a new Calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                // Set the selected date using the values received from the DatePicker dialog
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                // Format the selected date into a string
                val formattedDate = dateFormat.format(selectedDate.time)
                // Update the TextView to display the selected date with the "Selected Date: " prefix
                binding.tvAddContactSelectedDate.text = formattedDate
                newContactBirthday = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

    private fun addGroupBtn() {
        binding.ibtnAddContactAddGroup.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_group, null)

            val builder = AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_add_group))
                .setMessage(getString(R.string.dialog_new_group))
                .setView(dialogView)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(getString(R.string.dialog_confirm_add_group)) { _, _ ->
                    //startupViewModel.checkAndReset(token, layout.newPassword.text.toString())
                }
                .setNegativeButton(getString(R.string.dialog_cancel_add_group)) { dialog, _ ->
                    dialog.cancel()
                }
                .setCancelable(true)
                .show()
            builder.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false

//            val alertDialog = builder.create()


            val editGroup = dialogView.findViewById<EditText>(R.id.et_dialog_add_group)


            editGroup?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val text = editGroup.text.toString()
                    when {
                        text.isBlank() -> editGroup.error =
                            getString(AddContactErrorMessage.EMPTY_GROUP_NAME.message)

                        text.overlappingGroup() -> {
                            editGroup.error = null
                            builder.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true

                        }

                        else -> editGroup.error = getString(AddContactErrorMessage.OVERLAPPING_GROUP_NAME.message)

                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            builder.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                newContactGroup = editGroup.text.toString()
                ContactDatabase.addGroup(newContactGroup)
                setGroupProvider()
                shortToastMessage(this, R.string.dialog_new_group_added)
                builder.dismiss()
            }


        }


    }

    private fun shortToastMessage(context: Context, message: Int) {
        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
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

    private fun setMbtiProvider() {
        //object클래스 ContactDatabase.kt에 저장되어 있는 groupData에 있는 값들을 추가해준다.
        binding.spAddContactMbti.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item,
            mbtiData.toList()
        )
        binding.spAddContactGroup.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    newContactMbti = parent?.getItemAtPosition(position).toString()
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
        val text = binding.etAddContactNumber.text.toString()
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
        }
        return errorCode?.let {
            getString(it.message)
        }
    }

    private fun onClickButtonBack() {
        binding.ibtnAddContactBack.setOnClickListener {
            finish()
        }
    }

    private fun onClickButtonComplete() {

        binding.btnAddContactComplete.setOnClickListener {
            ContactDatabase.addContact(
                ContactData(
                    binding.etAddContactName.text.toString(),
                    R.drawable.blank_profile_image_square,
                    binding.etAddContactNumber.text.toString(),
                    binding.etAddContactAddress.text.toString(),
                    binding.etAddContactEmail.text.toString(),
                    newContactGroup,
                    newContactBirthday,
                    newContactMbti,
                    binding.etAddContactMemo.text.toString(),
                    null,
                    false
                )
            )
            Log.d("saveToDataBase", "total Contact List $totalContactData")
            finish()
        }


    }

    private fun setConfirmButtonEnable() {
        binding.btnAddContactComplete.isEnabled = getMessageValidName() == null
                && getMessageValidNumber() == null
                && getMessageValidEmail() == null
                && getMessageValidAddress() == null
                && getMessageValidMemo() == null
    }

}