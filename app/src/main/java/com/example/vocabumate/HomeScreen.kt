package com.example.vocabumate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Text(
      text = "Hello Content!"
    )
    OutlinedTextField(
      value = "",
      singleLine = true,
      shape = MaterialTheme.shapes.large,
      modifier = Modifier.fillMaxWidth(),
      colors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        disabledContainerColor = MaterialTheme.colorScheme.surface,
      ),
      onValueChange = { },
//      label = "",
//      keyboardOptions = KeyboardOptions.Default.copy(
//        imeAction = ImeAction.Done
//      ),
//      keyboardActions = KeyboardActions(
//        onDone = { }
//      )
    )
  }
}
