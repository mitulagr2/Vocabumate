package com.example.vocabumate.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WordApiService {
  @GET("define")
  suspend fun getWord(
    @Query("word") word: String
  ): String

  @GET("daily")
  suspend fun getDaily(): String
}
