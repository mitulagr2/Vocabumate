package com.example.vocabumate.data

import android.content.Context
import com.example.vocabumate.network.WordApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


interface AppContainer {
  val wordsRepository: WordsRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

  private val baseUrl =
    "https://vocabumate.onrender.com"

  private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .build()

  private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
//    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .build()

  private val retrofitService: WordApiService by lazy {
    retrofit.create(WordApiService::class.java)
  }

  override val wordsRepository: WordsRepository by lazy {
    NetworkWordsRepository(retrofitService)
  }
}

