# AddContactActivity.kt

## 기능 목차

1. 이름 / 이메일 / 주소  유효성 검사
2. 이름 / 이메일 입력 시 확인 버튼 활성화
3. 그룹 / mbti 스피너
4. 그룹 추가 다이얼로그
5. 생일 추가 다이얼로그
6. 알림 설정
7. 메모 EditText
8. 페이지 (내 연락처 추가/수정 | 연락처 수정) 재활용


### 1. 이름 / 이메일 / 주소  유효성 검사

editText를 클릭하여 focus가 바뀌거나 editText에 적혀진 Text가 수정될때마다 실행될 setTextChanedListener() 메소드와 setOnFocusChangedListener 함수를 만들었다.

> setTextChangedListener()

    private fun setTextChangedListener() {
        editTextArray.forEach { editText ->
            editText.addTextChangedListener {
                editText.setErrorMessage()
                setConfirmButtonEnable()
            }
        }
    }
> setOnFocusChangedListener()

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

포커스나 텍스트가 바뀜에따라 유효성 검사를 해주었다.

> EditText.setErrorMessage()

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

각각의 유효성 검사는 getMessageValid이름() 메소드를 사용하였고 표시할 에러 메세지는 enum class에 저장해두었다가 가져오는 방식을 이용했다.

enum class에 따로 저장함으로써 코드를 더 블럭화 시킬 수 있었다.

이름은 한글만 작성할 수 있게 해 놓았다.

이메일은 @와 .을 포함해야하고 영어만 가능하다.

휴대번호는 01로 시작해야하며 6자리나 10자리 숫자여야한다.

주소는 최대3줄 작성할 수 있다.

이름과 이메일은 무조건 채워 넣어야하고 나머지는 입력하거나 선택하지 않아도 유효성 검사에 통과하도록 했다.


> getMessageValidName()

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

### 2. 이름 / 이메일 입력 시 확인 버튼 활성화

모든 유효성 검사를 통과했을 시에 버튼을 활성화 시켜주었다.

> setConfirmButtonEnable()

    private fun setConfirmButtonEnable() {
        binding.btnAddContactComplete.isEnabled = getMessageValidName() == null
                && getMessageValidNumber() == null
                && getMessageValidEmail() == null
                && getMessageValidAddress() == null
                && getMessageValidMemo() == null
        //ddd
        binding.btnAddContactComplete.isEnabled = true
    }


### 3. 그룹 / mbti 스피너


> setGroupProvider(index: Int)

    private fun setGroupProvider(index: Int?) {
        //object클래스 ContactDatabase.kt에 저장되어 있는 groupData에 있는 값들을 추가해준다.
        binding.spAddContactGroup.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item,
            groupData.toList()


        )

        when (index) {
            null -> Unit
            else -> binding.spAddContactGroup.post {binding.spAddContactGroup.setSelection(groupData.size-1)}
        }

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

함수의 매개변수로 index를 줌으로써 이미 그룹이 선택되어있는 경우나 새로운 그룹을 추가했을 때 그 그룹이 이미 스피너에 선택되어 있도록 했다.

> 매개변수가 null이 아닐때

    when (index) {
            null -> Unit
            else -> binding.spAddContactGroup.post {binding.spAddContactGroup.setSelection(groupData.size-1)}
        }

> mbti spinner : setMbtiProvider()

그룹 스피너와 마찬가지로 스피너를 만들었고 스피너가 선택 되었을 때 그 선택된 값을 저장해주는 코드를 작성해주었다.

    newContactMbti = parent?.getItemAtPosition(position).toString()

### 4. 그룹 추가 다이얼로그

> 커스텀 다이얼로그 레이아웃 dialog_add_group.xml

다이얼로그에 editText만 추가해주었습니다.

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="16dp">

            <EditText
                android:id="@+id/et_dialog_add_group"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp"
                android:ems="15"
                android:inputType="textPersonName"
                android:hint="@string/dialog_enter_group" />
        </LinearLayout>
    </LinearLayout>

> 다이얼로그 셋팅

    val builder = AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_add_group))
                .setMessage(getString(R.string.dialog_new_group))
                .setView(dialogView)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(getString(R.string.dialog_confirm_add_group)) { _, _ ->

                    addGroup(groupText)
                    setGroupProvider(groupData.size-1)
                }
                .setNegativeButton(getString(R.string.dialog_cancel_add_group)) { dialog, _ ->
                    dialog.cancel()
                }
                .setCancelable(true)
                .show()

다이얼로그가 뜰때 제목 메세지, 뷰, 버튼 등을 셋팅해주었다.

PositiveButton을 눌렀을 때 그룹 리스트인 GroupData에 groupText를 추가해 주었다.

그리고 setGroupProvider() 스피너 메소드를 다시 실행해 주었다. 

이때 groupData에 있는 마지막 인덱스 값을 넘겨 주었다.

(마지막 인덱스는 방금 추가한 그룹의 인덱스이기 때문이다.)

> 다이얼로그 에딧택스트 유효성 검사

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
                            groupText = text
                            builder.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true

                        }

                        else -> editGroup.error =
                            getString(AddContactErrorMessage.OVERLAPPING_GROUP_NAME.message)

                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })



### 5. 생일 추가 다이얼로그

> showDatePicker()

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

날짜를 골라주는 다이얼로그를 작성해주었다. 

고른 날짜는 일/월/년 순으로 작성하도록 해 놓았다.

그리고 선택된 생일을 AddContactActivity에 표시 하기 위해서 다이얼로그 버튼 옆에 textView에 날짜 값을 셋팅해주었다.

    binding.tvAddContactSelectedDate.text = formattedDate

### 6. 알림 설정

### 7. 메모 EditText


> xml

    <com.google.android.material.textfield.TextInputLayout
                    style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="@string/add_contact_add_memo"
                    app:boxCornerRadiusBottomEnd="10dp"
                    app:boxCornerRadiusBottomStart="10dp"
                    app:boxCornerRadiusTopEnd="10dp"
                    app:boxCornerRadiusTopStart="10dp"
                    app:layout_constraintTop_toBottomOf="@id/tv_detail_memo"
                    app:boxStrokeColor="@color/default_background"
                    app:boxStrokeWidth="1dp"
                    app:boxStrokeWidthFocused="1dp"
                    app:errorIconDrawable="@null"
                    app:hintTextColor="#d3d3d3">

                    <com.google.android.material.textfield.TextInputEditText
                        android:id="@+id/et_add_contact_memo"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:inputType="textMultiLine"
                        android:gravity="start|top"
                        android:paddingStart="10dp"
                        android:paddingEnd="0dp"
                        android:lines="3"
                        android:maxLines="10"
                        android:textCursorDrawable="@null"
                        android:textSize="12sp" />
                </com.google.android.material.textfield.TextInputLayout>

여러줄 입력할 수 있는 란을 만들고 싶은데 그냥 EditText를 사용하면 아래 밑줄이 쳐져 있어서 UI상 이쁘지 않았다.

그래서 검색해본 결과 TextInputEditText를 사용해 주었고 그 테두리도 둥글고 이쁘게 꾸미기 위해 TextInputLayout 도 셋팅해 주었다.

### 8. 페이지 (내 연락처 추가/수정 | 연락처 수정) 재활용
