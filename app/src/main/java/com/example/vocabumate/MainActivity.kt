package com.example.vocabumate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.vocabumate.ui.App
import com.example.vocabumate.ui.theme.VocabumateTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      VocabumateTheme {
        App()
      }
    }
  }
}
