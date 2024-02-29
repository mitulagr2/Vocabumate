package com.example.vocabumate.data

import android.content.Context
import com.example.vocabumate.network.WordApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
  val vocabumateRepository: VocabumateRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

  private val baseUrl =
    "https://vocabumate.onrender.com"

  /**
   * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
   */
  private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(baseUrl)
    .build()

  private val retrofitService: WordApiService by lazy {
    retrofit.create(WordApiService::class.java)
  }

  override val vocabumateRepository: VocabumateRepository by lazy {
    NetworkVocabumateRepository(retrofitService)
  }
}

