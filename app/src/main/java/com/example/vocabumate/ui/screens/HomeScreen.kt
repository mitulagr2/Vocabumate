package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vocabumate.ui.components.AppBottomBar

@Composable
fun HomeScreen(
  wordUiState: WordUiState,
  defineAction: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Column(modifier = Modifier.weight(1F)) {
      when (wordUiState) {
        is WordUiState.Loading -> Text(
          text = "Loading..."
        )
        is WordUiState.Success -> Text(
          text = wordUiState.word
        )
        is WordUiState.Error -> Text(
          text = "Error."
        )
      }
    }
    AppBottomBar(defineAction)
  }
}
