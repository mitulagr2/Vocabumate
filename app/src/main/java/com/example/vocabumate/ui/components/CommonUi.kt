package com.example.vocabumate.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppBottomBar(
  defineAction: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var query by rememberSaveable {
    mutableStateOf("")
  }

  Row(
    modifier = modifier
      .shadow(
        elevation = 16.dp,
        spotColor = Color.DarkGray
      )
  ) {
    TextField(
      value = query,
      singleLine = true,
      modifier = Modifier.weight(1F),
      colors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        disabledContainerColor = MaterialTheme.colorScheme.surface,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
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
    FloatingActionButton(
      onClick = { defineAction(query) }
    ) {
      Icon(Icons.Filled.Search, contentDescription = "Search")
    }
  }
}
