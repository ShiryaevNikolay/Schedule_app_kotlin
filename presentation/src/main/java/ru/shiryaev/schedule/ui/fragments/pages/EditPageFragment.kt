package ru.shiryaev.schedule.ui.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.shiryaev.schedule.common.adapters.ViewPagerAdapter
import ru.shiryaev.schedule.databinding.FrEditPageBinding

class EditPageFragment: Fragment() {

    private var mCountPage = 6

    private var _binding: FrEditPageBinding? = null
    private val binding get() = _binding!!

    private var mVpAdapter: ViewPagerAdapter? = null

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

        return binding.root
    }

    companion object {
        val TAG = EditPageFragment::class.simpleName
    }
}