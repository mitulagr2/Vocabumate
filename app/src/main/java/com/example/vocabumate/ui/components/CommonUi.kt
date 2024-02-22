package com.example.vocabumate.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

  OutlinedTextField(
    value = query,
    singleLine = true,
    shape = MaterialTheme.shapes.large,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.colors(
      focusedContainerColor = MaterialTheme.colorScheme.surface,
      unfocusedContainerColor = MaterialTheme.colorScheme.surface,
      disabledContainerColor = MaterialTheme.colorScheme.surface,
    ),
    onValueChange = { query = it },
//      label = "",
//      keyboardOptions = KeyboardOptions.Default.copy(
//        imeAction = ImeAction.Done
//      ),
//      keyboardActions = KeyboardActions(
//        onDone = { }
//      )
  )
}
