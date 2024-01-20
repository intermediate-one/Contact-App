package com.example.contactapp.activity

import RealPathUtil
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import com.example.contactapp.R
import com.example.contactapp.data.ActType
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
import com.example.contactapp.data.Contants
import com.example.contactapp.databinding.ActivityAddContactBinding
import com.example.contactapp.notification.MyManagers
import com.example.contactapp.notification.MyReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding
    private val calendar = Calendar.getInstance()

    private var newContactGroup: String = ""
    private var newContactMbti: String = ""
    private var newContactBirthday: String = ""

    private var profileResId: Int? = null
    private var profilePath: String? = null

    private lateinit var actType: ActType
    private var data: ContactData? = null
//    private val data: ContactData? by lazy {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            intent?.getParcelableExtra(Contants.ITEM_DATA, ContactData::class.java)
//        } else {
//            intent?.getParcelableExtra<ContactData>(Contants.ITEM_DATA)
//        }
//    }

    private val editTextArray by lazy {
        arrayOf(
            binding.etAddContactName,
            binding.etAddContactNumber,
            binding.etAddContactAddress,
            binding.etAddContactEmail,
            binding.etAddContactMemo
        )
    }

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) openGallery()
            else Log.e("myLogTag", "RequestPermission not granted")
        }

    // 가져온 사진 이미지뷰에 세팅
    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) result.data?.data?.let {
                profileResId = null
                profilePath = RealPathUtil.getRealPathFromURI_API19(this, it)
                binding.ivAddContactPerson.setImageURI(it)
            } else Log.e("myLogTag", "result.resultCode != RESULT_OK")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actType = intent.getSerializableExtra(Contants.ActType) as ActType? ?: run {
            Log.e("myTag", "ActType null")
            ActType.ADD_CONTACT
        }
        data =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                intent?.getParcelableExtra(Contants.ITEM_DATA, ContactData::class.java)
            else
                intent?.getParcelableExtra<ContactData>(Contants.ITEM_DATA)

        initView()
        //ddd
        binding.btnAddContactComplete.isEnabled = true
    }

    private fun initView() {

        if (actType == ActType.EDIT_DETAIL || actType == ActType.EDIT_MY_PAGE) setDataToViews()

        setTextChangedListener()
        setOnFocusChangedListener()
        onClickButtonComplete()
        onClickButtonBack()
        onClickDatePicker()
        onClickProfileImage()

        setGroupProvider()
        setMbtiProvider()
        addGroupBtn()

    }

    private fun onClickProfileImage() {
        binding.ivAddContactPerson.setOnClickListener {
            val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
            // TODO: 한번 권한 거부하면 다시 요청이 불가능한 문제. 버튼이 무반응이 된다.
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) requestPermissionLauncher.launch(permission)
            else openGallery()
        }
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

                        else -> editGroup.error =
                            getString(AddContactErrorMessage.OVERLAPPING_GROUP_NAME.message)

                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })


        }

        // p0에 해당 AlertDialog가 들어온다. findViewById를 통해 view를 가져와서 사용
        val listener = DialogInterface.OnClickListener { p0, _ ->
            val alert = p0 as AlertDialog
            val newGroup: EditText? = alert.findViewById(R.id.et_dialog_add_group)

        }


    }

