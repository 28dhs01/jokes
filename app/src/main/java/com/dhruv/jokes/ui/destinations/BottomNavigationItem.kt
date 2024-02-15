package com.dhruv.jokes.ui.destinations

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.dhruv.jokes.data.local.JokesEntity
import com.dhruv.jokes.ui.navigation.AppNavigation
import com.dhruv.jokes.ui.viewmodel.JokesViewModel

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(
    viewModel: JokesViewModel = hiltViewModel(),
    bottomNavItems: List<BottomNavigationItem>
) {

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Jokes",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            })
        },
        bottomBar = {
            BottomAppBar {
                bottomNavItems.forEachIndexed { index, bottomItem ->
                    NavigationBarItem(
                        label = { Text(text = bottomItem.title)},
                        selected = index == selectedIndex,
                        onClick = {
                            navController.popBackStack()
                            selectedIndex = index
                            if( selectedIndex == 1 ){
                                navController.navigate(TopLevelDestination.Bookmarks.route)
                            }else if( selectedIndex == 2 ){
                                navController.navigate(TopLevelDestination.Delete.route)
                            }else{
                                navController.navigate(TopLevelDestination.Home.route)
                            }
                        },
                        icon = {
                            val id =
                                if (index == selectedIndex) bottomItem.selectedIcon else bottomItem.unselectedIcon
                            Icon(
                                painter = painterResource(id = id),
                                contentDescription = "bottom nav item"
                            )
                        })
                }
            }
        }) { innerPadding ->
        AppNavigation(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}