package com.dhruv.jokes.ui.screens

import com.dhruv.jokes.data.model.JokeOrSetup

sealed class UiState {
    data object Initial : UiState()
    data object Loading : UiState()
    class Success(val jokes :List<JokeOrSetup>) : UiState()
    class Error(val message: String) : UiState()
}