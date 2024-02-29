package com.example.vocabumate.data.local

import kotlinx.coroutines.flow.Flow

class OfflineWordsRepository(private val wordDao: WordDao) : LocalWordsRepository {
  override fun getAllWordsStream(): Flow<List<Word>> = wordDao.getAllWords()

  override fun getWordStream(word: String): Flow<Word?> = wordDao.getWord(word)

  override suspend fun insertWord(word: Word) = wordDao.insert(word)

  override suspend fun deleteWord(word: Word) = wordDao.delete(word)

  override suspend fun updateWord(word: Word) = wordDao.update(word)
}
