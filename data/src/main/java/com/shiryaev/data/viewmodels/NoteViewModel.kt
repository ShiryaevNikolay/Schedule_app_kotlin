package com.shiryaev.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.repository.NoteRepository
import com.shiryaev.domain.models.Note
import javax.inject.Inject

class NoteViewModel : ViewModel() {

    @Inject
    lateinit var mRepository: NoteRepository

    private val mIsLoading = MutableLiveData<Boolean>()
    private val mIsErrorVisible = MutableLiveData<Boolean>()

    init {
        AppDelegate.getAppComponent().injectNoteViewModel(this)
    }

    fun getIsLoading() = mIsLoading

    fun getIsErrorVisible() = mIsErrorVisible

    fun setIsErrorVisible(value: Boolean) { mIsErrorVisible.value = value }

    fun setIsLoading(value: Boolean) { mIsLoading.postValue(value) }

    fun getNotes() = mRepository.getNotes()

    fun getNotesByDate(date: String) = mRepository.getNotesByDate(date)

    fun insertNote(note: Note) { mRepository.insertNote(note) }

    fun updateNote(note: Note) { mRepository.updateNote(note) }

    fun deleteNote(note: Note) { mRepository.deleteNote(note) }
}