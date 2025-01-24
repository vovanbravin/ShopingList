package com.example.shopinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shopinglist.dao.Dao
import com.example.shopinglist.entities.LibraryItem
import com.example.shopinglist.entities.NoteItem
import com.example.shopinglist.entities.ShoppingListItem
import com.example.shopinglist.entities.ShoppingListNames

@Database(entities = [NoteItem::class, ShoppingListItem:: class, LibraryItem:: class, ShoppingListNames:: class], version = 1)
abstract class MainDatabase: RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        @Volatile
        private var INSTANCE: MainDatabase? = null
        fun getDb(context: Context): MainDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context,
                    MainDatabase::class.java,
                    "db"
                ).build()
                instance
            }
        }

    }
}