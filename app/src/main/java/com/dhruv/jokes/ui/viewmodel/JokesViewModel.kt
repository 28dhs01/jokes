package com.dhruv.jokes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruv.jokes.data.model.JokeOrSetup
import com.dhruv.jokes.repos.JokesRepo
import com.dhruv.jokes.ui.screens.UiState
import com.dhruv.jokes.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(private val jokesRepo: JokesRepo): ViewModel() {

    private val _jokes: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val jokes = _jokes.asStateFlow()
    fun getJokes(genre: String = "Programming", amount: Int = 10) {
        viewModelScope.launch {
            _jokes.value = UiState.Loading
            try {
                val jokesFromRepo = jokesRepo.getJokes(genre = genre, amount = amount)
                _jokes.value = UiState.Success(jokesFromRepo)
            }catch (e: Exception){
                _jokes.value = UiState.Error(e.message.toString())
                debugLog(e.message.toString())
            }
        }
    }
}