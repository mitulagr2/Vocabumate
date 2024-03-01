package com.example.vocabumate.data

import android.content.Context
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.local.OfflineWordsRepository
import com.example.vocabumate.data.local.VocabumateDatabase
import com.example.vocabumate.data.network.NetworkWordsRepository
import com.example.vocabumate.data.network.RemoteWordsRepository
import com.example.vocabumate.data.network.WordApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


interface AppContainer {
  val localWordsRepository: LocalWordsRepository
  val remoteWordsRepository: NetworkWordsRepository
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

  override val localWordsRepository: LocalWordsRepository by lazy {
    OfflineWordsRepository(VocabumateDatabase.getDatabase(context).wordDao())
  }

  override val remoteWordsRepository: NetworkWordsRepository by lazy {
    RemoteWordsRepository(retrofitService)
  }
}

