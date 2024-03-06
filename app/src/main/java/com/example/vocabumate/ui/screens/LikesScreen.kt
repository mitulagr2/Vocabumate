package com.example.vocabumate.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabumate.R
import com.example.vocabumate.capitalize
import com.example.vocabumate.data.Word
import com.example.vocabumate.ui.AppViewModelProvider
import com.example.vocabumate.ui.navigation.NavigationDestination
import com.example.vocabumate.ui.viewmodels.LikesViewModel

object LikesDestination : NavigationDestination {
  override val route = "likes"
  override val titleRes = R.string.app_name
}

@Composable
fun LikesScreen(
  navigateToWordDetails: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: LikesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
  val localList by viewModel.allLocalWordsState.collectAsState()

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier.padding(vertical = 24.dp)
  ) {
    Row {
      Text(
        text = "Likes",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
          .padding(start = 32.dp)
          .fillMaxWidth()
      )
    }
    Spacer(modifier = Modifier.height(16.dp))
    if (localList.isEmpty()) {
      Text(
        text = stringResource(R.string.empty_list_local_message),
        style = MaterialTheme.typography.bodyLarge,
      )
    } else {
      WordList(
        wordList = localList,
        onWordClick = { navigateToWordDetails(it) },
        unlikeWord = viewModel::deleteWord
      )
    }
  }
}

@Composable
private fun WordList(
  wordList: List<Word>,
  onWordClick: (String) -> Unit,
  unlikeWord: (Word) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier) {
    items(items = wordList, key = { item -> item.word }) { item ->
      WordCard(
        word = item,
        unlike = { unlikeWord(item) },
        modifier = Modifier
          .clickable { onWordClick(item.word) }
      )
      Divider(color = Color(0xA3DDDDDD), thickness = 1.dp)
    }
  }
}

@Composable
private fun WordCard(
  word: Word, unlike: () -> Unit, modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
//    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 24.dp)
    ) {
      Text(
        text = word.word.capitalize(),
        style = MaterialTheme.typography.titleMedium,
      )
      IconButton(
        onClick = unlike,
        modifier = Modifier.padding(top = 4.dp)
      ) {
        Icon(Icons.Filled.Favorite, contentDescription = "Unlike")
      }
    }
  }
}