//    private fun shortToastMessage(context: Context, message: Int) {
//        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
//    }

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
        when (actType) {
            ActType.ADD_CONTACT -> {
                Log.d("myTag", "onClickButtonComplete ActType.ADD_CONTACT")
                binding.btnAddContactComplete.setOnClickListener {
                    ContactDatabase.addContact(makeData())
                    Log.d("saveToDataBase", "total Contact List $totalContactData")
                    intent.putExtra(Contants.ITEM_DATA, makeData())
                    setResult(RESULT_OK)
                    finish()
                }
            }

            ActType.EDIT_DETAIL -> {
                Log.d("myTag", "onClickButtonComplete ActType.EDIT_DETAIL")
                binding.btnAddContactComplete.setOnClickListener {
                    if (data == null) {
                        Log.e("myTag", "data == null")
                        return@setOnClickListener
                    }
                    val index = ContactDatabase.getIndex(data!!.phoneNumber)
                    if (index == -1) {
                        Log.e("myTag", "index == -1")
                        return@setOnClickListener
                    }
                    ContactDatabase.editContactData(index, makeData())
                    setNotifications()
                    intent.putExtra(Contants.ITEM_INDEX, index)
                    intent.putExtra(Contants.ITEM_DATA, makeData())
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }

            ActType.EDIT_MY_PAGE -> {
                Log.d("myTag", "onClickButtonComplete ActType.EDIT_MY_PAGE")
                binding.btnAddContactComplete.setOnClickListener {
                    ContactDatabase.myContact = makeData()
                    intent.putExtra(Contants.ITEM_DATA, makeData())
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }

    private fun setNotifications() {
        if (binding.cbAddContactBirthday.isChecked) {
            notification(makeData())  //ddd
            // TODO: 날짜 지정해서 알람 만드는거
            Toast.makeText(this, "생일 알림이 설정되었습니다", Toast.LENGTH_SHORT).show()
        }
        if (binding.cbAddContact5s.isChecked) {  // 이거 안돼...?
            setAlarm(makeData(), 5)
            Toast.makeText(this, "5초 뒤 알림이 설정되었습니다", Toast.LENGTH_SHORT).show()
        }
        if (binding.cbAddContact5m.isChecked) {
            setAlarm(makeData(), 10)  //ddd
//            setAlarm(makeData(), 300)
            Toast.makeText(this, "5분 뒤 알림이 설정되었습니다", Toast.LENGTH_SHORT).show()
        }
        if (binding.cbAddContact10m.isChecked) {
            setAlarm(makeData(), 20)  //ddd
//            setAlarm(makeData(), 600)
            Toast.makeText(this, "10분 뒤 알림이 설정되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setConfirmButtonEnable() {
        binding.btnAddContactComplete.isEnabled = getMessageValidName() == null
                && getMessageValidNumber() == null
                && getMessageValidEmail() == null
                && getMessageValidAddress() == null
                && getMessageValidMemo() == null
        //ddd
        binding.btnAddContactComplete.isEnabled = true
    }

    private fun makeData() = ContactData(
        binding.etAddContactName.text.toString(),
        profileResId,
        binding.etAddContactNumber.text.toString(),
        binding.etAddContactAddress.text.toString(),
        binding.etAddContactEmail.text.toString(),
        newContactGroup,
        newContactBirthday,
        newContactMbti,
        binding.etAddContactMemo.text.toString(),
        null,
        false,
        profilePath
    )

    private fun setData(data: ContactData) {
        binding.apply {
            profileResId = data.profileImage
            profilePath = data.profilePath
            data.profileImage?.let { ivAddContactPerson.setImageResource(it) }
            data.profilePath?.let { ivAddContactPerson.setImageURI(it.toUri()) }
            etAddContactName.setText(data.name)
            etAddContactNumber.setText(data.phoneNumber)
            etAddContactAddress.setText(data.address)
            etAddContactEmail.setText(data.email)
            // TODO: 그룹 스피너 세팅
            // TODO: 생일 세팅
            // TODO: MBTI 세팅
            // TODO: 알림 생일이면 세팅?
            etAddContactMemo.setText(data.memo)
        }
    }

    private fun setDataToViews() {
        if (data == null) {
            Log.e("myTag", "data == null")
            return
        }
        setData(data!!)
    }

    private fun notification(data: ContactData) {
        // 알림에 띄울 이미지 비트맵이랑 실행시킬 인텐트 준비.
//        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val intent = Intent(this, ContactActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 알림 정보
        val builder = NotificationCompat.Builder(this, MyManagers.channelId).apply {
            setSmallIcon(R.drawable.call_logo)
//            setLargeIcon(data.profileImage.toDrawable().toBitmap())
            setWhen(System.currentTimeMillis())  // 이건 언제 보일지가 아니라 이 알림이 언제 알림인지 정보임
            setContentTitle("띠리링! 알림!")
            setContentText("${data.name}님께 연락할 시간입니다.")
//            flag = !flag  // 알림 버튼 클릭 시 번갈아 스타일 보려고 만든 flag
//            if (flag) {
//                setStyle(
//                    NotificationCompat.BigTextStyle()
//                        .bigText("setStyle(NotificationCompat.BigTextStyle().bigText(지금내용)  아주 긴 텍스트를 쓸 때는 여기에 하면 된다. 아주 긴 텍스트 아주 긴 텍스트 아주 긴 텍스트 아주 긴 텍스트 아주 긴 텍스트 아주 긴 텍스트 끝")
//                )
//            } else {
//                setStyle(
//                    NotificationCompat.BigPictureStyle()
//                        .bigPicture(bitmap)
//                        .bigLargeIcon(null)  // hide largeIcon while expanding
//                )
//            }
//            setLargeIcon(bitmap)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
//            addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
        }

        MyManagers.notificationManager?.notify(11, builder.build())
            ?: Log.e("myTag", "노티 매니저 없음")
    }

    private fun setAlarm(data: ContactData, sec: Long = 0) {
        val intent = Intent(applicationContext, MyReceiver::class.java).apply {
            putExtra("title", "띠리링! 알림!")
            putExtra("text", "${data.name}님께 연락할 시간입니다.")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        MyManagers.alarmManager?.also {
            it[AlarmManager.RTC, System.currentTimeMillis() + sec * 1000] = pendingIntent
        }
            ?: Log.e("myTag", "알람 매니저 null")

//        val intent2 = Intent(this, ContactActivity::class.java)
//        val pendingIntent2 = PendingIntent.getActivity(
//            this, 101, intent2,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//        val builder = NotificationCompat.Builder(this, MyManagers.channelId).apply {
//            setSmallIcon(R.mipmap.ic_launcher)
//            setWhen(System.currentTimeMillis())  // 이건 언제 보일지가 아니라 이 알림이 언제 알림인지 정보임
//            setContentTitle("알림 타이틀")
//            setContentText("저장한 노티. 꺼내와봅시다.")
//            setContentIntent(pendingIntent2)
//            setAutoCancel(true)
////            addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
//        }
//        MyReceiver.savedNotification = builder.build()
    }

    private fun openGallery() {
        /*
        ACTION_PICK 으로 하면 사진 선택하게끔 나오는데 절대경로 오류남.
        ACTION_OPEN_DOCUMENT으로 해야 가능. 맘에 안드네..
         */
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }
}