package com.example.vocabumate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vocabumate.R
import com.example.vocabumate.ui.navigation.VocabumateNavHost
import com.example.vocabumate.ui.screens.HomeScreen

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun VocabumateApp(navController: NavHostController = rememberNavController()) {
  VocabumateNavHost(navController)
}
