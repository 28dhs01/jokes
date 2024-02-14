package com.dhruv.jokes.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.lang.Error

fun debugLog(message: String) {
    println("DEBUG: $message")
}

@Composable
fun CustomRowWith2Values(value1: String, value2: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = value1,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(text = value2)
    }
}

@Composable
fun ErrorMessage(error: String){
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        text = error,
        color = MaterialTheme.colorScheme.error
    )
}