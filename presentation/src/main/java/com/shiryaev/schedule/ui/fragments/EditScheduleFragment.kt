package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.FrEditScheduleBinding
import com.shiryaev.schedule.tools.adapters.ViewPagerAdapter
import com.shiryaev.schedule.ui.views.CustomTabLayout

class EditScheduleFragment : Fragment() {

    private var countPage = 0
    private var _binding: FrEditScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var vpAdapter: ViewPagerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        countPage = context.resources.getStringArray(R.array.days_of_week).size - 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vpAdapter = ViewPagerAdapter(this@EditScheduleFragment).apply {
            // Устанавливаем колличество страниц viewPage2
            setCountPage(countPage)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding  = FrEditScheduleBinding.inflate(inflater, container, false)

        initTabLayout(binding.customTabLayout)
        initViewPager(binding)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTabLayout(tabLayout: CustomTabLayout) {
        tabLayout.run {
            setCountTab(countPage)
            setSelectedPage = { selectedTab ->
                binding.homeScreenVp.currentItem = selectedTab
            }
        }
    }

    private fun initViewPager(_binding: FrEditScheduleBinding) {
        _binding.homeScreenVp.apply {
            adapter = vpAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.customTabLayout.setSelectedTab(position)
                }
            })
        }
    }
}