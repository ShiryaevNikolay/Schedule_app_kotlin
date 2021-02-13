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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.viewmodels.NoteViewModel
import com.shiryaev.schedule.databinding.FrHomeCalendarBinding

class CalendarFragment : Fragment() {

    private var _binding: FrHomeCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var mNavController: NavController
    private lateinit var mViewModel: NoteViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel = ViewModelProvider(this, CustomFactory(NoteViewModel())).get(NoteViewModel::class.java)
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


        with (mViewModel) {
            // Получаем список заметок
            getNotes().observe(viewLifecycleOwner) { notes ->
                mViewModel.setIsErrorVisible(binding.noteBottomSheet.setNoteList(notes))
            }
            // Проверяем progress
            getIsLoading().observe(viewLifecycleOwner) { value ->
                binding.noteBottomSheet.setLoading(value)
            }
            // Проверяем список на пустоту
            getIsErrorVisible().observe(viewLifecycleOwner) { value ->
                binding.noteBottomSheet.setLoading(false)
                binding.noteBottomSheet.setInfoError(value)
            }
        }


        binding.topBarCalendar.getHeight = { heightTopBar ->
            val params = CoordinatorLayout.LayoutParams(
                    CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                    CoordinatorLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = heightTopBar
            }
            binding.calendar.layoutParams = params
        }

        binding.noteBottomSheet.onChangeState = { state ->
            when (state) {
                BottomSheetBehavior.STATE_COLLAPSED -> { changeCalendarMode(CalendarMode.MONTHS) }
                BottomSheetBehavior.STATE_EXPANDED -> { changeCalendarMode(CalendarMode.WEEKS) }
            }
        }

        binding.calendar.setOnMonthChangedListener { widget, date ->
            setDateToTopBar(date.year, date.month - 1)
        }

        binding.calendar.setOnDateChangedListener { widget, date, selected ->

        }

//        binding.topBarCalendar.onShowSchedule = { mNavController.popBackStack() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.calendar.apply {
            topbarVisible = false
            selectedDate = CalendarDay.today()
            setDateToTopBar(currentDate.year, currentDate.month - 1)
        }
    }

    private fun setDateToTopBar(year: Int, month: Int) {
        binding.topBarCalendar.apply {
            setYear(year)
            setMonth(month)
        }
    }

    private fun changeCalendarMode(mode: CalendarMode) {
        binding.calendar.newState()
                .setShowWeekDays(true)
                .setCalendarDisplayMode(mode)
                .commit()
    }
}