package com.dhruv.jokes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JokesDao {

    @Query("Select * from jokes_entity")
    suspend fun getJokesList(): List<JokesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(jokesEntity: JokesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJokesList(jokesEntity: List<JokesEntity>)

    @Delete
    suspend fun deleteJoke(jokesEntity: JokesEntity)

    @Query("Delete from jokes_entity")
    suspend fun deleteAllJokes()

}