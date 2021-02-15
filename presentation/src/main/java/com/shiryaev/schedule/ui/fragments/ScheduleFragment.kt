package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.viewmodels.ScheduleViewModel
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.FrHomeScheduleBinding
import com.shiryaev.schedule.tools.adapters.ViewPagerAdapter
import com.shiryaev.schedule.ui.views.utils.*
import java.util.*

class ScheduleFragment : Fragment() {

    companion object {
        val TAG = ScheduleFragment::class.simpleName
    }

    private var mCountPage = 0
    private var _binding: FrHomeScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var mVpAdapter: ViewPagerAdapter
    private lateinit var mViewModel: ScheduleViewModel
    private lateinit var mNavController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCountPage = context.resources.getStringArray(R.array.days_of_week).size
        mViewModel = ViewModelProvider(this, CustomFactory(ScheduleViewModel())).get(ScheduleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVpAdapter = ViewPagerAdapter(this@ScheduleFragment).apply {
            // Устанавливаем колличество страниц viewPage2
            setCountPage(mCountPage)
            TAG?.let { setScreenTag(it) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding  = FrHomeScheduleBinding.inflate(inflater, container, false)

        mNavController = NavHostFragment.findNavController(this)

        initViewPager(binding)

        binding.topBarHome.onChangeCurrentItem = { selectedTab ->
            binding.homeScreenVp.currentItem = selectedTab
        }

        binding.topBarHome.onChangeHeight = { height ->
            mViewModel.setHeightTopBar(height)
        }

        binding.topBarHome.onShowCalendar = {
            mNavController.navigate(R.id.action_homeScheduleFragment_to_calendarFragment)
        }

        setCurrentDay()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getViewModel() = mViewModel

    private fun initViewPager(_binding: FrHomeScheduleBinding) {
        with (_binding.homeScreenVp) {
            adapter = mVpAdapter
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.topBarHome.setSelectedTab(position)
                }
            })
        }
    }

    private fun setCurrentDay() {
        // Устанавливаем сегодняшний день
        binding.topBarHome.setSelectedTab(when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> {
                binding.homeScreenVp.currentItem = 0
                0
            }
            Calendar.TUESDAY -> {
                binding.homeScreenVp.currentItem = 1
                1
            }
            Calendar.WEDNESDAY -> {
                binding.homeScreenVp.currentItem = 2
                2
            }
            Calendar.THURSDAY -> {
                binding.homeScreenVp.currentItem = 3
                3
            }
            Calendar.FRIDAY -> {
                binding.homeScreenVp.currentItem = 4
                4
            }
            Calendar.SATURDAY -> {
                binding.homeScreenVp.currentItem = 5
                5
            }
            Calendar.SUNDAY -> {
                binding.homeScreenVp.currentItem = 6
                6
            }
            else -> {
                binding.homeScreenVp.currentItem = 7
                0
            }
        })
    }
}