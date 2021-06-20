package ru.shiryaev.schedule.common.adapters

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.shiryaev.domain.utils.UtilsKeys
import ru.shiryaev.schedule.ui.fragments.viewpager.PageScheduleFragment

class ViewPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private var mCountPage = 0
    private var mPage: String = ""

    override fun createFragment(position: Int) = PageScheduleFragment().apply {
        arguments = bundleOf(
            UtilsKeys.POSITION_PAGE.name to position,
            UtilsKeys.PAGE.name to mPage
        )
    }

    override fun getItemCount(): Int = mCountPage

    fun setCountPage(count: Int) {
        this.mCountPage = count
    }

    fun setPageTag(page: String) {
        mPage = page
    }
}