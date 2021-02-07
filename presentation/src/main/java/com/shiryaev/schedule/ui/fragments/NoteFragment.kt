package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.common.Transfer
import com.shiryaev.data.viewmodels.NoteViewModel
import com.shiryaev.domain.utils.UtilsIntent
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.schedule.R
import com.shiryaev.schedule.common.controllers.ItemNoteController
import com.shiryaev.schedule.databinding.FrNoteBinding
import com.shiryaev.schedule.ui.AddNoteActivity
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class NoteFragment : Fragment(), View.OnClickListener {

    private var _binding: FrNoteBinding? = null
    private val binding get() = _binding!!

    private val mEasyAdapter = EasyAdapter()
    private lateinit var mViewModel: NoteViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel = ViewModelProvider(this, CustomFactory(NoteViewModel())).get(NoteViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FrNoteBinding.inflate(inflater, container, false)

        // Синхронизируем xml с viewModel
        with(binding) {
            vm = mViewModel
            lifecycleOwner = this@NoteFragment
        }

        with (mViewModel) {
            // Получаем список заметок
            getNotes().observe(viewLifecycleOwner) { notes ->
                val itemNote = ItemNoteController()
                val itemList = ItemList.create().apply {
                    addAll(notes, itemNote)
                }
                mEasyAdapter.setItems(itemList)
                mViewModel.setIsErrorVisible(mEasyAdapter.itemCount == 0)
            }
        }

        initRecyclerView()

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

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = mEasyAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }
}