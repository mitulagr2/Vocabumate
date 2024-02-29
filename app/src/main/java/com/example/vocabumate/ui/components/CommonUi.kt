package com.example.vocabumate.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vocabumate.R

/**
 * App bar to display title and navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabumateTopAppBar(
//  canNavigateBack: Boolean,
//  navigateUp: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .shadow(
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        spotColor = Color.DarkGray
      )
  ) {
    TopAppBar(
      title = { Text(stringResource(R.string.app_name)) },
      //    navigationIcon = {
      //      if (canNavigateBack) {
      //        IconButton(onClick = navigateUp) {
      //          Icon(
      //            imageVector = Icons.Filled.ArrowBack,
      //            contentDescription = stringResource(R.string.back_button)
      //          )
      //        }
      //      }
      //    }
    )
  }
}

@Composable
fun InputBottomBar(
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
