package com.shiryaev.schedule.tools.adapters

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shiryaev.schedule.ui.fragments.PageScheduleFragment
import com.shiryaev.schedule.utils.UtilsKeys

class ViewPagerAdapter(
        fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private var countPage = 0

    override fun createFragment(position: Int): Fragment = PageScheduleFragment().apply {
        arguments = bundleOf(
                UtilsKeys.POSITION_PAGE.key to position
        )
    }

    override fun getItemCount(): Int = countPage

    fun setCountPage(count: Int) {
        this.countPage = count
    }
}