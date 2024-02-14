package com.dhruv.jokes.ui.screens

import com.dhruv.jokes.data.local.JokesEntity


sealed class UiState {
    data object Initial : UiState()
    data object Loading : UiState()
    class Success(val jokes :List<JokesEntity>) : UiState()
    class Error(val message: String) : UiState()
}