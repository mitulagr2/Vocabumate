package com.example.vocabumate.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.example.vocabumate.KEY_LOCAL_OUTPUT
import com.example.vocabumate.KEY_QUERY_OUTPUT
import com.example.vocabumate.data.Word
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.network.RemoteWordsRepository
import com.example.vocabumate.ui.screens.WordDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json

/**
 * ViewModel to retrieve, update and delete a word from the [LocalWordsRepository]'s data source.
 */
class WordDetailsViewModel(
  savedStateHandle: SavedStateHandle,
  private val workManagerLocalWordsRepository: LocalWordsRepository,
  private val workManagerRemoteWordsRepository: RemoteWordsRepository
) : ViewModel() {

  private val word: String = checkNotNull(savedStateHandle[WordDetailsDestination.wordArg])

  init {
    workManagerLocalWordsRepository.getWord(word)
    Log.d("Test4", word)
  }

  val localWordState: StateFlow<Word> = workManagerLocalWordsRepository.outputWorkInfo
    .map { info ->
      val outputJson = info.outputData.getString(KEY_LOCAL_OUTPUT)
      var outputWord =
        if (!outputJson.isNullOrEmpty()) Json.decodeFromString<Word>(outputJson) else Word("","")
      when {
        info.state.isFinished && outputWord.word === word -> {
          Log.d("Test3", "outputJson")
          isSaved = true
          outputWord
        }

        info.state.isFinished -> {
          Log.d("Test2", outputJson ?: "NULL")
          isSaved = false
          workManagerRemoteWordsRepository.getDefinition(word)
          Word(word, "Fetching...")
        }

        else -> Word(word, "Loading...")
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
      initialValue = Word(word, "Loading...")
    )

  val remoteWordState: StateFlow<Word> =
    workManagerRemoteWordsRepository.outputWorkInfo
      .map { info ->

        val outputJson = info.outputData.getString(KEY_QUERY_OUTPUT)
        when {
          info.state.isFinished && !outputJson.isNullOrEmpty() -> {
            Json.decodeFromString<Word>(outputJson)
          }

          info.state == WorkInfo.State.FAILED -> {
            Word(word, "Error fetching the word.")
          }

          else -> Word(word, "Fetching...")
        }
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = Word(word, "")
      )

  var isSaved: Boolean by mutableStateOf(true)
    private set

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }

  fun saveWord() {
    workManagerLocalWordsRepository.insertWord(remoteWordState.value)
    isSaved = true
  }

  fun deleteWord() {
    workManagerLocalWordsRepository.deleteWord(localWordState.value)
    isSaved = false
  }
}
