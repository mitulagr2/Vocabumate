package com.example.vocabumate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vocabumate.ui.navigation.VocabumateNavHost

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun VocabumateApp(navController: NavHostController = rememberNavController()) {
  VocabumateNavHost(navController)
}
