package com.example.vocabumate.data.network

import kotlinx.serialization.Serializable

@Serializable
data class Word(
  val word: String,
  val meaning: String
)
