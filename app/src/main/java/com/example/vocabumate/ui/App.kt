package com.example.vocabumate.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vocabumate.HomeScreen
import com.example.vocabumate.ui.theme.VocabumateTheme

@Composable
fun App(modifier: Modifier = Modifier) {
  Scaffold(
    topBar = {
      Text(
        text = "Hello Topbar!"
      )
    },
    modifier = modifier
  ) { innerPadding ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      color = MaterialTheme.colorScheme.background
    ) {
      HomeScreen()
    }
  }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
  VocabumateTheme {
    App()
  }
}
