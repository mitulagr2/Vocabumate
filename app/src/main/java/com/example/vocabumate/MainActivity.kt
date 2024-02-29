package com.example.vocabumate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vocabumate.ui.theme.VocabumateTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      VocabumateTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          VocabumateApp()
        }

      }
    }
  }
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

