# Contact-App

<h1 align="center">
<img height="90px" src="https://github.com/intermediate-one/Contact-App/assets/62335652/d0d27662-9fc8-4f36-a9be-8d7eb620c686" align="center" alt="" />
&nbsp
<img height="80px" src="https://github.com/intermediate-one/Contact-App/assets/62335652/671bc274-f7f4-4bd2-8c5b-55269ecd9a54" align="center" alt="" />
</h1>
 <p align="center">
   <a>간편하고 이쁜 UI의 연락처 앱!</a>
   <br />
   <br />
    <a> 프로젝트 팀원</a>
    <br />
    <a href="https://github.com/jericho31">이용제</a>
    ·
    <a href="https://github.com/kgdrjsen">김건두</a>
    ·
    <a href="https://github.com/eun-24k">김은이</a>
    ·
    <a href="https://github.com/UChangh">홍유창</a>
   <br />
   <br />
    <a>페이지 상세 설명</a>
    <br />
    <a href="https://github.com/intermediate-one/Contact-App/blob/main/Md/ContactListFragment.md">연락처 목록 탭</a>
    ·
    <a href="https://github.com/intermediate-one/Contact-App/blob/main/Md/GroupFragment.md">그룹별 보기 탭</a>
    ·
    <a href="https://github.com/intermediate-one/Contact-App/blob/main/Md/MyPageFragment.md">마이페이지 탭</a>
    ·
    <a href="https://github.com/intermediate-one/Contact-App/blob/main/Md/AddContactActivity.md">연락처 추가 페이지</a>
    ·
    <a href="https://github.com/intermediate-one/Contact-App/blob/main/Md/Detail.md">연락처 상세정보 페이지</a>
</p>
 <h3 align = "center">PreView</h3>

 <p align ="center">
 <img alt="" src ="https://github.com/intermediate-one/Contact-App/assets/62335652/952c94f7-c45d-4070-acea-cc26f8471d2e" height="380"/>
 <img alt="" src ="https://github.com/intermediate-one/Contact-App/assets/62335652/520ef269-ccf6-4692-a51b-c125eccec61b" height="380" />
 <img alt="" src ="https://github.com/intermediate-one/Contact-App/assets/62335652/4f679fa9-2337-4caa-bc22-437b86c1c778" height="380" />
 <img alt="" src ="https://github.com/intermediate-one/Contact-App/assets/62335652/7d0d2bc5-66db-433c-be6d-2b8832cbd893" height="380" />
 <img alt="" src ="https://github.com/intermediate-one/Contact-App/assets/62335652/cb2bdc5f-19a6-4767-b3ae-02b993f475b5" height="380" />
 <img alt="" src ="https://github.com/intermediate-one/Contact-App/assets/62335652/704791c2-8f78-459b-84d3-f2365e3b940c" height="380" />
 <img alt="" src ="https://github.com/intermediate-one/Contact-App/assets/62335652/a77d9260-0b99-4571-87e3-508a69b6fe6a" height="380" />
 </p>

### 프로젝트 팀원
이용제, 김건두, 김은이, 홍유창 

### 앱 이름
띠리링!

### 앱 이름의 의미
누구나 전화 소리 하면 떠올릴 수 있는 띠리링!
이렇게 쉽게 떠올릴 수 있는 만큼 누구나 쉽게 사용할 수 있는 앱이 되었으면 바램이 들어갔다.

### 앱의 목적
간단하고 사용자가 사용하기 편안한 연락처 앱

### 프로젝트 일정
24/01/15(월) ~ 24/01/21(일)

### 프로젝트 기획
먼저 팀노션에서 자유롭게 아이디어 브레인스토밍을 통해 사전 조사로 이미 시장에 유통되고 있는 
연락처 앱들을 살펴보고 밴치마킹 할 부분을 생각하고, 컬러코드도 여러가지를 가져와서 그 중에서 제일 눈이 편안하고 안정감을 주는 색깔을 선택했다.
이후 Figma를 통해 각 페이지마다 들어갈 기능과 레이아웃 등에 대한 의견을 나누고 결정하였다.
또한 매일 2번씩 코드 리뷰 및 PR을 진행 하고, 팀 노션에 각 페이지별로 들어가는 내용들을 기록하여 완료했는지, 진행중인지 팀원들이 알 수 있게 하였다.

<Figma 링크>

<p align="center">
 <a href="https://www.figma.com/file/FBuwfsaWWQwWYkBMvutzhS?&viewer=1">
  <img alt="Wire Frame" src="https://github.com/intermediate-one/Contact-App/assets/62335652/8522dc45-84be-45ba-a834-04a110bc4e10" />
 </a>
</p>


