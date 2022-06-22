package com.example.beadando.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(star: Star)

    @Update
    suspend fun update(star: Star)

    @Delete
    suspend fun delete(star: Star)

    @Query("SELECT * FROM star ORDER BY star_name ASC")
    fun getAll(): Flow<List<Star>>

    @Query("SELECT * from star WHERE id = :id")
    fun getStar(id: Int): Flow<Star>

}