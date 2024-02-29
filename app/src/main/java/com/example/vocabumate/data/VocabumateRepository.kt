package com.example.vocabumate.data

import com.example.vocabumate.network.WordApiService

interface VocabumateRepository {
  suspend fun getDefinition(word: String): String
}

class NetworkVocabumateRepository(
  private val wordApiService: WordApiService
) : VocabumateRepository {
  override suspend fun getDefinition(word: String): String = wordApiService.getDefinition(word)
}
