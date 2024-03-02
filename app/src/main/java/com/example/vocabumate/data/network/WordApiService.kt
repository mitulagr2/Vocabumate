package com.example.vocabumate.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WordApiService {
  @GET("define")
  suspend fun getDefinition(
    @Query("word") word: String
  ): WordRemote
}
