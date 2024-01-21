1. Md 폴더 만들고 안에 detail.md 파일 만들기
2. 자꾸 리스트에서 디테일로 넘어갈 때 앱이 죽는 현상 -> 튜터님 피드백 -> fragment를 activity로 전환

# 레이아웃
이름,사진, 전화번호, 이메일, 그룹, 생일, mbti, 저장한 알림, 메모, 주소와 즐겨찾기 유무를
받아오는 사진 위에 별 모양으로 레이아웃을 만들고, 툴바 쪽에는 뒤로가기 버튼과 수정 버튼을 만들었다.

## 기능
리스트에서 눌렀을 때 거기에 해당하는 아이템의 정보들을 받아오고, 즐겨찾기 설정을 여기서 할 수 있다.
연락처를 복사하고, 전화, 메세지 보내는 화면으로 넘어갈 수 있다.


### 데이터 받아오기
```kotlin
private var data: ContactData? = null
private val position: Int by lazy {
    intent.getIntExtra(Contants.ITEM_INDEX, 0)
}
private val groupPosition: Int by lazy {
    intent.getIntExtra("groupPosition", 0)
}
```

### 받아온 데이터로 화면 구성 하기
```kotlin
    private fun setData(data: ContactData?) {
        data?.profileImage?.let { binding.ivDetailPerson.setImageResource(it) }
        data?.profilePath?.let { binding.ivDetailPerson.setImageURI(it.toUri()) }
        binding.tvDetailName.text = data?.name
        binding.tvDetailMobilePerson.text = data?.phoneNumber
        binding.tvDetailEmailPerson.text = data?.email
        binding.tvDetailGroupPerson.text = data?.group
        binding.tvDetailMbtiPerson.text = data?.mbti
        binding.tvDetailMemoPerson.text = data?.memo
        binding.tvDetailLocationPerson.text = data?.address
        binding.tvDetailBirthPerson.text = data?.birthday
        binding.tvDetailNotifyPerson.text = data?.notification?.toChar().toString()
}
data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    intent?.getParcelableExtra(Contants.ITEM_DATA, ContactData::class.java)
} else {
    intent?.getParcelableExtra<ContactData>(Contants.ITEM_DATA)
}
setData(data)
```

### 즐겨찾기
```kotlin
//즐겨찾기 눌렀을 때와 안 눌렀을 때
binding.ivDetailStar.setImageResource(if (isFavorite) R.drawable.star_full else R.drawable.star_empty)

        //즐겨찾기 눌렀을 때
        binding.ivDetailStar.setOnClickListener {
            if (!isFavorite) {
                binding.ivDetailStar.setImageResource(R.drawable.star_full)
                Toast.makeText(this, R.string.detail_favorite, Toast.LENGTH_SHORT).show()
//                list.find { it.phoneNumber == data?.phoneNumber }?.favorite = true
isFavorite = true
data?.favorite = true
data?.let { it1 -> ContactDatabase.editContactData(it1) }
//그 외
} else {
binding.ivDetailStar.setImageResource(R.drawable.star_empty)
Toast.makeText(this, R.string.detail_favorite_del, Toast.LENGTH_SHORT).show()
//                list.find { it.phoneNumber == data?.phoneNumber }?.favorite = false
isFavorite = false
data?.favorite = false
data?.let { it1 -> ContactDatabase.editContactData(it1) }
}
}
```

### 뒤로가기
```kotlin
        //뒤로가기(리스트로)
        binding.ivDetailBack.setOnClickListener {
            val intent2 = Intent(this, GroupFragment::class.java)
            intent2.putExtra(Contants.ITEM_INDEX, position)
            intent2.putExtra("isFavorite",isFavorite)
            intent2.putExtra("groupPosition", groupPosition)
            setResult(RESULT_OK, intent2)

            finish()
        }
```

