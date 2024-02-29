package com.example.vocabumate.data

import com.example.vocabumate.network.WordApiService

interface VocabumateRepository {
  suspend fun getDefinition(): String
}

class NetworkVocabumateRepository(
  private val wordApiService: WordApiService
) : VocabumateRepository {
  override suspend fun getDefinition(): String = wordApiService.getDefinition()
}
