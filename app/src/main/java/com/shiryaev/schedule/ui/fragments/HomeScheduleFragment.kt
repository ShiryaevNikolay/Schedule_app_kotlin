package com.shiryaev.schedule.ui.fragments

import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.FrHomeScheduleBinding
import com.shiryaev.schedule.tools.adapters.ViewPagerAdapter
import com.shiryaev.schedule.ui.views.CustomTabLayout
import com.shiryaev.schedule.ui.views.utils.*

class HomeScheduleFragment : Fragment(), View.OnClickListener {

    private var countPage = 0
    private var showCalendar = false
    private var showTabs = false
    private var _binding: FrHomeScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var vpAdapter: ViewPagerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        countPage = context.resources.getStringArray(R.array.days_of_week).size
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vpAdapter = ViewPagerAdapter(this@HomeScheduleFragment).apply {
            // Устанавливаем колличество страниц viewPage2
            setCountPage(countPage)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding  = FrHomeScheduleBinding.inflate(inflater, container, false)

        initTabLayout(binding.customTabLayout)
        initViewPager(binding)


        binding.apply {
            topBarShowTabsBtn.setOnClickListener(this@HomeScheduleFragment)
            topBarTitle.setOnClickListener(this@HomeScheduleFragment)
            changeViewBtn.setOnClickListener(this@HomeScheduleFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.top_bar_show_tabs_btn, R.id.top_bar_title -> {
                context?.let { showTabs = showTabs(it, binding.topBarShowTabsBtn, !showTabs) }
                binding.customTabLayout.isVisible = showTabs
            }
            R.id.change_view_btn -> {
                context?.let { showCalendar = changeIconCalendar(it, binding.changeViewBtn, !showCalendar) }
            }
        }
    }

    private fun initTabLayout(tabLayout: CustomTabLayout) {
        tabLayout.run {
            setCountTab(countPage)
            setSelectedPage = { selectedTab ->
                binding.homeScreenVp.currentItem = selectedTab
            }
            getSelectedTab = { selectedTab ->
                setTextTitle(selectedTab)
            }
        }
    }

    private fun initViewPager(_binding: FrHomeScheduleBinding) {
        _binding.homeScreenVp.apply {
            adapter = vpAdapter
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.customTabLayout.setSelectedTab(position)
                }
            })
        }
    }

    private fun setTextTitle(position: Int) {
        val arrayTextTitle = context?.resources?.getStringArray(R.array.full_days_of_week)
        binding.topBarTitle.text = arrayTextTitle?.get(position)
    }
}