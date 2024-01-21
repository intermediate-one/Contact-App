<img src="https://github.com/intermediate-one/Contact-App/assets/62335652/d0d27662-9fc8-4f36-a9be-8d7eb620c686" height="200" />
띠리링!

# Contact-App
앱개발 숙련 팀플 - 연락처 앱




## 프로젝트 개요

### 프로젝트 팀원
박희수, 심규상, 김은이, 최성현

### 앱 이름
별개냥

### 앱 이름의 의미
강아지🐶와 고양이😺의 의미가 들어가 있으면서 인⭐그램의 '별'의 느낌도 가져가 익숙한 sns 느낌인데 강아지와 고양이가 주가 되는 sns임을 나타내는 동시에
'별거냐~'라는 의미도 가진다.

### 앱의 목적
자신의 펫이 주체가 되어 앱 내에서 재밌고 자유롭게 이야기를 공유하는 공간이 되는 것

### 프로젝트 일정
24/01/15(월) ~ 24/01/21(일)

### 프로젝트 기획
먼저 팀노션에서 자유롭게 아이디어 브레인스토밍을 하고
앱의 컨셉, 레퍼런스가 잡히고 나서 Figma를 가지고 레이아웃에 대한 의견을 나누고 결정했다.

<Figma 링크>

<p align="center">
 <a href="https://www.figma.com/file/W77t6eKPMSJsTW6WhqeqCJ/%EB%B3%84%EA%B0%9C%EB%83%A5?type=design&mode=design&t=WLSCOLn5fvtgA33U-1">
  <img alt="와이어프레임" src="https://github.com/heesoo-park/TeamAssignment3_2/assets/116724657/901448b8-cb81-49bb-a4bb-ef29a7fa5121"
</p>

### 역할 분담
이용제 : MyPageFragment,연락처 복사,알림, 깃과 전체적인 코드 관리

김은이 : ContactActivity, GroupFragment, AddContactActivity, Dialog

홍유창 : ContactListFragment

김건두 : DetailActivity, 전화걸기, 문자보내기


### 코드 컨벤션
베이스는 Kotlin 스타일 가이드에 맞춰 진행했다.

가장 기본적인 부분에 대한 코드 컨벤션을 다음과 같이 설정했다.

|제목|내용|
|------|---|
|클래스 이름|PascalCase|
|함수 이름|camelCase|
|변수 이름|camelCase|
|문자열 리소스 이름|snake_case|
|문자열 이름|(페이지_)용도|
|레이아웃 이름|속성_페이지_위치|
|뷰 이름|속성_페이지_용도(_사용방법)|
|리스트 내 변수|엔터로 정리|
|enum class 내 변수 이름|모두 다 대문자|
|drawable 내 이미지 이름|img(_위치)_용도|
|drawable 내 셀렉터 이름|selector(_위치)_용도|
|drawable 내 아이콘 이름|ic(_위치)_용도|
|by lazy|엔터로 구분|
|조건이 3개 이상일 때 |when 사용|
|코드 한 줄을 여러 줄로 작성하는 경우|안드로이드 스튜디오의 흰 줄을 넘어가는 경우|
|기본적인 코드 정리|CTRL + ALT + L|

### 협업 관련 사항 조정
**안드로이드 스튜디오** 사용

에뮬레이터는 **Nexus 5 API 31** 사용

**풀 리퀘스트** 전에는 말해주기 -
풀 리퀘스트에는 최대한 이름과 내용을 자세히 기록하고 팀원을 리뷰어로 등록해 최소 1명에게 확인받은 후 merge 진행

**Readme.md**를 이용해서 개발 기록 작성

### 만든 기능 :

#### 1. ViewPager를 통해 여러 탭 프래그먼트와 연결
은이님

#### 2. RecyclerView 구현
유창님

#### 3. Adapter 사용
유창님

#### 4. ViewHolder 사용
유창님

#### 5. EditText를 사용하여 정보 입력 구현
...?

#### 6. 유효성 검사
은이님

#### 7. 그룹,mbti 선택 스피너
은이님

#### 8. 생일 선택 다이얼 로그
은이님

#### 9. 메세지, 전화버튼을 누르면 해당 번호로 전화, 문자
건두님

#### 10. 즐겨찾기 기능 구현
건두님

#### 11. 공유버튼 누르면 연락처 복사
용제님

#### 12. StickyHeader를 통해 그룹별로 나누기
은이님

#### 13. notification
용제님

## TroubleShooting
- 브랜치에서 풀 했을시 충돌 문제

  깃에서 개발하고 있는 dev브랜치가 업데이트가 되면 본인이 작업하고 있는 파일에 pull 해야 하는데 내가 작업한 부분과

  깃에서 불러온 부분이 겹쳤을 때 충돌이 일어나게 된다

  ![](https://xacdo.net/wp/wp-content/uploads/2021/02/idea-idea_2016_3_vcs_magic_resolve.png)

  그랬을때 이런 이미지가 나타나게 되는대

  이미지의 >>> 버튼을 누르면 가운데 파일로 옮겨지게 된다

  서로의 코드를 비교하고 필요한 코드와 그렇지 않은 코드를 구분한지 머지하게해서 해결했다.

- ImageButton 클릭시 stroke를 주는 selector를 작성해서 적용해도 아무런 변화가 없는 문제

-> stroke가 객체의 안쪽으로 생겨서 생기는 문제

-> ImageButton에 padding을 줘 stroke가 보이게 함

-> 해결
