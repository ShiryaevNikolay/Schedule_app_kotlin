package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.common.Transfer
import com.shiryaev.data.viewmodels.HomeScheduleViewModel
import com.shiryaev.domain.utils.UtilsIntent
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.FrEditScheduleBinding
import com.shiryaev.schedule.tools.adapters.ViewPagerAdapter
import com.shiryaev.schedule.ui.AddScheduleActivity
import com.shiryaev.schedule.ui.views.CustomTabLayout

class EditScheduleFragment : Fragment(), View.OnClickListener {

    private var mCountPage = 0
    private var _binding: FrEditScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var vpAdapter: ViewPagerAdapter
    private lateinit var mViewModel: HomeScheduleViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCountPage = context.resources.getStringArray(R.array.days_of_week).size - 1
        mViewModel = ViewModelProvider(this, CustomFactory(HomeScheduleViewModel())).get(HomeScheduleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vpAdapter = ViewPagerAdapter(this@EditScheduleFragment).apply {
            // Устанавливаем колличество страниц viewPage2
            setCountPage(mCountPage)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding  = FrEditScheduleBinding.inflate(inflater, container, false)

        initViewPager(binding)

        binding.topBarEdit.onChangeCurrentItem = { selectedTab ->
            binding.homeScreenVp.currentItem = selectedTab
        }

        binding.topBarEdit.onChangeHeight = { height ->
            mViewModel.setHeightTopBar(height)
        }

        binding.fab.setOnClickListener(this)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.fab -> run {
                val options = Bundle().apply {
                    putString(UtilsKeys.REQUEST_CODE.name, UtilsIntent.CREATE_LESSON.name)
                    putInt(UtilsKeys.POSITION_PAGE.name, binding.homeScreenVp.currentItem)
                }
                Transfer.transferToActivity(activity as AppCompatActivity, AddScheduleActivity(), options)
            }
        }
    }

    fun getViewModel() = mViewModel

    private fun initViewPager(_binding: FrEditScheduleBinding) {
        _binding.homeScreenVp.apply {
            adapter = vpAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.topBarEdit.setSelectedTab(position)
                }
            })
        }
    }
}