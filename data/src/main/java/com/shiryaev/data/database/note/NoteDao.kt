package com.shiryaev.data.database.note

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shiryaev.domain.models.Note
import com.shiryaev.domain.utils.UtilsTable

@Dao
interface NoteDao {
    @Query("SELECT * FROM ${UtilsTable.TABLE_NOTE}")
    fun getAllNote(): LiveData<List<Note>>

    @Query("SELECT * FROM ${UtilsTable.TABLE_NOTE} WHERE ${UtilsTable.NOTE_DEADLINE} = :date")
    fun getAllNoteByDate(date: String): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}