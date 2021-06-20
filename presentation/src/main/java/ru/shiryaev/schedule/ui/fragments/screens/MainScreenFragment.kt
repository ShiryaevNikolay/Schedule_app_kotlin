package ru.shiryaev.schedule.ui.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.databinding.FrMainScreenBinding

class MainScreenFragment : Fragment() {

    private var _binding: FrMainScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrMainScreenBinding.inflate(inflater, container, false)

        val host = childFragmentManager.findFragmentById(R.id.main_screen_nhf) as NavHostFragment
        val navController = host.navController
        binding.mainScreenBnv.setupWithNavController(navController)

        return binding.root
    }
}