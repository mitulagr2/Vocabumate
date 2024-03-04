package com.example.vocabumate.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabumate.R
import com.example.vocabumate.data.Word
import com.example.vocabumate.ui.AppViewModelProvider
import com.example.vocabumate.ui.components.CardData
import com.example.vocabumate.ui.components.FlashCard
import com.example.vocabumate.ui.components.InputBottomBar
import com.example.vocabumate.ui.navigation.NavigationDestination
import com.example.vocabumate.ui.viewmodels.HomeViewModel

object HomeDestination : NavigationDestination {
  override val route = "home"
  override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
  navigateToWordDetails: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
  val localList by viewModel.allLocalWordsState.collectAsState()
  val (first, second, third) = Triple("legendary", "test", "wow") //hard coded

  Column {
    Column(
      modifier = modifier
        .padding(horizontal = 32.dp, vertical = 24.dp)
        .weight(1F)
    ) {
      Row {
        Text(
          text = "Revise",
          style = MaterialTheme.typography.headlineMedium
        )
      }
      Spacer(modifier = Modifier.height(40.dp))
      if (localList.size > 3) {
        Row(
          modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
          FlashCard(
            data = CardData(info = localList.find { it.word == first } ?: Word("", ""),
              isRevise = true),
            modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 64).dp)
          )
          Spacer(modifier = Modifier.width(32.dp))
          FlashCard(
            data = CardData(info = localList.find { it.word == second } ?: Word("", ""),
              isRevise = true),
            modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 64).dp)
          )
          Spacer(modifier = Modifier.width(32.dp))
          FlashCard(
            data = CardData(info = localList.find { it.word == third } ?: Word("", ""),
              isRevise = true),
            modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 64).dp)
          )
        }

      } else {
        Text(
          text = "Add more words to start revising.",
          style = MaterialTheme.typography.bodyLarge,
        )
      }
    }
    InputBottomBar(navigateToWordDetails)
  }
}
