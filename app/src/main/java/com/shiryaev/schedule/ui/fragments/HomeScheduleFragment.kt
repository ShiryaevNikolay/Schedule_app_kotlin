package com.shiryaev.schedule.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.FrHomeScheduleBinding
import com.shiryaev.schedule.tools.adapters.ViewPagerAdapter

class HomeScheduleFragment : Fragment() {

    private var _binding: FrHomeScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var vpAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vpAdapter = ViewPagerAdapter(this@HomeScheduleFragment).apply {
            // Устанавливаем колличество страниц viewPage2
            context?.resources?.getStringArray(R.array.days_of_week)?.let { setCountPage(it.size) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding  = FrHomeScheduleBinding.inflate(inflater, container, false)

        initViewPager(binding)

        binding.customTabLayout.setSelectedPage = { selectedTab ->
            binding.homeScreenVp.currentItem = selectedTab
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}