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
import com.shiryaev.data.viewmodels.ScheduleViewModel
import com.shiryaev.domain.utils.UtilsIntent
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.FrEditScheduleBinding
import com.shiryaev.schedule.tools.adapters.ViewPagerAdapter
import com.shiryaev.schedule.ui.AddScheduleActivity
import kotlinx.android.synthetic.main.fr_edit_schedule.view.*

class EditScheduleFragment : Fragment(), View.OnClickListener {

    companion object {
        val TAG = EditScheduleFragment::class.simpleName
    }

    private var mCountPage = 0
    private var _binding: FrEditScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var mVpAdapter: ViewPagerAdapter
    private lateinit var mViewModel: ScheduleViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCountPage = context.resources.getStringArray(R.array.days_of_week).size - 1
        mViewModel = ViewModelProvider(this, CustomFactory(ScheduleViewModel())).get(ScheduleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVpAdapter = ViewPagerAdapter(this@EditScheduleFragment).apply {
            // Устанавливаем колличество страниц viewPage2
            setCountPage(mCountPage)
            TAG?.let { setScreenTag(it) }
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
            adapter = mVpAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.topBarEdit.setSelectedTab(position)
                }
            })
        }
    }
}