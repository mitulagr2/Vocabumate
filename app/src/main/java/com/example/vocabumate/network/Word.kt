package com.example.vocabumate.network

import kotlinx.serialization.Serializable

@Serializable
data class Word(
  val word: String,
  val meaning: String
)
