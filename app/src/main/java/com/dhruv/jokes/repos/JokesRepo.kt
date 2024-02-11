package com.dhruv.jokes.repos

import com.dhruv.jokes.data.model.JokeOrSetup

interface JokesRepo {
    suspend fun getJokes(genre: String,amount: Int): List<JokeOrSetup>
}