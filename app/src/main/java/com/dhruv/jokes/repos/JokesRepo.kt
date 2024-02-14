package com.dhruv.jokes.repos

import android.content.Context
import com.dhruv.jokes.data.local.JokesEntity


interface JokesRepo {
    suspend fun getJokes(genre: String,amount: Int): List<JokesEntity>
}