package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabumate.R
import com.example.vocabumate.data.local.Word
import com.example.vocabumate.ui.AppViewModelProvider
import com.example.vocabumate.ui.components.VocabumateTopAppBar
import com.example.vocabumate.ui.navigation.NavigationDestination
import com.example.vocabumate.ui.viewmodels.WordDetailsViewModel

object WordDetailsDestination : NavigationDestination {
  override val route = "word_details"
  override val titleRes = R.string.app_name
  const val wordArg = "wordArg"
  val routeWithArgs = "$route/{${wordArg}}"
}

@Composable
fun WordDetailsScreen(
  navigateTo: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: WordDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
  val wordDetailsUiState = viewModel.wordDetailsUiState.collectAsState()

  Scaffold(
    modifier = modifier,
    topBar = {
      VocabumateTopAppBar(navigateTo)
    },
  ) { innerPadding ->
    WordDetailsBody(
      wordDetails = if (wordDetailsUiState.value.isLocal) wordDetailsUiState.value.wordDetails else viewModel.wordUiState.wordDetails,
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    )
  }
}

@Composable
private fun WordDetailsBody(
  wordDetails: Word,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(text = wordDetails.word)
    Text(
      text = wordDetails.meaning
    )
  }
}
