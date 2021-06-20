package ru.shiryaev.schedule.ui.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.databinding.FrAddScheduleScreenBinding

class AddScheduleScreenFragment : Fragment() {

    private var _binding: FrAddScheduleScreenBinding? = null
    private val binding get() = _binding!!

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
        _binding = FrAddScheduleScreenBinding.inflate(inflater, container, false)

        binding.toolbar.setNavigationOnClickListener { navControllerMain!!.popBackStack() }

        return binding.root
    }
}