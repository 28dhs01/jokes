package com.dhruv.jokes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhruv.jokes.data.model.JokeOrSetup
import com.dhruv.jokes.ui.viewmodel.JokesViewModel
import com.dhruv.jokes.utils.CustomRowWith2Values

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
        TopAppBar(title = { Text(text = "Programming Jokes") })
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
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            is UiState.Success -> {
                LazyColumn(modifier = modifier.padding(innerPadding)) {
                    items(jokesUiState.jokes, key = { joke ->
                        joke.id
                    }) { joke ->
                        JokeItem(joke = joke)
                    }
                }
            }

            else -> {}
        }

    }


}

@Composable
fun JokeItem(joke: JokeOrSetup) {
    if (joke.type == "single") {
        joke.joke?.let {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier.padding(8.dp)) {
                    CustomRowWith2Values(value1 = "Joke", value2 = joke.joke)
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
                Column(modifier = Modifier.padding(8.dp)) {
                    CustomRowWith2Values(value1 = "Setup", value2 = setup)
                    joke.punchline?.let { punchline ->
                        CustomRowWith2Values(value1 = "Punchline", value2 = punchline)
                    }
                }
            }

        }
    }
}