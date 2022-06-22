package com.example.beadando.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Star")
data class Star(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "star_name")
    val starName: String,
    @ColumnInfo(name = "watch_tower")
    val watchTower: Int,
    @ColumnInfo(name = "star_comment")
    val starComment : String,
)