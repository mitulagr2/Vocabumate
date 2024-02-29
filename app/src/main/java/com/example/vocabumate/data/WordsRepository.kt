package com.example.vocabumate.data

import com.example.vocabumate.network.WordApiService

interface WordsRepository {
  suspend fun getDefinition(word: String): String
}

class NetworkWordsRepository(
  private val wordApiService: WordApiService
) : WordsRepository {
  override suspend fun getDefinition(word: String): String = wordApiService.getDefinition(word)
}
