package com.example.vocabumate.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.local.Word
import com.example.vocabumate.data.network.NetworkWordsRepository
import com.example.vocabumate.ui.screens.WordDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel to retrieve, update and delete a word from the [LocalWordsRepository]'s data source.
 */
class WordDetailsViewModel(
  savedStateHandle: SavedStateHandle,
  private val localWordsRepository: LocalWordsRepository,
  private val remoteWordsRepository: NetworkWordsRepository
) : ViewModel() {

  private val word: String = checkNotNull(savedStateHandle[WordDetailsDestination.wordArg])

  var wordUiState: WordUiState by mutableStateOf(WordUiState())
    private set

  val wordDetailsUiState: StateFlow<WordUiState> =
    localWordsRepository.getWordStream(word)
      .map {
        if (it === null) {
          getDefinition(word)
          WordUiState()
        } else {
          WordUiState(isLocal = true, wordDetails = it)
        }
      }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = WordUiState()
      )

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }

  private fun getDefinition(word: String) {
    Log.d("ViewModel", word)
    wordUiState = WordUiState(Word(word, "Loading..."))
    viewModelScope.launch {
      wordUiState = try {
        WordUiState(Word(word, remoteWordsRepository.getDefinition(word)))
      } catch (e: IOException) {
        Log.d("ViewModel", "" + e.message)
        WordUiState(Word(word, "Error."))
      }
    }
  }

  suspend fun saveWord() {
    localWordsRepository.insertWord(wordUiState.wordDetails)
  }

  suspend fun deleteWord() {
    localWordsRepository.deleteWord(wordDetailsUiState.value.wordDetails)
  }
}

/**
 * UI state for WordDetailsScreen
 */
data class WordUiState(
  val wordDetails: Word = Word("", ""),
  val isLocal: Boolean = false
)
