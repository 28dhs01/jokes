package com.dhruv.jokes.repos

import com.dhruv.jokes.data.local.JokesEntity
import kotlinx.coroutines.flow.Flow


interface JokesRepo {
    suspend fun getJokes(genre: String,amount: Int): Flow<List<JokesEntity>>
    suspend fun updateBookmark(id: Int, bookmarked: Boolean)
    fun getBookmarksOnly(): Flow<List<JokesEntity>>
    suspend fun deleteUnbookmarkedJokes()
    suspend fun deleteJoke(id: Int)
}