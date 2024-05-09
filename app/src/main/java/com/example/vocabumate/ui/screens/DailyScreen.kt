package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabumate.R
import com.example.vocabumate.data.Word
import com.example.vocabumate.ui.AppViewModelProvider
import com.example.vocabumate.ui.components.CardData
import com.example.vocabumate.ui.components.FlashCard
import com.example.vocabumate.ui.navigation.NavigationDestination
import com.example.vocabumate.ui.viewmodels.WordsViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object DailyDestination : NavigationDestination {
  override val route = "daily"
  override val titleRes = R.string.app_name
}

@Composable
fun DailyScreen(
  modifier: Modifier = Modifier,
  viewModel: WordsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
  val coroutineScope = rememberCoroutineScope()
  val localList by viewModel.allLocalWordsState.collectAsState()
  val preferencesState by viewModel.preferencesState.collectAsState()
  var wordData by rememberSaveable {
    mutableStateOf(Json.encodeToString(viewModel.loadingWord))
  }
  val word = Json.decodeFromString<Word>(wordData)

  LaunchedEffect(Unit) {
    val prev = preferencesState.lastTime
    val today = (System.currentTimeMillis() / (1000 * 3600 * 24)).toInt()
    val dailyWord = Json.decodeFromString<Word>(preferencesState.dailyWord)
    wordData =
      if ((today - prev < 1) && dailyWord.word.isNotEmpty()) preferencesState.dailyWord else viewModel.getDaily()
  }

  LaunchedEffect(localList.size) {
    if (localList.size == 3) viewModel.initReviseSet()
    if (localList.any { it.word == word.word }) viewModel.isSaved = true
//    localList.foldRight(false) {val, res ->
//      if(it.word == word.word) {
//        return@foldRight true
//      } else {
//        res
//      }
//    }
  }

  Column(modifier = modifier.padding(horizontal = 32.dp, vertical = 24.dp)) {
    Row {
      Text(
        text = "Word of the day",
        style = MaterialTheme.typography.headlineMedium
      )
    }
    Spacer(modifier = Modifier.height(16.dp))
    FlashCard(
      data = CardData(
        info = word,
        likeAction = {
          coroutineScope.launch {
            if (viewModel.isSaved) viewModel.deleteWord(word) else viewModel.saveWord(word)
          }
        },
        icon = if (viewModel.isSaved) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
      )
    )
  }
}
