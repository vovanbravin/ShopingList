package com.example.shopinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoping_list_item")
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "Name")
    val name: String,
    @ColumnInfo(name = "itemInfo")
    val itemInfo: String?,
    @ColumnInfo(name = "listId")
    val listId: Int,
    @ColumnInfo(name = "itemChecked")
    val itemChecked: Int = 0,
    @ColumnInfo(name = "itemType")
    val itemType: String = "item"
)