package com.example.vocabumate.data

import android.content.Context
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.local.UserPreferencesRepository
import com.example.vocabumate.data.local.VocabumateDatabase
import com.example.vocabumate.data.local.WordDao
import com.example.vocabumate.data.network.RemoteWordsRepository
import com.example.vocabumate.data.network.WordApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

interface WordsRepositoryInterface : LocalWordsRepository, RemoteWordsRepository

class WordsRepository(context: Context) : WordsRepositoryInterface {

  private val wordDao: WordDao = VocabumateDatabase.getDatabase(context).wordDao()

  private val userPreferencesRepository: UserPreferencesRepository =
    UserPreferencesRepository(context.dataStore)

  private val baseUrl =
    "https://vocabumate.onrender.com"

  private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .build()

  private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .build()

  private val retrofitService: WordApiService by lazy {
    retrofit.create(WordApiService::class.java)
  }

  override fun getAllWordsStream(): Flow<List<Word>> = wordDao.getAllWords()

  override fun getWord(word: String): Word? = wordDao.getWord(word)

  override suspend fun insertWord(word: Word) = wordDao.insert(word)

  override suspend fun deleteWord(word: Word) = wordDao.delete(word)

  override suspend fun getRemoteWord(word: String): String = retrofitService.getWord(word)

  override suspend fun getRemoteDaily(): String = retrofitService.getDaily()
}
