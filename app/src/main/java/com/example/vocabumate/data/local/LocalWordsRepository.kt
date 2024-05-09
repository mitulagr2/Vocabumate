package com.example.vocabumate.data.local

import androidx.work.WorkInfo
import com.example.vocabumate.data.Word
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Word] from a given data source.
 */
interface LocalWordsRepository {
  /**
   * Retrieve all the items from the given data source.
   */
  fun getAllWordsStream(): Flow<List<Word>>

  /**
   * Retrieve an item from the given data source that matches with the [word].
   */
  fun getWord(word: String): Word?

  /**
   * Insert item in the data source
   */
  suspend fun insertWord(word: Word)

  /**
   * Delete item from the data source
   */
  suspend fun deleteWord(word: Word)
}
