package com.example.vocabumate.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class represents a single row in the database.
 */

@Entity(tableName = "words")
data class Word(
  @PrimaryKey
  val word: String,
  val meaning: String
)
