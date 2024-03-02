package com.example.vocabumate.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Entity data class represents a single row in the database.
 */

@Serializable
@Entity(tableName = "words")
data class Word(
  @PrimaryKey
  val word: String,
  val meaning: String,
  val popularity: Int = 1
)
