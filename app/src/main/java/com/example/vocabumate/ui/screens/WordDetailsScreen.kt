package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vocabumate.R
import com.example.vocabumate.ui.components.VocabumateTopAppBar
import com.example.vocabumate.ui.navigation.NavigationDestination

object WordDetailsDestination : NavigationDestination {
  override val route = "word_details"
  override val titleRes = R.string.app_name
  const val wordArg = "wordArg"
  val routeWithArgs = "$route/{${wordArg}}"
}

@Composable
fun WordDetailsScreen(
  navigateTo: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      VocabumateTopAppBar(navigateTo)
    },
  ) { innerPadding ->
    WordDetailsBody(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    )
  }
}

@Composable
private fun WordDetailsBody(
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(text = "WordDetailsScreen")
  }
}
