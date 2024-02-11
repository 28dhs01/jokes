package com.dhruv.jokes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruv.jokes.repos.JokesRepo
import com.dhruv.jokes.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(private val jokesRepo: JokesRepo): ViewModel() {
    fun getJokes(genre: String = "Programming", amount: Int = 10) {
        viewModelScope.launch {
            try {
                jokesRepo.getJokes(genre = genre, amount = amount)
            }catch (e: Exception){
                debugLog(e.message.toString())
            }
        }
    }
}