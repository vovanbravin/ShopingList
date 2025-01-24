package com.example.shopinglist.activities

import android.app.Application
import com.example.shopinglist.db.MainDatabase

class MainApp : Application() {
    val database by lazy { MainDatabase.getDb(this) }
}