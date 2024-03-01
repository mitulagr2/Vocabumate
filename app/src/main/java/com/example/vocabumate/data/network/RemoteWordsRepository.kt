package com.example.vocabumate.data.network

class RemoteWordsRepository(
  private val wordApiService: WordApiService
) : NetworkWordsRepository {
  override suspend fun getDefinition(word: String): String = wordApiService.getDefinition(word)
}
