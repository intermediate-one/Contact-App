[readme](https://github.com/intermediate-one/Contact-App/blob/dev/README.md)

# MyPageFragment.md

# Layout

(마이페이지 이미지)

디테일 페이지를 팀원이 먼저 만들고, 이를 가져와서 약간의 수정을 거쳐 만들었다.\
레이아웃에 대한 설명은 디테일 페이지를 참고.

# Fragment
## 사용자 정보 표시

```kotlin
// onViewCreated
setData(ContactDatabase.myContact)

// 뷰에 데이터 입력하는 함수
private fun setData(contactData: ContactData) {
    binding.tvMyPageName.text = contactData.name
    contactData.profileImage?.let { binding.ivMyPagePerson.setImageResource(it) }
    contactData.profilePath?.let { binding.ivMyPagePerson.setImageURI(it.toUri()) }
    binding.tvMyPageMobilePerson.text = contactData.phoneNumber
    binding.tvMyPageEmailPerson.text = contactData.email
    binding.tvMyPageGroupPerson.text = contactData.group
    binding.tvMyPageBirthPerson.text = contactData.birthday
    binding.tvMyPageMbtiPerson.text = contactData.mbti
    binding.tvMyPageMemo.text = contactData.memo
}
```

연락처 정보를 담고 있는 ContactDatabase 오브젝트에 내 정보를 담는 데이터 클래스 변수가 있다.\
프래그먼트가 로드될 때 해당 변수에서 정보를 가져와 뷰에 뿌려준다.

## 수정 페이지로 연결

```kotlin
binding.btnMyPageEdit.setOnClickListener {
    val intent = Intent(context, AddContactActivity::class.java)
    intent.putExtra(Contants.ActType, ActType.EDIT_MY_PAGE)
    intent.putExtra(Contants.ITEM_DATA, ContactDatabase.myContact)
    launcher.launch(intent)
}
```

우상단 수정 버튼을 누르면 AddContactActivity로 넘어간다.\
처음에 연락처 추가하는 액티비티로 먼저 만들었는데, 연락처 수정에서도 같은 화면을 재사용할 수 있을 것 같아서 AddContactActivity에서
intent로 동작 종류를 넘겨받으면 이에 따라 연락처 추가 혹은 연락처 변경의 동작을 하도록 했다.

## 연락처 복사

```kotlin
binding.ivMyPageShare.setOnClickListener {
    val clipboard =
        requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", ContactDatabase.myContact.phoneNumber)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "전화번호가 클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
}
```

처음에는 연락처 공유 기능을 구상했다. 공유 버튼을 누르면 문자나 카톡 등 연동되는 앱 목록을 띄우게끔
하는 것을 생각해봤는데, 제한된 개발기간에서 우선순위에 밀려서, 연락처를 클립보드에 복사하는 것으로
변경했다.

## 문자 보내기

```kotlin
binding.ivMyPageShare.setOnClickListener {
    val clipboard =
        requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", ContactDatabase.myContact.phoneNumber)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "전화번호가 클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
}
```

연락처 앱들을 보니, 자기 자신의 번호로도 문자를 보낼 수 있도록 되어있었다.\
그래서 마이페이지에서도 내 연락처로 문자가 연결되도록 구현했다.