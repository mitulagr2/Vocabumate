package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vocabumate.R
import com.example.vocabumate.ui.navigation.NavigationDestination

object DiscoverDestination : NavigationDestination {
  override val route = "discover"
  override val titleRes = R.string.app_name
}

@Composable
fun DiscoverScreen(
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.padding(horizontal = 32.dp, vertical = 24.dp)) {
    Row {
      Text(
        text = "DiscoverScreen",
        style = MaterialTheme.typography.headlineMedium
      )
    }
  }
}
