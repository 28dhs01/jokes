package com.dhruv.jokes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhruv.jokes.R
import com.dhruv.jokes.data.local.JokesEntity
import com.dhruv.jokes.ui.viewmodel.JokesViewModel
import com.dhruv.jokes.utils.CustomRowWith2Values
import com.dhruv.jokes.utils.ErrorMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgrammingJokesScreen(
    modifier: Modifier = Modifier,
    viewModel: JokesViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getJokes()
    }
    val jokesUiState = viewModel.jokes.collectAsState().value
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "Jokes") })
    }) { innerPadding ->
        when (jokesUiState) {
            is UiState.Loading -> {
                Column(
                    modifier = modifier.padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Column(
                    modifier = modifier.padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val error = jokesUiState.message
                    ErrorMessage(error = error)
                }
            }

            is UiState.Success -> {
                if (jokesUiState.jokes.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ErrorMessage(error = "No joke stored locally and your internet is also not available")
                    }
                } else {
                    LazyColumn(modifier = modifier.padding(innerPadding)) {
                        items(jokesUiState.jokes, key = { joke ->
                            joke.id
                        }) { joke ->
                            JokeItem(joke = joke) { isBookmarked ->
                                viewModel.updateBookmark(joke.id, isBookmarked)
                            }
                        }
                    }
                }
            }

            else -> {}
        }

    }


}

@Composable
fun JokeItem(joke: JokesEntity, updateBookmark: (Boolean) -> Unit) {
    if (joke.type == "single") {
        joke.joke?.let {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    CustomRowWith2Values(
                        modifier = Modifier.weight(1f),
                        value1 = "Joke",
                        value2 = joke.joke
                    )
                    IconToggleButton(checked = joke.isBookmarked, onCheckedChange = { value ->
                        updateBookmark(value)
                    }) {
                        Icon(
                            painter = if (joke.isBookmarked) painterResource(id = R.drawable.baseline_bookmark_added_24) else painterResource(
                                id = R.drawable.baseline_bookmark_add_24
                            ), contentDescription = "bookmark"
                        )
                    }
                }
            }

        }
    } else {
        joke.setup?.let { setup ->
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(8.dp)){
                    Column(modifier = Modifier.weight(1f)) {
                        CustomRowWith2Values(value1 = "Setup", value2 = setup)
                        joke.punchline?.let { punchline ->
                            CustomRowWith2Values(value1 = "Punchline", value2 = punchline)
                        }
                    }
                    IconToggleButton(checked = joke.isBookmarked, onCheckedChange = { value ->
                        updateBookmark(value)
                    }) {
                        Icon(
                            painter = if (joke.isBookmarked) painterResource(id = R.drawable.baseline_bookmark_added_24) else painterResource(
                                id = R.drawable.baseline_bookmark_add_24
                            ), contentDescription = "bookmark"
                        )
                    }

                }

            }

        }
    }
}