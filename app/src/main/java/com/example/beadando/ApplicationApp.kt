package com.example.beadando

import android.app.Application
import com.example.beadando.data.StarDatabase

class StargazingApp: Application() {
    val database: StarDatabase by lazy { StarDatabase.getDatabase(this) }
}