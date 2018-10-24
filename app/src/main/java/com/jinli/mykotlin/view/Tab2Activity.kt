package com.jinli.mykotlin.view

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.jinli.mykotlin.R
import com.jinli.mykotlin.utils.IScrollToTop
import com.jinli.mykotlin.view.fragment.OneFragment
import com.jinli.mykotlin.view.fragment.TwoFragment
import kotterknife.bindView

class Tab2Activity : BaseActivity() {

    private val mViewPager: ViewPager by bindView(R.id.view_pager)
    private val mTabLayout: TabLayout by bindView(R.id.tab_layout)

    private val mFragments by lazy { arrayOf<Fragment>(OneFragment(), TwoFragment()) }

    private val mTitles by lazy {
        arrayOf(
                getString(R.string.tab1).toUpperCase(),
                getString(R.string.tab2).toUpperCase()
        )
    }


    private val mFragmentAdapter: FragmentPagerAdapter by lazy {
        object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getPageTitle(position: Int): CharSequence {
                return mTitles[position]
            }

            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab2)
        initView()
    }

    private fun initView() {
        mViewPager.offscreenPageLimit = 2
        mViewPager.adapter = mFragmentAdapter
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (mFragments[mTabLayout.selectedTabPosition] is IScrollToTop) {
                    (mFragments[mTabLayout.selectedTabPosition] as IScrollToTop).scrollToTop()
                }
            }

        })
    }


}
