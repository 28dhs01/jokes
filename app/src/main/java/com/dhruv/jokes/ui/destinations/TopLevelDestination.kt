package com.dhruv.jokes.ui.destinations

sealed class TopLevelDestination(val route: String) {
    data object Home: TopLevelDestination("home_screen")
}
