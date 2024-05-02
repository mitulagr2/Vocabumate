package com.example.vocabumate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object WordDetailsDestination : NavigationDestination {
  override val route = "word_details"
  override val titleRes = R.string.app_name
  const val wordArg = "wordArg"
  val routeWithArgs = "$route/{${wordArg}}"
}

@Composable
fun WordDetailsScreen(
  modifier: Modifier = Modifier,
  viewModel: WordsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
  val coroutineScope = rememberCoroutineScope()
  val localList by viewModel.allLocalWordsState.collectAsState()
  var wordData by rememberSaveable {
    mutableStateOf(Json.encodeToString(viewModel.loadingWord))
  }
  val word = Json.decodeFromString<Word>(wordData)

  LaunchedEffect(Unit) {
    coroutineScope.launch(Dispatchers.IO) {
      wordData = viewModel.getWord() ?: Json.encodeToString(viewModel.errorWord)
    }
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
