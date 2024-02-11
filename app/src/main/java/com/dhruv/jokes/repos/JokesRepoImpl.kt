package com.dhruv.jokes.repos

import com.dhruv.jokes.data.model.JokeOrSetup
import com.dhruv.jokes.data.remote.ApiService
import javax.inject.Inject

class JokesRepoImpl @Inject constructor(private val apiService: ApiService): JokesRepo {
    override suspend fun getJokes(genre: String, amount: Int): List<JokeOrSetup> {
        val response = apiService.getJokes(genre = genre, amount = amount)
        val jokesResponse = response.body()
        if (response.isSuccessful && jokesResponse != null) {
            return jokesResponse.jokes
        }
        return emptyList()
    }
}