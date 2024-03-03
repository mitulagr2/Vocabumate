package com.example.vocabumate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vocabumate.ui.navigation.VocabumateNavHost
import com.example.vocabumate.ui.theme.VocabumateTheme

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun VocabumateApp(navController: NavHostController = rememberNavController()) {
  VocabumateNavHost(navController)
}

@Preview(showBackground = true)
@Composable
fun AppCompactPreview() {
  VocabumateTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background
    ) {
      VocabumateApp()
    }
  }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun AppMediumPreview() {
  VocabumateTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background
    ) {
      VocabumateApp()
    }
  }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun AppExpandedPreview() {
  VocabumateTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background
    ) {
      VocabumateApp()
    }
  }
}
