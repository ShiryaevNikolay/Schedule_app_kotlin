package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.viewmodels.CalendarViewModel
import com.shiryaev.schedule.databinding.FrHomeCalendarBinding

class CalendarFragment : Fragment() {

    private var _binding: FrHomeCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var mNavController: NavController
    private lateinit var mViewModel: CalendarViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel = ViewModelProvider(this, CustomFactory(CalendarViewModel())).get(CalendarViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrHomeCalendarBinding.inflate(inflater, container, false)

//        // Синхронизируем xml с viewModel
//        with(binding) {
//            vm = mViewModel
//            lifecycleOwner = this@CalendarFragment
//        }

        mNavController = NavHostFragment.findNavController(this)

        initView()

        binding.topBarCalendar.getHeight = { heightTopBar ->
            val params = CoordinatorLayout.LayoutParams(
                    CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                    CoordinatorLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = heightTopBar
            }
            binding.calendar.layoutParams = params
        }

        binding.calendar.setOnMonthChangedListener { widget, date ->
            binding.topBarCalendar.apply {
                setMonth(date.month - 1)
            }
        }

        binding.calendar.setOnDateChangedListener { widget, date, selected ->
            binding.topBarCalendar.apply {
                setYear(date.year)
            }
        }

//        binding.topBarCalendar.onShowSchedule = { mNavController.popBackStack() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.topBarCalendar.apply {
            setYear(binding.calendar.currentDate.year)
            setMonth(binding.calendar.currentDate.month - 1)
        }
        binding.calendar.apply{
            topbarVisible = false
            selectedDate = CalendarDay.today()
        }
    }
}