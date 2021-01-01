package com.shiryaev.schedule.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.FrPageScheduleBinding
import kotlin.properties.Delegates

class PageScheduleFragment : Fragment() {

    private var _binding: FrPageScheduleBinding? = null
    private val binding get() = _binding!!

    private var positionPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrPageScheduleBinding.inflate(inflater, container, false)
        arguments?.let {
            positionPage = it.getInt("position")
        }
        binding.pageTv.text = positionPage.toString()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}