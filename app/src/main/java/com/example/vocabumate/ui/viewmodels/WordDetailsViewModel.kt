package com.example.vocabumate.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.example.vocabumate.KEY_OUTPUT_DATA
import com.example.vocabumate.KEY_OUTPUT_TYPE
import com.example.vocabumate.data.Word
import com.example.vocabumate.data.WordsRepository
import com.example.vocabumate.ui.screens.WordDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json

/**
 * ViewModel to retrieve, update and delete a word from the [WordsRepository]'s data source.
 */
class WordDetailsViewModel(
  savedStateHandle: SavedStateHandle,
  private val workManagerWordsRepository: WordsRepository
) : ViewModel() {

  private val word: String = checkNotNull(savedStateHandle[WordDetailsDestination.wordArg])

  init {
    Log.d("init", word)
    workManagerWordsRepository.getWord(word)
  }

  val wordDetailsState: StateFlow<Word> = workManagerWordsRepository.outputWorkInfo
    .map { info ->
      val outputData = info.outputData.getString(KEY_OUTPUT_DATA)
      val outputType = info.outputData.getString(KEY_OUTPUT_TYPE)
      isSaved = false

      when {
        (info.state == WorkInfo.State.FAILED) && (outputType.equals("LOCAL")) -> {
          isSaved = true
          Json.decodeFromString<Word>(outputData!!)
        }

        (info.state == WorkInfo.State.SUCCEEDED) && (outputType.equals("REMOTE")) -> {
          Json.decodeFromString<Word>(outputData!!)
        }

        info.state == WorkInfo.State.FAILED -> {
          Word(word, "Error retrieving word. Please try again later.")
        }

        else -> Word(word, "Loading...")
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
      initialValue = Word(word, "Loading...")
    )

  var isSaved: Boolean by mutableStateOf(false)
    private set

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }

  fun saveWord() {
    isSaved = true
    workManagerWordsRepository.insertWord(wordDetailsState.value)
  }

  fun deleteWord() {
    isSaved = false
    workManagerWordsRepository.deleteWord(wordDetailsState.value)
  }
}
