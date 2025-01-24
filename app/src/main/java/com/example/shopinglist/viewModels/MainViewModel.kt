package com.example.shopinglist.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopinglist.db.MainDatabase
import com.example.shopinglist.entities.NoteItem
import kotlinx.coroutines.launch

class MainViewModel(database: MainDatabase): ViewModel() {
    private val dao = database.getDao()
    var NoteList: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()

    fun insertNote(noteItem: NoteItem) = viewModelScope.launch{
        dao.addNote(noteItem)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    fun updateNote(noteItem: NoteItem) = viewModelScope.launch{
        dao.updateNote(noteItem)
    }


    class MainViewModelFactory(val database: MainDatabase): ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java))
            {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown cast")
        }
    }

}
