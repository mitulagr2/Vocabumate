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

object LikesDestination : NavigationDestination {
  override val route = "likes"
  override val titleRes = R.string.app_name
}

@Composable
fun LikesScreen(
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      VocabumateTopAppBar()
    },
  ) { innerPadding ->
    LikesBody(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    )
  }
}

@Composable
private fun LikesBody(
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(text = "LikesScreen")
  }
}
