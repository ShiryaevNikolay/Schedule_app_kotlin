package ru.shiryaev.schedule.ui.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayoutMediator
import ru.shiryaev.domain.utils.UtilsKeys
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.common.adapters.ViewPagerAdapter
import ru.shiryaev.schedule.common.navigation.CreateScheduleFragmentRoute
import ru.shiryaev.schedule.common.navigation.FragmentClass
import ru.shiryaev.schedule.common.navigation.NavigationFragment
import ru.shiryaev.schedule.databinding.FrEditPageBinding

class EditPageFragment: Fragment(), View.OnClickListener {

    private var mCountPage = 6

    private var _binding: FrEditPageBinding? = null
    private val binding get() = _binding!!

    private var mVpAdapter: ViewPagerAdapter? = null
    private var navControllerMain: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navControllerMain = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrEditPageBinding.inflate(inflater, container, false)

        mVpAdapter = ViewPagerAdapter(this@EditPageFragment).apply {
            // Устанавливаем кол-во страниц viewPager2
            setCountPage(mCountPage)
            TAG?.let { setPageTag(it) }
        }

        binding.viewPager.adapter = mVpAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // Some implementation
        }.attach()

        binding.fab.setOnClickListener(this)

        return binding.root
    }

    companion object {
        val TAG = EditPageFragment::class.simpleName
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.fab -> {
                val bundle = bundleOf(
                    UtilsKeys.REQUEST_CODE.name to FragmentClass.CREATE_SCHEDULE.name,
                    UtilsKeys.POSITION_PAGE.name to binding.viewPager.currentItem
                )
                NavigationFragment.navigate(
                    navControllerMain!!,
                    CreateScheduleFragmentRoute(FragmentClass.CREATE_SCHEDULE),
                    bundle
                )
            }
        }
    }
}