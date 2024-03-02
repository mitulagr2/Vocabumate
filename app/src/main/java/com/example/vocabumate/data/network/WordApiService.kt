package com.example.vocabumate.data.network

import com.example.vocabumate.data.Word
import retrofit2.http.GET
import retrofit2.http.Query

interface WordApiService {
  @GET("define")
  suspend fun getDefinition(
    @Query("word") word: String
  ): String
}
