package com.example.vocabumate.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vocabumate.data.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(item: Word)

  @Update
  suspend fun update(item: Word)

  @Delete
  suspend fun delete(item: Word)

  @Query("SELECT * from words WHERE word = :word")
  fun getWord(word: String): Flow<Word>

  @Query("SELECT * from words ORDER BY word ASC")
  fun getAllWords(): Flow<List<Word>>
}
