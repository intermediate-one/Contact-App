ContactListFragment
=
# 개요
연락처(ContactDatabase)에 기본적으로 저장되어 있는 데이터,
또는 갱신된 데이터를 받아와서 사용자에게 보여주는 역할을 하는 Fragment이다.

# 구성요소
## ContactListFragment.kt
### ContactListFragment
연락처 페이지를 구성할 프래그먼트
```kotlin
class ContactListFragment : Fragment() {
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private var _binding:FragmentContactListBinding? = null
    private val binding get() = _binding!!
    
    private val mainPage by lazy { context as ContactActivity }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(mainPage, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickFloatingActionButtonAddContact()
        onViewCreatedInit()
    }

    override fun onResume() {
        super.onResume()
        notifyDataSetChangedStayedScroll()
    }

    private fun onViewCreatedInit() {
        /** 내용 **/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```
### onViewCreatedInit
Recycler View와 RecyclerView Adapter를 연결해 Fragment에 띄우는 함수이다.
```kotlin
private fun onViewCreatedInit() {
        var sortedList = ContactDatabase.nameSorting()
        var clAdapter = ContactListAdapter(sortedList)
        with(binding) {
            recyclerView.adapter = clAdapter
            btnListGrid.setOnClickListener {
                binding.recyclerView.apply {
                    listGrid *= -1
                    when (listGrid) {
                        1 -> {
                            layoutManager = LinearLayoutManager(mainPage, LinearLayoutManager.VERTICAL, false)
                            btnListGrid.setImageResource(R.drawable.icon_grid_black)    // 현재가 list니 버튼을 누르면 Grid로 바꿀 수 있다는 것을 미리 보여주기 위해
                            notifyDataSetChangedStayedScroll()  //ddd
                        }
                        -1 -> {
                            layoutManager = GridLayoutManager(mainPage, 3, GridLayoutManager.VERTICAL, false)
                            btnListGrid.setImageResource(R.drawable.icon_list_black)
                            notifyDataSetChangedStayedScroll()  //ddd
                        }
                    }
                }
            }
//            activityResultLauncher = ...
        }
    }
```
### activityResultLauncher
DetailActivity가 수정되었는지 확인 후 수정된 값을 받아오는 부분
```kotlin
activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    if (it.resultCode == AppCompatActivity.RESULT_OK) {
        val isFavorite = it.data?.getBooleanExtra("isFavorite",false) ?: false
        val itemNum = it.data?.getIntExtra(Contants.ITEM_INDEX,0) ?: 0
    
        if (isFavorite) {
            sortedList[itemNum].favorite = true
        } else {
            if (sortedList[itemNum].favorite) {
                sortedList[itemNum].favorite = false
            }
        }
        //어댑터 갱신해주는 코드
        notifyDataSetChangedStayedScroll()
        //수정된 값 받아오기
    }else if (it.resultCode == AppCompatActivity.RESULT_FIRST_USER) {
        val isFavorite = it.data?.getBooleanExtra("isFavorite",false) ?: false
        val itemNum = it.data?.getIntExtra(Contants.ITEM_INDEX,0) ?: 0
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
        sortedList[itemNum].profileImage = data?.profileImage ?: -1
        userPosition = itemNum
        notifyDataSetChangedStayedScroll()
    }
}
```
### ItemClick Interface
전달받은 값을 Detail Activity로 보내고 값을 다시 받는 Interface
```kotlin
//Detail로 보내고 다시 값 받기
clAdapter.itemClick = object : ContactListAdapter.ItemClick{
    override fun onClick(view: View, position: Int) {
        sortedList = ContactDatabase.nameSorting()  // 정렬된 Object를 다시 받아오는 작업
        val intent = Intent(activity,DetailActivity::class.java)
        intent.putExtra(Contants.ITEM_DATA,sortedList[position])
        intent.putExtra(Contants.ITEM_INDEX,position)
        activityResultLauncher.launch(intent)
    }
}
```
### Companion Object
클릭된 사용자의 Item 위치와, 현재 RecyclerView의 Layout을 List/Grid로 전환하기 위한 전역변수
```kotlin
companion object {
    var userPosition = 0
    var listGrid = 1
}
```
### onClickFloatingActionButtonAddContact
플로팅 액션 버튼 클릭 시 연락처 추가 액티비티(AddContactActivity.kt)로 이동하는 함수
```kotlin
private fun onClickFloatingActionButtonAddContact() {
    binding.fbtnContactListAdd.setOnClickListener {
        val intent = Intent(activity, AddContactActivity::class.java)
        startActivity(intent)
    }
}
```
### notifyDataSetChangedStayedScroll
Recycler View의 Adapter를 전체적으로 갱신하는 함수
```kotlin
private fun notifyDataSetChangedStayedScroll() {
    binding.recyclerView.adapter?.notifyDataSetChanged()
}
```
## ContactListAdapter.kt
### adapter
Fragment와 RecyclerView를 연결할 Adapter
```kotlin
class ContactListAdapter(private var userDataList:List<ContactData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var holdList:Holder
    private lateinit var holdGrid:Hold

    companion object {
        private const val LINEAR_LAYOUT = 1
        private const val GRID_LAYOUT = -1
    }

    interface ItemClick {
        fun onClick(view : View, position:Int)
    }
    var itemClick : ItemClick? = null

    inner class Holder(binding: LayoutRvUserBinding):RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivRvUser
        val name = binding.tvRvUserName
        val favorite = binding.ivRvFavorite
    }

    inner class Hold(binding: LayoutRvUserGridBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivRvUserGrid
        val name = binding.tvUserNameGrid
        val favorite = binding.ivRvFavoriteGrid
    }

    override fun getItemViewType(position: Int):Int {   // Holder나 Hold를 Casting하기 위해 사용
        when(listGrid) {
            LINEAR_LAYOUT -> listGrid = LINEAR_LAYOUT
            GRID_LAYOUT -> listGrid = GRID_LAYOUT
        }
        return listGrid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        return when(viewType) {
            LINEAR_LAYOUT -> Holder(LayoutRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            GRID_LAYOUT -> Hold(LayoutRvUserGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw Exception("Layout Adapter 연결에 실패함")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it,position)
        }

        when(listGrid) {
            1 -> {
                holdList = holder as Holder
                with(holdList) {
                    userDataList = nameSorting()
                    name.text = userDataList[position].name
                    userDataList[position].profileImage?.let { image.setImageResource(it) }
                    userDataList[position].profilePath?.let { image.setImageURI(it.toUri()) }
                    when(userDataList[position].favorite) {
                        true -> favorite.setImageResource(R.drawable.star_full)
                        false -> favorite.setImageResource(R.drawable.star_empty)
                    }
                }
            }
            -1 -> {
                holdGrid = holder as Hold
                with (holdGrid) {
                    userDataList = nameSorting()
                    name.text = userDataList[position].name
                    userDataList[position].profileImage?.let { image.setImageResource(it) }
                    userDataList[position].profilePath?.let { image.setImageURI(it.toUri()) }
                    when(userDataList[position].favorite) {
                        true -> favorite.setImageResource(R.drawable.star_full)
                        false -> favorite.setImageResource(R.drawable.star_empty)
                    }
                }
            }
            else -> throw Exception("Holder를 Casting 할 수 없습니다.")
        }
    }

    override fun getItemCount(): Int = userDataList.size
}
```