[//]: # (<p align="center">)

[//]: # ( <a href="https://www.figma.com/file/W77t6eKPMSJsTW6WhqeqCJ/%EB%B3%84%EA%B0%9C%EB%83%A5?type=design&mode=design&t=WLSCOLn5fvtgA33U-1">)

[//]: # (  <img alt="와이어프레임" src="https://github.com/heesoo-park/TeamAssignment3_2/assets/116724657/901448b8-cb81-49bb-a4bb-ef29a7fa5121")

[//]: # (</p>)

### 역할 분담
이용제 : MyPageFragment,연락처 복사,알림, 깃과 전체적인 코드 관리

김은이 : ContactActivity, GroupFragment, AddContactActivity, Dialog

홍유창 : ContactListFragment

김건두 : DetailActivity, 전화걸기, 문자보내기


### 코드 컨벤션
베이스는 Kotlin 스타일 가이드에 맞춰 진행했다.

가장 기본적인 부분에 대한 코드 컨벤션을 다음과 같이 설정했다.

| 제목                        | 내용                      |
|---------------------------|-------------------------|
| 클래스 이름                    | PascalCase              |
| 함수 이름                     | camelCase               |
| 변수 이름                     | camelCase               |
| 문자열 리소스 이름                | snake_case              |
| 문자열 이름                    | (페이지_)용도                |
| 레이아웃 이름                   | 속성_페이지_위치               |
| 뷰 이름                      | 속성_페이지_용도(_사용방법)        |
| 리스트 내 변수                  | 엔터로 정리                  |
| enum class 내 변수 이름        | 모두 다 대문자                |
| drawable 내 이미지/셀렉터/아이콘 이름 | img/selector/ic(_위치)_용도 |
| 조건이 3개 이상일 때              | when 사용                 |
| 기본적인 코드 정리                | CTRL + ALT + L          |

### 협업 관련 사항 조정
**안드로이드 스튜디오** 사용

에뮬레이터는 서로 다른 에뮬레이터를 사용하여 여러 기기에서의 호환성 보장

**풀 리퀘스트** 

풀 리퀘스트에는 최대한 이름과 내용을 자세히 기록하고 팀원을 리뷰어로 등록해 최소 1명에게 확인받은 후 merge 진행

**Readme.md**

Readme.md를 이용해서 개발 기록 작성 및 트러블 슈팅 관련 사항 작성

**코드 리뷰 및 PR진행 시간 맞추기**

매일 오후 2시 30분과 오후 7시 30분에 진행

선행 작업으로 인해 대기할 시간이 필요하다면 다른 작업(피그마나, 자료 준비, 발표 준비 등) 진행

앱 기획 단계에서 유사 앱 사전 조사 후 밴치마킹

**organization**을 만들어서 사용

기능별 브랜치 사용 해 보기

### 만든 기능 :

#### 1. ViewPager를 통해 여러 탭 프래그먼트와 연결
1) 메인, 그룹, 마이 페이지 Button 클릭 시 화면 전환

#### 2. RecyclerView 구현
1) RecyclerView로 재사용 가능한 연락처 List를 생성
2) 데이터가 업데이트 되면 기준에 따라 연락처 리스트가 재정렬됨
3) 기준) 1. 즐겨찾기, 2. 이름 순

#### 3. Adapter, ViewHolder 사용
1) RecyclerView Adapter를 생성해 List와 Grid 변경을 구현
2) 변경된 데이터를 ViewHolder에 담아 연락처, 그룹의 Item 구현

#### 4. 유효성 검사
1) 정규식을 이용해 각각의 Item마다 유효성을 검사

#### 5. 그룹, mbti 선택 스피너
1) Spinner를 선택해 Item을 설정한다.
2) 그룹, MBTI의 기본값 = 선택 안함

#### 6. 생일 선택 다이얼 로그
1) DatePickerDialog사용
2) 캘린더 구현, Dialog로 띄우기

#### 7. 메세지, 전화버튼을 누르면 해당 번호로 전화, 문자
1) Permission을 추가해 메세지, 전화 기능을 사용하는데 필요한 권한 확인
2) 앱 실행하면 해당 앱으로 전화번호를 전송
3) 메세지 버튼 클릭 -> 메세지 앱 실행
4) 전화 버튼 클릭 -> 전화 앱 실행

#### 8. 즐겨찾기 기능 구현
1) 즐겨찾기된 연락처가 연락처 리스트에서 우선적으로 표시됨
2) 연락처에 들어가서 별 모양 버튼을 누르는 것으로 해당 연락처를 즐겨찾기에 추가/제거 가능
3) 연락처 즐겨찾기 추가/제거 시 해당 기능이 실행되었다는 Toast 메세지 표시

#### 9. 공유버튼 누르면 연락처 복사
1) 연락처에서 공유버튼을 누르면 클립보드에 연락처가 복사됨 
2) 연락처가 복사되면 "전화번호가 클립보드에 복사되었습니다" Toast 메세지 출력

#### 10. StickyHeader를 통해 그룹별로 나누기
1) StickyHeader를 사용해 그룹의 Title을 View의 맨 위에 붙임
2) StickyHeader를 그룹별로 구분
3) StickyHeader가 표시하는 아이템의 갯수가 0개가 되면 다른 Header에 의해 바깥으로 밀려나감

#### 11. notification
1) 연락처 설정에서 생일을 입력하면, 해당 날짜가 될 때 알림을 수신함

## TroubleShooting
- Github Organization Repository 추가 문제 -> 해결
- push / pull / abort 가 안되는 문제 발생 -> 해결
- RecyclerView가 데이터를 받아와도 화면에 표시하지 못하는 문제 발생 -> 해결
- 다이얼로그 창을 띄웠을 때의 유효성 검사 및 버튼 활성화 하는 방법 -> 해결
- @Parcelize가 인식되지 않고 빨갛게 뜨는 문제 -> 해결
- 수정 페이지가 열리지 않는 문제 -> 해결
- 즐겨찾기가 프래그먼트 간에 업데이트 되지 않는 문제 -> 해결
- 예약 알림이 리시버에서 받아지지 않는 문제 -> 해결
- uri 구현 중, 다시 불러오면? 터지는 문제 -> 해결
- 브랜치 이동 시 rebase 실수해서 dev에 잘못된 코드가 올라간 경우 -> 해결

