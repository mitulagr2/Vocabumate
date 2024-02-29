package com.example.vocabumate.network

import retrofit2.http.GET

interface WordApiService {
  @GET("define")
  suspend fun getDefinition(): String
}
