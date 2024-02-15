package com.dhruv.jokes.repos

import com.dhruv.jokes.data.local.JokesDb
import com.dhruv.jokes.data.local.JokesEntity
import com.dhruv.jokes.data.remote.ApiService
import com.dhruv.jokes.utils.debugLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JokesRepoImpl @Inject constructor(private val apiService: ApiService,private val jokesDb: JokesDb) : JokesRepo {
    override suspend fun getJokes(genre: String, amount: Int): Flow<List<JokesEntity>> {
        try {
            val response = apiService.getJokes(genre = genre, amount = amount)
            val jokesResponse = response.body()

            if (response.isSuccessful && jokesResponse != null) {
                val jokesEntityList = jokesResponse.jokes.map { joke ->
                    JokesEntity(
                        id = joke.id,
                        type = joke.type,
                        setup = joke.setup,
                        punchline = joke.punchline,
                        joke = joke.joke
                    )
                }
                jokesDb.jokesDao().insertJokesList(jokesEntityList)

            }
        } catch (e: Exception) {
            debugLog(e.message.toString())
        }
        return jokesDb.jokesDao().getJokesList()
    }

    override suspend fun updateBookmark(id: Int, bookmarked: Boolean) {
        jokesDb.jokesDao().updateBookmark(jokeId = id, isBookmarked = bookmarked)
    }
}