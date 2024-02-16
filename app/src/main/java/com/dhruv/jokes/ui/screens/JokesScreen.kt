package com.dhruv.jokes.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
fun JokesScreen(
    modifier: Modifier = Modifier,
    viewModel: JokesViewModel = hiltViewModel()
) {
    when (val jokesUiState = viewModel.jokes.collectAsState().value) {
        is UiState.Loading -> {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Error -> {
            Column(
                modifier = modifier,
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
                    ErrorMessage(error = "It seems no joke is stored locally! Check your bookmarked jokes")
                }
            } else {
                LazyColumn(modifier = modifier) {
                    items(jokesUiState.jokes, key = { joke ->
                        joke.id
                    }) { joke ->

                        val dismissState = rememberSwipeToDismissBoxState()
                        when (dismissState.currentValue) {
                            SwipeToDismissBoxValue.EndToStart -> {
                                viewModel.deleteJoke(joke.id)
                            }

                            SwipeToDismissBoxValue.StartToEnd -> {
                                viewModel.updateBookmark(id = joke.id, bookmarked = true)
                            }

                            else -> {}
                        }
                        SwipeToDismissBox(state = dismissState, backgroundContent = {
                            // background color
                            val backgroundColor by animateColorAsState(
                                when (dismissState.targetValue) {
                                    SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(alpha = 0.8f)
                                    SwipeToDismissBoxValue.StartToEnd -> Color.Green
                                    else -> Color.White
                                }, label = ""
                            )
                            // icon size
                            val iconScale by animateFloatAsState(
                                targetValue = if (
                                    dismissState.targetValue == SwipeToDismissBoxValue.EndToStart ||
                                    dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd
                                ) 1.3f else 0.5f,
                                label = ""
                            )
                            Box(
                                Modifier
                                    .padding(16.dp)
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(color = backgroundColor)
                                    .padding(16.dp),
                            ) {
                                if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) {
                                    Icon(
                                        modifier = Modifier
                                            .scale(iconScale)
                                            .align(Alignment.CenterStart),
                                        painter = painterResource(id = R.drawable.baseline_bookmark_added_24),
                                        contentDescription = "Bookmark",
                                        tint = Color.White
                                    )
                                } else {
                                    Icon(
                                        modifier = Modifier
                                            .scale(iconScale)
                                            .align(Alignment.CenterEnd),
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                        ) {
                            AnimatedVisibility(
                                visible = !joke.isBookmarked,
                                exit = fadeOut(animationSpec = TweenSpec(durationMillis = 10)) + shrinkVertically(
                                    animationSpec = TweenSpec(durationMillis = 10)
                                )
                            ) {
                                JokeItem(joke = joke) { isBookmarked ->
                                    viewModel.updateBookmark(joke.id, isBookmarked)
                                }
                            }
                        }
                    }
                }
            }
        }

        else -> {}
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
                Row(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(8.dp)
                ) {
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
                Row(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(8.dp)
                ) {
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