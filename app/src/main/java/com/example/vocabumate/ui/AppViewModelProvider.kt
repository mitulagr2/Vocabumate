package com.example.vocabumate.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vocabumate.VocabumateApplication
import com.example.vocabumate.ui.viewmodels.HomeViewModel
import com.example.vocabumate.ui.viewmodels.LikesViewModel
import com.example.vocabumate.ui.viewmodels.WordDetailsViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Vocabumate app
 */
object AppViewModelProvider {
  val Factory = viewModelFactory {
    // Initializer for WordDetailsViewModel
    initializer {
      WordDetailsViewModel(
        this.createSavedStateHandle(),
        vocabumateApplication().container.workManagerWordsRepository
      )
    }
    // Initializer for LikesViewModel
    initializer {
      LikesViewModel(vocabumateApplication().container.workManagerWordsRepository)
    }
    // Initializer for HomeViewModel
    initializer {
      HomeViewModel(
        vocabumateApplication().container.workManagerWordsRepository
      )
    }
  }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [VocabumateApplication].
 */
fun CreationExtras.vocabumateApplication(): VocabumateApplication =
  (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VocabumateApplication)
