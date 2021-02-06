package com.shiryaev.data.database.repository

import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.Storage
import com.shiryaev.data.database.note.NoteDao
import com.shiryaev.domain.models.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NoteRepository {

    private var mNoteDao: NoteDao

    @Inject
    lateinit var mStorage: Storage

    init {
        AppDelegate.getAppComponent().injectNoteRepository(this)
        mNoteDao = mStorage.getNoteDao()
    }

    fun getNotes() = mNoteDao.getAllNote()

    fun insertNote(note: Note) {
        Completable.fromRunnable { mNoteDao.insertNote(note) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun updateNote(note: Note) {
        Completable.fromRunnable { mNoteDao.updateNote(note) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun deleteNote(note: Note) {
        Completable.fromRunnable { mNoteDao.deleteNote(note) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}