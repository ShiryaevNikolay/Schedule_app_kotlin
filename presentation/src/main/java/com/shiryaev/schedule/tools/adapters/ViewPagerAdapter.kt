package com.shiryaev.schedule.tools.adapters

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shiryaev.schedule.ui.fragments.PageScheduleFragment
import com.shiryaev.domain.utils.UtilsKeys

class ViewPagerAdapter(
        fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private var mCountPage = 0
    private var mScreen: String = ""

    override fun createFragment(position: Int) = PageScheduleFragment().apply {
        arguments = bundleOf(
                UtilsKeys.POSITION_PAGE.name to position,
                UtilsKeys.SCREEN.name to getScreenTag()
        )
    }

    override fun getItemCount(): Int = mCountPage

    fun setCountPage(count: Int) {
        this.mCountPage = count
    }

    fun setScreenTag(screen: String) {
        mScreen = screen
    }

    private fun getScreenTag() = mScreen
}