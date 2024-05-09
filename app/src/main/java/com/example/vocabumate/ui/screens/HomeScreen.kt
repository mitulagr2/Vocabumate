package com.example.vocabumate.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabumate.R
import com.example.vocabumate.data.Word
import com.example.vocabumate.ui.AppViewModelProvider
import com.example.vocabumate.ui.components.CardData
import com.example.vocabumate.ui.components.FlashCard
import com.example.vocabumate.ui.components.InputBottomBar
import com.example.vocabumate.ui.navigation.NavigationDestination
import com.example.vocabumate.ui.viewmodels.WordsViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

object HomeDestination : NavigationDestination {
  override val route = "home"
  override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
  navigateToWordDetails: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: WordsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
  val localList by viewModel.allLocalWordsState.collectAsState()
  val preferencesState by viewModel.preferencesState.collectAsState()
  val coroutineScope = rememberCoroutineScope()

  Column {
    Column(
      modifier = modifier
        .padding(vertical = 24.dp)
        .weight(1F)
    ) {
      Row {
        Text(
          text = "Revise",
          style = MaterialTheme.typography.headlineMedium,
          modifier = Modifier
            .padding(start = 32.dp)
            .fillMaxWidth()
        )
      }
      Spacer(modifier = Modifier.height(40.dp))
      if (localList.size > 3) {
        Row(
          modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {

          preferencesState.reviseSet.map {
            val cur = Json.decodeFromString<Word>(it)

            Spacer(modifier = Modifier.width(32.dp))
            FlashCard(
              data = CardData(
                info = cur,
                isRevise = true,
                surfaceAction = { coroutineScope.launch { viewModel.updateReviseSet(cur.word) } }
              ),
              modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 64).dp)
            )
          }
          Spacer(modifier = Modifier.width(32.dp))
        }
      } else {
        Text(
          text = "Like more words to start revising.",
          style = MaterialTheme.typography.bodyLarge,
          modifier = Modifier
            .padding(start = 32.dp)
            .fillMaxWidth()
        )
      }
    }
    InputBottomBar(navigateToWordDetails)
  }
}
