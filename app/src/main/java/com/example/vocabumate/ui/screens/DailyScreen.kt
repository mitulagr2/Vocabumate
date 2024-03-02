package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vocabumate.R
import com.example.vocabumate.ui.components.TopAppBar
import com.example.vocabumate.ui.navigation.NavigationDestination


object DailyDestination : NavigationDestination {
  override val route = "daily"
  override val titleRes = R.string.app_name
}

@Composable
fun DailyScreen(
  navigateTo: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(navigateTo)
    },
  ) { innerPadding ->
    DailyBody(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    )
  }
}

@Composable
private fun DailyBody(
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(text = "DailyScreen")
  }
}
