package com.example.vocabumate.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.example.vocabumate.KEY_WORD_QUERY
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.local.Word
import com.example.vocabumate.data.network.RemoteWordsRepository
import com.example.vocabumate.ui.screens.WordDetailsDestination
import kotlinx.coroutines.flow.Flow
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

  private val localWordUiFlow: Flow<WordDetailsUiState> = localWordsRepository.getWordStream(word)
    .map {
      if (it === null) {
        workManagerRemoteWordsRepository.getDefinition(word)
        WordDetailsUiState()
      } else {
        isSaved = true
        WordDetailsUiState(isSaved = true, wordDetails = it)
      }
    }

  private val remoteWordUiFlow: Flow<WordDetailsUiState> =
    workManagerRemoteWordsRepository.outputWorkInfo
      .map { info ->
        val outputWord = info.outputData.getString(KEY_WORD_QUERY)
        when {
          info.state.isFinished && !outputWord.isNullOrEmpty() -> {
            WordDetailsUiState(Word(word, outputWord))
          }

          info.state == WorkInfo.State.CANCELLED -> {
            WordDetailsUiState()
          }

          else -> WordDetailsUiState()
        }
      }

  private var isSaved = false

  val wordDetailsUiState: StateFlow<WordDetailsUiState> =
    (if (isSaved) localWordUiFlow else remoteWordUiFlow)
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = WordDetailsUiState()
      )

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }

  suspend fun saveWord() {
    localWordsRepository.insertWord(wordDetailsUiState.value.wordDetails)
  }

  suspend fun deleteWord() {
    localWordsRepository.deleteWord(wordDetailsUiState.value.wordDetails)
  }
}

/**
 * UI state for WordDetailsScreen
 */
data class WordDetailsUiState(
  val wordDetails: Word = Word("", ""),
  val isSaved: Boolean = false
)
