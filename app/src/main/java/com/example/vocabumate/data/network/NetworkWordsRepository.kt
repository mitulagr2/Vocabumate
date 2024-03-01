package com.example.vocabumate.data.network

interface NetworkWordsRepository {
  suspend fun getDefinition(word: String): String
}
