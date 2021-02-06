package com.shiryaev.schedule.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.shiryaev.data.common.Transfer
import com.shiryaev.domain.utils.UtilsIntent
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.FrNoteBinding
import com.shiryaev.schedule.ui.AddNoteActivity

class NoteFragment : Fragment(), View.OnClickListener {

    private var _binding: FrNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FrNoteBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener(this)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> {
                val options = Bundle().apply {
                    putString(UtilsKeys.REQUEST_CODE.name, UtilsIntent.CREATE_NOTE.name)
                }
                Transfer.transferToActivity(activity as AppCompatActivity, AddNoteActivity(), options)
            }
        }
    }
}