# GroupFragment.kt

## 기능 목차

1. sticky header 사용하여 그룹끼리 묶은 연락처 리스트 디스플레이
2. 디테일 페이지에서 수정된 즐겨찾기 유무 업데이트 시키기
3. 디테일 페이지에서 수정한 정보 업데이트 시키기

### 1. sticky header 사용하여 그룹끼리 묶은 연락처 리스트 디스플레이

가장 먼저 헤더 파일을 만들어 주어야 한다.

> header_group.xml

    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/default_background">

        <TextView
            style="@style/text20.bold"
            android:id="@+id/tv_header"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginVertical="5dp"
            android:paddingStart="20dp"
            android:text="text"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

그리고 item 리스트는 ContactListFragment에서도 사용되는 리사이클러뷰를 사용해준다.

이제 본격적으로 스티키해더를 구현해 줄 StickyHeaderItemDecoration 클래스를 만들었다.

[StickyHeaderItemDecoration.kt](https://github.com/intermediate-one/Contact-App/blob/dev/app/src/main/java/com/example/contactapp/data/StickyHeaderItemDecoration.kt)

원래 데이터리스트에 있었던 정보를 그룹별로 헤더와 아이템 값을 담아주는 Contacts 데이터 클래스를 생성하였다.

[Contacts.kt](https://github.com/intermediate-one/Contact-App/blob/dev/app/src/main/java/com/example/contactapp/data/Contacts.kt)

> Contacts.kt

    sealed class Contacts {
        data class Title(var ctitle: String) : Contacts()
        @Parcelize
        data class ContactList(
            var cname: String,
            var cPhoneNumber: String,
            var cImage: Int,
            var cFavorite: Boolean
        ) : Contacts() ,Parcelable
    }

그 안에는 헤더 부분에 해당하는 값을 Title data 클래스에 저장하고 연락처 리스트 정보에 일부를 저장해주었다.

여기에 있는 정보들은 RecyclerView 안에 들어가는 정보들이다.

다시 [GroupFragment.kt](https://github.com/intermediate-one/Contact-App/blob/dev/app/src/main/java/com/example/contactapp/fragment/GroupFragment.kt)
로 돌아와서 dataList에 있는 정보를 Contacts에 담아준다.

> Sticky Header 구현을 위한 데이터 만들기

        val dataList = arrayListOf<Contacts>() . apply {
            ContactDatabase.groupData.forEach {groupName->
                add(Contacts.Title(groupName))
                ContactDatabase.getContactPerGroup(groupName).forEach{
                    add(Contacts.ContactList(it.name, it.phoneNumber, it.profileImage ?: R.drawable.user_profile_empty, it.favorite))
                }
                remove(Contacts.Title(ContactDatabase.groupData[0]))
            }
        }

> [StickyHeaderItemDecoration.kt](https://github.com/intermediate-one/Contact-App/blob/dev/app/src/main/java/com/example/contactapp/data/StickyHeaderItemDecoration.kt)을 연결하기

        binding.rvGroup.addItemDecoration(
            StickyHeaderItemDecoration(
                binding.rvGroup
            ) { itemPosition: Int ->
                dataList[itemPosition] is Contacts.Title

            }
        )


### 2. 디테일 페이지에서 수정된 즐겨찾기 업데이트 시키기

ActivityResultLauncher을 이용하여 RESULT_OK를 받으면 실행시켰다.

                val isFavorite = it.data?.getBooleanExtra("isFavorite",false) as Boolean
                it.data?.getIntExtra(Contants.ITEM_INDEX,0) as Int

                val groupPosition = it.data?.getIntExtra("groupPosition", 0)

                when (val item = dataList[groupPosition!!]) {
                    is Contacts.ContactList -> {
                        item.cFavorite = isFavorite
                        adapter.notifyItemChanged(groupPosition)

                    }
                    is Contacts.Title -> {
                        Log.d("GroupFragment", "Wrong groupPosition Selected")
                    }
                }

코드가 onResume 되었을 때에 리스트 값들이 업데이트가 되도록 onResume 함수를 오버라이딩 해주었다.

    override fun onResume() {
        super.onResume()
        val dataList = arrayListOf<Contacts>() . apply {
            ContactDatabase.groupData.forEach {groupName->
                add(Contacts.Title(groupName))
                ContactDatabase.getContactPerGroup(groupName).forEach{
                    add(Contacts.ContactList(it.name,it.phoneNumber, it.profileImage ?: R.drawable.user_profile_empty, it.favorite))
                }
                remove(Contacts.Title(ContactDatabase.groupData[0]))
            }
        }
        val adapter = GroupAdapter(dataList)
        notifyDataSetChangedStayedScroll()
    }

### 3. 디테일 페이지에서 수정한 정보 업데이트 시키기

ActivityResultLauncher을 이용하여 RESULT_RESULT_FIRST_USER를 받으면 실행시켰다.

                val isFavorite = it.data?.getBooleanExtra("isFavorite",false) as Boolean
                val itemNum = it.data?.getIntExtra(Contants.ITEM_INDEX,0) as Int
                val data: ContactData? by lazy {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        arguments?.getParcelable(Contants.ITEM_DATA, ContactData::class.java)
                    } else {
                        arguments?.getParcelable<ContactData>(Contants.ITEM_DATA)
                    }
                }
                if (isFavorite) {
                    sortedList[itemNum].favorite = true
                } else {
                    if (sortedList[itemNum].favorite) {
                        sortedList[itemNum].favorite = false
                    }
                }
                sortedList[itemNum].name = data?.name ?: "이름"
                sortedList[itemNum].email = data?.email ?: "이름"
                sortedList[itemNum].group = data?.group ?: "이름"
                sortedList[itemNum].address = data?.address ?: "이름"
                sortedList[itemNum].birthday = data?.birthday ?: "이름"
                sortedList[itemNum].mbti = data?.mbti ?: "이름"
                sortedList[itemNum].notification = data?.notification
                sortedList[itemNum].phoneNumber = data?.phoneNumber ?: "010-1234-1234"
                sortedList[itemNum].memo = data?.memo ?: "010-1234-1234"
                sortedList[itemNum].profileImage = data?.profileImage as Int
//                GroupFragment.userPosition = itemNum
//                adapter.notifyItemRangeChanged(ContactListFragment.userPosition,sortedList.size)
                notifyDataSetChangedStayedScroll()


            

