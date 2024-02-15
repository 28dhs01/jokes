package com.dhruv.jokes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import com.dhruv.jokes.ui.destinations.BottomNavigation
import com.dhruv.jokes.ui.destinations.BottomNavigationItem
import com.dhruv.jokes.ui.navigation.AppNavigation
import com.dhruv.jokes.ui.theme.JokesTheme
import com.dhruv.jokes.ui.viewmodel.JokesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<JokesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JokesTheme {
                val bottomNavItems = listOf(
                    BottomNavigationItem(title = "Home", selectedIcon = R.drawable.baseline_home_24, unselectedIcon = R.drawable.outline_home_24),
                    BottomNavigationItem(title = "Bookmarks", selectedIcon = R.drawable.baseline_bookmarks_24, unselectedIcon = R.drawable.outline_bookmarks_24),
                    BottomNavigationItem(title = "Delete", selectedIcon = R.drawable.baseline_delete_24, unselectedIcon = R.drawable.baseline_delete_outline_24)
                )
                BottomNavigation(bottomNavItems = bottomNavItems)
            }
        }
    }
}

