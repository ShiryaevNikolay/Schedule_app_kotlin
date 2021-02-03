package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.common.Transfer
import com.shiryaev.data.viewmodels.PageScheduleViewModel
import com.shiryaev.domain.models.Schedule
import com.shiryaev.domain.models.Week
import com.shiryaev.domain.utils.UtilsIntent
import com.shiryaev.schedule.databinding.FrPageScheduleBinding
import com.shiryaev.schedule.common.controllers.ItemScheduleController
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.domain.utils.UtilsTable
import com.shiryaev.schedule.R
import com.shiryaev.schedule.ui.AddScheduleActivity
import com.shiryaev.schedule.ui.dialogs.ListDialog
import com.shiryaev.schedule.ui.views.utils.SpaceFirstItemDecoration
import com.shiryaev.schedule.utils.UtilsListData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import java.util.ArrayList

class PageScheduleFragment : Fragment() {

    private var mPositionPage = 0
    private var mListWeek: List<Week> = listOf()

    private var mDecorator = SpaceFirstItemDecoration()

    private var _binding: FrPageScheduleBinding? = null
    private val binding get() = _binding!!

    private val mEasyAdapter = EasyAdapter()

    private lateinit var mContext: Context
    private lateinit var mViewModel: PageScheduleViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mViewModel = ViewModelProvider(this, CustomFactory(PageScheduleViewModel())).get(PageScheduleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mPositionPage = it.getInt(UtilsKeys.POSITION_PAGE.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrPageScheduleBinding.inflate(inflater, container, false)

        // Синхронизируем xml с viewModel
        with(binding) {
            vm = mViewModel
            lifecycleOwner = this@PageScheduleFragment
        }

        mViewModel.getWeeks().observe(viewLifecycleOwner) { listWeek ->
            mListWeek = listWeek
        }

        initRecyclerView()


        when (parentFragment) {
            is HomeScheduleFragment -> {
                (parentFragment as HomeScheduleFragment).getViewModel().getHeightTopBar().observe(viewLifecycleOwner) { height ->
                    setHeightDecoration(height)
                }
                PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext.resources?.getString(R.string.current_week_key), "")?.let {
                    mViewModel.getSchedules(mPositionPage, it).observe(viewLifecycleOwner) { listSchedules ->
                        setListToAdapter(ArrayList(listSchedules))
                    }
                }
            }
            is EditScheduleFragment -> {
//                (parentFragment as EditScheduleFragment)
                mViewModel.getSchedules(mPositionPage).observe(viewLifecycleOwner, { listSchedules ->
                    setListToAdapter(ArrayList(listSchedules))
                })
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = mEasyAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setHeightDecoration(height: Int) {
        binding.recyclerView.apply {
            removeItemDecoration(mDecorator)
            mDecorator = SpaceFirstItemDecoration(height)
            addItemDecoration(mDecorator)
        }
    }

    private fun setListToAdapter(list: ArrayList<Schedule>) {
        Observable.fromCallable {
            if (list.isNotEmpty()) { getListScheduleByDay(list) }
            else { ArrayList() }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mViewModel.setIsLoading(true)
                    mViewModel.setIsErrorVisible(false)
                }
                .doFinally { mViewModel.setIsLoading(false) }
                .subscribe { newList ->
                    val mItemSchedule = ItemScheduleController(mListWeek, { schedule ->
                        // TODO: Детальный показ занятия при нажатии на карточку
                    }, { schedule -> showListDialog(schedule) })
                    val listSchedule = ItemList.create().apply {
                        addAll(newList, mItemSchedule.apply { setCountItem(newList.size) })
                    }
                    mEasyAdapter.setItems(listSchedule)
                    mViewModel.setIsErrorVisible(mEasyAdapter.itemCount == 0)
                }
    }

    private fun getListScheduleByDay(list: ArrayList<Schedule>): ArrayList<ArrayList<Schedule>> {
        val newListSchedules: ArrayList<ArrayList<Schedule>> = arrayListOf(arrayListOf())
        var timeTemp = list.first().mTimeStart
        for (item in list) {
            if (item.mTimeStart != timeTemp) {
                newListSchedules.add(arrayListOf())
                timeTemp = item.mTimeStart
            }
            newListSchedules[newListSchedules.size - 1].add(item)
        }
        return newListSchedules
    }

    private fun actionSchedule(schedule: Schedule, action: Int) {
        val arrayAction = mContext.resources.getStringArray(R.array.dialog_schedule)
        when(arrayAction[action]) {
            arrayAction.first() -> run {
                val options = Bundle().apply {
                    putString(UtilsKeys.REQUEST_CODE.name, UtilsIntent.EDIT_LESSON.name)
                    putInt(UtilsKeys.POSITION_PAGE.name, mPositionPage)
                    putSerializable(UtilsTable.SCHEDULE, schedule)
                }
                Transfer.transferToActivity(activity as AppCompatActivity, AddScheduleActivity(), options)
            }
            // Удаление занятия
            arrayAction.last() -> mViewModel.deleteSchedule(schedule)
        }
    }

    private fun showListDialog(schedule: Schedule) {
        ListDialog()
            .setData(UtilsListData.getListScheduleDialog(mContext)) { positionItem ->
                actionSchedule(schedule, positionItem)
            }
            .show(childFragmentManager, null)
    }
}