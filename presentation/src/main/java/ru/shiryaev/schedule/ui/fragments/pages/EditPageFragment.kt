package ru.shiryaev.schedule.ui.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.shiryaev.schedule.databinding.FrEditPageBinding

class EditPageFragment: Fragment() {

    private var _binding: FrEditPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrEditPageBinding.inflate(inflater, container, false)

        return binding.root
    }
}