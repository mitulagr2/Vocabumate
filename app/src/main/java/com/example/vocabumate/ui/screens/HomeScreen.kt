package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vocabumate.ui.components.AppBottomBar

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Column(modifier = Modifier.weight(1F)) {
      Text(
        text = "Hello Content!"
      )
    }
    AppBottomBar()
  }
}
