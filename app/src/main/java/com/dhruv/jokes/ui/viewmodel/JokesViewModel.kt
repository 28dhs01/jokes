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

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _bookmarkedJokes: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val bookmarkedJokes = _bookmarkedJokes.asStateFlow()

    init{
        debugLog("view model created")
        fetchUnbookmarkedJokes()
    }
    private fun fetchUnbookmarkedJokes(genre: String = "Programming", amount: Int = 10) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
               jokesRepo.fetchUnbookmarkedJokes(genre = genre, amount = amount).collect{ jokesList->
                    _uiState.value = UiState.Success(jokesList)
                }
            }catch (e: Exception){
                _uiState.value = UiState.Error(e.message.toString())
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
                    _bookmarkedJokes.value = UiState.Success(unbookmarkedJokes = jokesList)
                }
            } catch (e: Exception) {
                _bookmarkedJokes.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun deleteUnbookmarkedJokes() {
        viewModelScope.launch {
            try {
                jokesRepo.deleteUnbookmarkedJokes()
            } catch (e: Exception) {
                debugLog("Error to delete all unbookmarked joke with msg ${e.message}")
            }
        }
    }

    fun deleteJoke(id: Int){
        viewModelScope.launch {
            try {
                jokesRepo.deleteJoke(id = id)
            } catch (e: Exception) {
                debugLog("Error to delete joke with id $id with msg ${e.message}")
            }
        }
    }
}