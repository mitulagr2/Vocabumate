package com.example.vocabumate.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.vocabumate.HomeScreen
import com.example.vocabumate.R

@Composable
fun App(modifier: Modifier = Modifier) {
  Scaffold(
    topBar = {
      AppTopBar()
    },
    modifier = modifier
  ) { innerPadding ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      color = MaterialTheme.colorScheme.background
    ) {
      HomeScreen()
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
//  canNavigateBack: Boolean,
//  navigateUp: () -> Unit,
  modifier: Modifier = Modifier
) {
  TopAppBar(
    title = { Text(stringResource(R.string.app_name)) },
    colors = TopAppBarDefaults.mediumTopAppBarColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer
    ),
    modifier = modifier,
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
