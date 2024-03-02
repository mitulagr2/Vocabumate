package com.example.vocabumate.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabumate.R
import com.example.vocabumate.data.Word
import com.example.vocabumate.ui.AppViewModelProvider
import com.example.vocabumate.ui.components.VocabumateTopAppBar
import com.example.vocabumate.ui.navigation.NavigationDestination
import com.example.vocabumate.ui.viewmodels.LikesViewModel

object LikesDestination : NavigationDestination {
  override val route = "likes"
  override val titleRes = R.string.app_name
}

@Composable
fun LikesScreen(
  navigateTo: (String) -> Unit,
  navigateToWordDetails: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: LikesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
  val localList by viewModel.allLocalWordsState.collectAsState()

  Scaffold(
    modifier = modifier,
    topBar = {
      VocabumateTopAppBar(navigateTo)
    },
  ) { innerPadding ->
    LikesBody(
      wordList = localList,
      onWordClick = navigateToWordDetails,
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    )
  }
}

@Composable
private fun LikesBody(
  wordList: List<Word>,
  onWordClick: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
  ) {
    if (wordList.isEmpty()) {
      Text(
        text = "Oops! No words to show.",
        textAlign = TextAlign.Center,
      )
    } else {
      WordList(
        wordList = wordList,
        onWordClick = { onWordClick(it) },
      )
    }
  }
}

@Composable
private fun WordList(
  wordList: List<Word>, onWordClick: (String) -> Unit, modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier) {
    items(items = wordList) { item ->
      WordCard(word = item,
        modifier = Modifier
          .clickable { onWordClick(item.word) })
    }
  }
}

@Composable
private fun WordCard(
  word: Word, modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = word.word,
        style = MaterialTheme.typography.titleLarge,
      )
    }
  }
}
