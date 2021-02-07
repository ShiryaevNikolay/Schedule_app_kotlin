package com.shiryaev.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.repository.NoteRepository
import com.shiryaev.domain.models.Note
import javax.inject.Inject

class AddNoteViewModel : ViewModel() {

    @Inject
    lateinit var mRepository: NoteRepository

    private val mFabIsVisible = MutableLiveData<Boolean>()

    init {
        AppDelegate.getAppComponent().injectAddNoteViewModel(this)
    }

    fun getListLessons() = mRepository.getLessons()

    fun getDeadline() = mRepository.getDeadline()

    fun setFabIsVisible(value: Boolean) { mFabIsVisible.value = value }

    fun getFabIsVisible() = mFabIsVisible

    fun insertNote(note: Note) { mRepository.insertNote(note) }

    fun updateNote(note: Note) { mRepository.updateNote(note) }
}