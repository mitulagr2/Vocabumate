package com.example.vocabumate.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.example.vocabumate.KEY_QUERY_OUTPUT
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.local.Word
import com.example.vocabumate.data.network.RemoteWordsRepository
import com.example.vocabumate.ui.screens.WordDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve, update and delete a word from the [LocalWordsRepository]'s data source.
 */
class WordDetailsViewModel(
  savedStateHandle: SavedStateHandle,
  private val localWordsRepository: LocalWordsRepository,
  private val workManagerRemoteWordsRepository: RemoteWordsRepository
) : ViewModel() {

  private val word: String = checkNotNull(savedStateHandle[WordDetailsDestination.wordArg])

  val localWordState: StateFlow<Word> = localWordsRepository.getWordStream(word)
    .map {
      if (it === null) {
        isLocal = false
        workManagerRemoteWordsRepository.getDefinition(word)
        Word(word, "")
      } else {
        isSaved = true
        it
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
        val outputWord = info.outputData.getString(KEY_QUERY_OUTPUT)
        when {
          info.state.isFinished && !outputWord.isNullOrEmpty() -> {
            Word(word, outputWord)
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

  var isLocal: Boolean by mutableStateOf(true)
    private set
  var isSaved: Boolean by mutableStateOf(false)
    private set

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }

  suspend fun saveWord() {
    localWordsRepository.insertWord(if (isLocal) localWordState.value else remoteWordState.value)
    isSaved = true
  }

  suspend fun deleteWord() {
    localWordsRepository.deleteWord(localWordState.value)
    isSaved = false
  }
}
