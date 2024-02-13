package com.dhruv.jokes.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dhruv.jokes.ui.destinations.TopLevelDestination
import com.dhruv.jokes.ui.screens.ProgrammingJokesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = TopLevelDestination.Home.route){
        composable(route = TopLevelDestination.Home.route){
            ProgrammingJokesScreen(modifier = Modifier.fillMaxSize())
        }
    }
}