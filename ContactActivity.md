# ContactActivity.kt

프래그먼트를 TabLayout에 담고 있고 ViewPager2를 이용하여 프래그먼트 간의 이동을 구현하였다.

        var viewPager2Adapter = ViewPager2Adapter(this)
        viewPager2Adapter.addFragment(ContactListFragment())
        viewPager2Adapter.addFragment(GroupFragment())
        viewPager2Adapter.addFragment(MyPageFragment())

ViewPager2Adapter 클래스를 만들었고 그 뷰페이저와 프래그먼트를 연동하여 셋팅해주는 코드이다

    binding.viewPagerContactActivitySwipe.apply {
            adapter = viewPager2Adapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            })
        }

ViewPager2Adapter 과 연결해주는 코드이다.

        TabLayoutMediator(binding.tabLayoutContactActivitySwitch, binding.viewPagerContactActivitySwipe) { tab, position ->
            Log.e("YMC", "ViewPager position: $position")
            when (position) {
                0 -> tab.text = "연락처"
                1 -> tab.text = "그룹"
                2 -> tab.text = "마이페이지"
            }
        }.attach()

ViewPager2와 TabLayout을 연결해주는 코드이다.


