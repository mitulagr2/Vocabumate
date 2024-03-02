package com.example.vocabumate.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vocabumate.data.Word

@Database(entities = [Word::class], version = 2, exportSchema = false)
abstract class VocabumateDatabase : RoomDatabase() {

  abstract fun wordDao(): WordDao

  companion object {
    @Volatile
    private var Instance: VocabumateDatabase? = null

    fun getDatabase(context: Context): VocabumateDatabase {
      // if the Instance is not null, return it, otherwise create a new database instance.
      return Instance ?: synchronized(this) {
        Room.databaseBuilder(context, VocabumateDatabase::class.java, "word_database")
          .fallbackToDestructiveMigration().build().also { Instance = it }
      }
    }
  }
}
