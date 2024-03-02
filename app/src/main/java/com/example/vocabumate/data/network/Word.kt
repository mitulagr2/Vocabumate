package com.example.vocabumate.data.network

import kotlinx.serialization.Serializable

@Serializable
data class WordRemote(
  val word: String,
  val meaning: String,
  val popularity: Int
)
