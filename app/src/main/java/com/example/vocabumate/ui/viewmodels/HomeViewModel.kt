package com.example.vocabumate.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vocabumate.VocabumateApplication
import com.example.vocabumate.data.WordsRepository
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(private val wordsRepository: WordsRepository) : ViewModel() {

  var wordUiState: WordUiState by mutableStateOf(WordUiState.Success(""))
    private set

  fun getDefinition(word: String) {
    Log.d("ViewModel", word)
    wordUiState = WordUiState.Loading
    viewModelScope.launch {
      wordUiState = try {
        WordUiState.Success(wordsRepository.getDefinition(word))
      } catch (e: IOException) {
        Log.d("ViewModel", "" + e.message)
        WordUiState.Error
      }
    }
  }
}

sealed interface WordUiState {
  data class Success(val word: String) : WordUiState
  data object Error : WordUiState
  data object Loading : WordUiState
}
