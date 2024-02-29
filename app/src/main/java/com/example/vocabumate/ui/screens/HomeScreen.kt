package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabumate.R
import com.example.vocabumate.ui.AppViewModelProvider
import com.example.vocabumate.ui.components.InputBottomBar
import com.example.vocabumate.ui.components.VocabumateTopAppBar
import com.example.vocabumate.ui.navigation.NavigationDestination
import com.example.vocabumate.ui.viewmodels.HomeViewModel
import com.example.vocabumate.ui.viewmodels.WordUiState

object HomeDestination : NavigationDestination {
  override val route = "home"
  override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
  navigateTo: (String) -> Unit,
  navigateToWordDetails: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      VocabumateTopAppBar(navigateTo)
    },
  ) { innerPadding ->
    HomeBody(
      wordUiState = viewModel.wordUiState,
      defineAction = viewModel::getDefinition,
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    )
  }
}

@Composable
private fun HomeBody(
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
    InputBottomBar(defineAction)
  }
}
