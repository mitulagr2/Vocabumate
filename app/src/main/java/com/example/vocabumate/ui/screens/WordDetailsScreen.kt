package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabumate.R
import com.example.vocabumate.data.Word
import com.example.vocabumate.ui.AppViewModelProvider
import com.example.vocabumate.ui.components.CardData
import com.example.vocabumate.ui.components.FlashCard
import com.example.vocabumate.ui.components.TopAppBar
import com.example.vocabumate.ui.navigation.NavigationDestination
import com.example.vocabumate.ui.viewmodels.WordDetailsViewModel
import kotlinx.coroutines.launch

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
  val coroutineScope = rememberCoroutineScope()
  val wordDetailsUiState by viewModel.wordDetailsState.collectAsState()

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(navigateTo)
    },
  ) { innerPadding ->

    WordDetailsBody(
      info = wordDetailsUiState,
      iconVector = if (viewModel.isSaved) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
      btnAction = {
        coroutineScope.launch {
          if (viewModel.isSaved) viewModel.deleteWord() else viewModel.saveWord()
        }
      },
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    )
  }
}

@Composable
private fun WordDetailsBody(
  info: Word,
  iconVector: ImageVector,
  btnAction: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    FlashCard(
      data = CardData(
        info = info,
        likeAction = btnAction,
        icon = iconVector
      )
    )
  }
}
