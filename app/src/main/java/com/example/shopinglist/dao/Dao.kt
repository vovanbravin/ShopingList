package com.example.shopinglist.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shopinglist.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Insert
    suspend fun addNote(noteItem: NoteItem)

    @Query("DELETE FROM note_list WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Update
    suspend fun updateNote(noteItem: NoteItem)

}