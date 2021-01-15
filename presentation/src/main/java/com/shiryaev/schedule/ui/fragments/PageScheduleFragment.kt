package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.viewmodels.PageScheduleViewModel
import com.shiryaev.domain.models.Schedule
import com.shiryaev.schedule.databinding.FrPageScheduleBinding
import com.shiryaev.schedule.common.controllers.ItemScheduleController
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.schedule.R
import com.shiryaev.schedule.ui.dialogs.ListDialog
import com.shiryaev.schedule.ui.views.utils.SpaceFirstItemDecoration
import com.shiryaev.schedule.utils.UtilsListData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.custom_layout_dialog.view.*
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import java.util.ArrayList

class PageScheduleFragment : Fragment() {

    private var positionPage = 0

    private var _binding: FrPageScheduleBinding? = null
    private val binding get() = _binding!!

    private val mEasyAdapter = EasyAdapter()

    private lateinit var mContext: Context
    private lateinit var mItemSchedule: ItemScheduleController
    private lateinit var mViewModel: PageScheduleViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mViewModel = ViewModelProvider(this, CustomFactory(PageScheduleViewModel())).get(PageScheduleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            positionPage = it.getInt(UtilsKeys.POSITION_PAGE.key)
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

        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mItemSchedule = ItemScheduleController({ schedule ->
            // TODO: Детальный показ занятия при нажатии на карточку
        }, { schedule ->
            ListDialog { positionItem ->
                actionSchedule(schedule, positionItem)
            }.apply {
                setData(UtilsListData.getListScheduleDialog(mContext))
            }.show(childFragmentManager, null)
        })

        mViewModel.getSchedules(positionPage).observe(viewLifecycleOwner, { listSchedules ->
            setListToAdapter(ArrayList(listSchedules))
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = mEasyAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceFirstItemDecoration(60))
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
            // Удаление занятия
            arrayAction.last() -> mViewModel.deleteSchedule(schedule)
        }
    }
}