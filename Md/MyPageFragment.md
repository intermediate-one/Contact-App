[readme](https://github.com/intermediate-one/Contact-App/blob/dev/README.md)

# MyPageFragment.md

# Layout
(마이페이지 이미지)

디테일 페이지를 팀원이 먼저 만들고, 이를 복사한 뒤 약간의 수정을 거쳐 만들었다.   
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
연락처 정보를 담고 있는 ContactDatabase 오브젝트에 내 정보를 담는 데이터 클래스 변수가 있다.   
프래그먼트가 로드될 때 해당 변수에서 정보를 가져와 뷰에 뿌려준다. 

## 수정 페이지로 연결

## 연락처 복사

## 문자 보내기
