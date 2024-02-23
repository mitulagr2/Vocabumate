package com.example.vocabumate.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun AppBottomBar(
  modifier: Modifier = Modifier
) {
  var query by rememberSaveable {
    mutableStateOf("")
  }

  Surface(
    modifier = modifier
  ) {
    TextField(
      value = query,
      singleLine = true,
      modifier = Modifier.fillMaxWidth(),
      colors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        disabledContainerColor = MaterialTheme.colorScheme.surface,
      ),
      onValueChange = { query = it },
      label = { Text("Enter word here") },
      //      keyboardOptions = KeyboardOptions.Default.copy(
      //        imeAction = ImeAction.Done
      //      ),
      //      keyboardActions = KeyboardActions(
      //        onDone = { }
      //      )
    )
  }
}
