package com.example.vocabumate.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vocabumate.R
import com.example.vocabumate.ui.screens.AnalyticsDestination
import com.example.vocabumate.ui.screens.DailyDestination
import com.example.vocabumate.ui.screens.DiscoverDestination
import com.example.vocabumate.ui.screens.HomeDestination
import com.example.vocabumate.ui.screens.LikesDestination
import com.example.vocabumate.ui.screens.ProfileDestination

/**
 * App bar to display title and navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
  navigateTo: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .shadow(
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        spotColor = Color.DarkGray
      )
  ) {
    Column {
      TopAppBar(
        title = {
          Text(
            stringResource(R.string.app_name),
            modifier = Modifier.clickable { navigateTo(HomeDestination.route) }
          )
        },
      )
      Button(onClick = { navigateTo(LikesDestination.route) }) {
        Text(text = "Likes")
      }
      Button(onClick = { navigateTo(DiscoverDestination.route) }) {
        Text(text = "Discover")
      }
      Button(onClick = { navigateTo(AnalyticsDestination.route) }) {
        Text(text = "Analytics")
      }
      Button(onClick = { navigateTo(ProfileDestination.route) }) {
        Text(text = "Profile")
      }
      IconButton(onClick = { navigateTo(DailyDestination.route) }) {
        Icon(Icons.Filled.DateRange, contentDescription = "Daily word")
      }
    }
  }
}
