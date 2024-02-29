package com.example.vocabumate.ui.screens

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
import com.example.vocabumate.data.VocabumateRepository
import com.example.vocabumate.network.Word
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface WordUiState {
  data class Success(val word: String) : WordUiState
  data object Error : WordUiState
  data object Loading : WordUiState
}

class VocabumateViewModel(
  private val vocabumateRepository: VocabumateRepository
) : ViewModel() {

  var wordUiState: WordUiState by mutableStateOf(WordUiState.Success(""))
    private set

  fun getDefinition(word: String) {
    Log.d("ViewModel", word)
    wordUiState = WordUiState.Loading
    viewModelScope.launch {
      wordUiState = try {
        Log.d("ViewModel", "word")
        WordUiState.Success(vocabumateRepository.getDefinition(word))
      } catch (e: IOException) {
        Log.d("ViewModel", ""+e.message)
        WordUiState.Error
      }
    }
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VocabumateApplication)
        val vocabumateRepository = application.container.vocabumateRepository
        VocabumateViewModel(vocabumateRepository)
      }
      Log.d("ViewModel", "HERE")
    }
  }
}
