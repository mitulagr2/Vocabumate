package com.example.vocabumate.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.example.vocabumate.ui.components.TopAppBar
import com.example.vocabumate.ui.screens.AnalyticsDestination
import com.example.vocabumate.ui.screens.DailyDestination
import com.example.vocabumate.ui.screens.DiscoverDestination
import com.example.vocabumate.ui.screens.HomeDestination
import com.example.vocabumate.ui.screens.LikesDestination
import com.example.vocabumate.ui.screens.ProfileDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavItem(
  navController: NavHostController,
  content: @Composable () -> Unit
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TopAppBar(
        navigateToHome = { navController.popBackStack(HomeDestination.route, false) },
        navigateToDaily = { navController.navigate(DailyDestination.route) },
        navigateToLikes = { navController.navigate(LikesDestination.route) },
        navigateToDiscover = { navController.navigate(DiscoverDestination.route) },
        navigateToAnalytics = { navController.navigate(AnalyticsDestination.route) },
        navigateToProfile = { navController.navigate(ProfileDestination.route) },
        scrollBehavior = scrollBehavior
      )
    },
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    ) {
      content()
    }
  }
}
