package com.dhruv.jokes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _bookmarkedJokes: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val bookmarkedJokes = _bookmarkedJokes.asStateFlow()
    fun getJokes(genre: String = "Any", amount: Int = 10) {
        viewModelScope.launch {
            _jokes.value = UiState.Loading
            try {
               jokesRepo.getJokes(genre = genre, amount = amount).collect{jokesList->
                    _jokes.value = UiState.Success(jokesList)
                }
            }catch (e: Exception){
                _jokes.value = UiState.Error(e.message.toString())
                debugLog(e.message.toString())
            }
        }
    }

    fun updateBookmark(id: Int, bookmarked: Boolean) {
        viewModelScope.launch {
            jokesRepo.updateBookmark(id, bookmarked)
        }
    }

    fun getBookmarksOnly() {
        _bookmarkedJokes.value = UiState.Loading
        viewModelScope.launch {
                try {
                jokesRepo.getBookmarksOnly().collect{jokesList->
                    _bookmarkedJokes.value = UiState.Success(jokes = jokesList)
                }
            } catch (e: Exception) {
                _bookmarkedJokes.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun deleteUnbookmarkedJokes() {
        viewModelScope.launch {
            jokesRepo.deleteUnbookmarkedJokes()
        }
    }
}