package com.example.vocabumate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.vocabumate.R
import com.example.vocabumate.sanitizeWord

@Composable
fun InputBottomBar(
  navigateToWordDetails: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var query by rememberSaveable {
    mutableStateOf("")
  }

  Row(
    modifier = modifier
      .shadow(elevation = 16.dp, spotColor = Color.DarkGray)
      .background(MaterialTheme.colorScheme.surface)
      .padding(start = 8.dp, top = 16.dp, end = 24.dp, bottom = 16.dp)
      .height(IntrinsicSize.Min)
  ) {
    TextField(
      value = query,
      singleLine = true,
      modifier = Modifier
        .weight(1F)
        .fillMaxHeight(),
      colors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
      ),
      onValueChange = {
        query = it.sanitizeWord()
      },
      placeholder = { Text(stringResource(R.string.word_input_placeholder)) },
      keyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Go
      ),
      keyboardActions = KeyboardActions(
        onGo = {
          navigateToWordDetails(query.trim().replace(Regex("\\s+"), " ").lowercase())
          query = ""
        }
      )
    )
    FloatingActionButton(
      onClick = {
        navigateToWordDetails(query.trim().replace(Regex("\\s+"), " ").lowercase())
        query = ""
      }
    ) {
      Icon(Icons.Filled.Search, contentDescription = "Search")
    }
  }
}
