package com.dhruv.jokes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes_entity")
data class JokesEntity(
    @PrimaryKey
    val id: Int,
    val type: String,
    val setup: String?,
    val punchline: String?,
    val joke: String?,
    val isBookmarked: Boolean = false
)
