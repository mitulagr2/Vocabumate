package com.example.vocabumate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

@Preview(showBackground = true)
@Composable
fun AppCompactPreview() {
  VocabumateTheme {
    App()
  }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun AppMediumPreview() {
  VocabumateTheme {
    App()
  }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun AppExpandedPreview() {
  VocabumateTheme {
    App()
  }
}

