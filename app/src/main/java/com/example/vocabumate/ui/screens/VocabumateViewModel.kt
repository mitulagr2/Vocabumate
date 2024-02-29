package com.example.vocabumate.ui.screens

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
import kotlinx.coroutines.launch
import java.io.IOException

class VocabumateViewModel(
  private val vocabumateRepository: VocabumateRepository
) : ViewModel() {

  fun getDefinition(word: String) {
    viewModelScope.launch {
      vocabumateRepository.getDefinition(word)
    }
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VocabumateApplication)
        val vocabumateRepository = application.container.vocabumateRepository
        VocabumateViewModel(vocabumateRepository)
      }
    }
  }
}
