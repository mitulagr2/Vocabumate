package com.example.vocabumate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vocabumate.ui.screens.AnalyticsDestination
import com.example.vocabumate.ui.screens.AnalyticsScreen
import com.example.vocabumate.ui.screens.DailyDestination
import com.example.vocabumate.ui.screens.DailyScreen
import com.example.vocabumate.ui.screens.DiscoverDestination
import com.example.vocabumate.ui.screens.DiscoverScreen
import com.example.vocabumate.ui.screens.HomeDestination
import com.example.vocabumate.ui.screens.HomeScreen
import com.example.vocabumate.ui.screens.LikesDestination
import com.example.vocabumate.ui.screens.LikesScreen
import com.example.vocabumate.ui.screens.LoginDestination
import com.example.vocabumate.ui.screens.LoginScreen
import com.example.vocabumate.ui.screens.ProfileDestination
import com.example.vocabumate.ui.screens.ProfileScreen
import com.example.vocabumate.ui.screens.WordDetailsDestination
import com.example.vocabumate.ui.screens.WordDetailsScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun VocabumateNavHost(
  navController: NavHostController,
  modifier: Modifier = Modifier,
) {
  NavHost(
    navController = navController,
    startDestination = HomeDestination.route,
    modifier = modifier
  ) {
    composable(route = HomeDestination.route) {
      NavItem(navController = navController) {
        HomeScreen(
          navigateToWordDetails = { navController.navigate("${WordDetailsDestination.route}/$it") },
        )
      }
    }
    composable(route = LikesDestination.route) {
      NavItem(navController = navController) {
        LikesScreen(
          navigateToWordDetails = { navController.navigate("${WordDetailsDestination.route}/$it") },
        )
      }
    }
    composable(route = DiscoverDestination.route) {
      NavItem(navController = navController) {
        DiscoverScreen()
      }
    }
    composable(route = AnalyticsDestination.route) {
      NavItem(navController = navController) {
        AnalyticsScreen()
      }
    }
    composable(route = ProfileDestination.route) {
      NavItem(navController = navController) {
        ProfileScreen()
      }
    }
    composable(
      route = WordDetailsDestination.routeWithArgs,
      arguments = listOf(navArgument(WordDetailsDestination.wordArg) {
        type = NavType.StringType
      })
    ) {
      NavItem(navController = navController) {
        WordDetailsScreen()
      }
    }
    composable(route = DailyDestination.route) {
      NavItem(navController = navController) {
        DailyScreen()
      }
    }
    composable(route = LoginDestination.route) {
      NavItem(navController = navController) {
        LoginScreen()
      }
    }
  }
}
