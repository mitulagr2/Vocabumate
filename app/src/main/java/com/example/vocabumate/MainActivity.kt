package com.example.vocabumate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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

fun String.capitalize() =
  this.substring(0, 1).uppercase() + this.substring(1).lowercase()

fun String.sanitizeWord() =
  Regex("^[a-zA-Z ]*$").replace(this.trim().replace(Regex("\\s+"), " "), "")
