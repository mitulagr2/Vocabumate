package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vocabumate.R
import com.example.vocabumate.ui.navigation.NavigationDestination

object ProfileDestination : NavigationDestination {
  override val route = "profile"
  override val titleRes = R.string.app_name
}

@Composable
fun ProfileScreen(
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.padding(horizontal = 32.dp, vertical = 24.dp)) {
    Row {
      Text(
        text = "Profile",
        style = MaterialTheme.typography.headlineMedium
      )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = "Cross-device sync is currently not supported.",
      style = MaterialTheme.typography.bodyLarge,
    )
  }
}
