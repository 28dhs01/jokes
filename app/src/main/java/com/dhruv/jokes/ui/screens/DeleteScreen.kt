package com.dhruv.jokes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhruv.jokes.ui.viewmodel.JokesViewModel

@Composable
fun DeleteScreen(
    modifier: Modifier,
    viewModel: JokesViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(true) }
    Column(modifier = modifier) {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when clicked outside
                    showDialog = false
                },
                title = {
                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "Delete Unbookmarked Jokes?",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text(text = "Dismiss")
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Action when confirm button is clicked
                            viewModel.deleteUnbookmarkedJokes()
                            showDialog = false
                        }
                    ) {
                        Text("Confirm")
                    }
                }
            )
        }
    }
}