package com.dhruv.jokes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JokesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(jokesEntity: JokesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJokesList(jokesEntity: List<JokesEntity>)

    @Query("Select * from jokes_entity order by id desc")
    fun getJokesList(): Flow<List<JokesEntity>>

    @Query(" Update jokes_entity set isBookmarked = :isBookmarked where id = :jokeId")
    suspend fun updateBookmark(jokeId: Int, isBookmarked: Boolean)

    @Query ("Select * from jokes_entity where isBookmarked = 1")
    fun getBookmarksOnly(): Flow<List<JokesEntity>>

    @Delete
    suspend fun deleteJoke(jokesEntity: JokesEntity)

    @Query("Delete from jokes_entity where isBookmarked = 0")
    suspend fun deleteUnbookmarkedJokes()

}