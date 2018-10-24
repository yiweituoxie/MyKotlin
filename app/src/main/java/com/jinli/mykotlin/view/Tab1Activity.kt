package com.jinli.mykotlin.view

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.jinli.mykotlin.R
import com.jinli.mykotlin.utils.IScrollToTop
import com.jinli.mykotlin.view.fragment.OneFragment
import com.jinli.mykotlin.view.fragment.TwoFragment
import kotterknife.bindView

class Tab1Activity : BaseActivity() {

    private val mBottomBar: AHBottomNavigation by bindView(R.id.bottom_navigation_bar)

    private lateinit var mFragments: Array<Fragment>

    private var mCurrentFragment: Fragment? = null

    private var mAHBottomNavigationAdapter: AHBottomNavigationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab1)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        mFragments = arrayOf(OneFragment(), TwoFragment())

        if (savedInstanceState != null) {  // “内存重启”时调用
            supportFragmentManager.fragments.forEach {
                supportFragmentManager.beginTransaction().remove(it).commit()
            }
        }

        mBottomBar.isForceTint = true
        mBottomBar.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        mBottomBar.inactiveColor = Color.parseColor("#80ffffff")
        mBottomBar.accentColor = ContextCompat.getColor(this, R.color.common_red)
//        mBottomBar.setColoredModeColors(ContextCompat.getColor(this, R.color.primaryRed), Color.parseColor("#7F7F7F"))
        //tab切换事件，保存fragment状态
        mBottomBar.setOnTabSelectedListener { position, wasSelected ->
            if (mCurrentFragment != null && wasSelected) {
                if (mCurrentFragment is IScrollToTop) {
                    (mCurrentFragment as IScrollToTop).scrollToTop()
                }
                return@setOnTabSelectedListener true
            }
            val transaction = supportFragmentManager.beginTransaction()
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment)
            }
            mFragments[position]
            mCurrentFragment = mFragments[position]
            if (mFragments[position].isAdded) {
                transaction.show(mFragments[position])
            } else {
                transaction.add(R.id.layout_content, mFragments[position])
            }
            transaction.commit()
            true
        }

        mAHBottomNavigationAdapter = AHBottomNavigationAdapter(this, R.menu.menu_main_bottom)
        mAHBottomNavigationAdapter?.setupWithBottomNavigation(mBottomBar, IntArray(mFragments.size, { Color.parseColor("#212121") }))

        mBottomBar.currentItem = mBottomBar.currentItem
    }


}
