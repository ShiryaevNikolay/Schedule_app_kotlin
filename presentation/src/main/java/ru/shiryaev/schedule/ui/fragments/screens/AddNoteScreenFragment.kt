package ru.shiryaev.schedule.ui.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.shiryaev.schedule.databinding.FrAddNoteScreenBinding

class AddNoteScreenFragment : Fragment() {

    private var _binding: FrAddNoteScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrAddNoteScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        fun newInstance() : AddNoteScreenFragment {
            return AddNoteScreenFragment()
        }
    }
}