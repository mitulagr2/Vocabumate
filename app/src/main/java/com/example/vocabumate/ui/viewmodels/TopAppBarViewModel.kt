package com.example.vocabumate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabumate.data.local.UserPreferences
import com.example.vocabumate.data.local.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TopAppBarViewModel(
  private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

  val preferencesState: StateFlow<UserPreferences> =
    userPreferencesRepository.userPreferencesFlow
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = UserPreferences()
      )

  suspend fun updateDailyStreak() {
    userPreferencesRepository.updateDailyStreak()
  }

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }
}
