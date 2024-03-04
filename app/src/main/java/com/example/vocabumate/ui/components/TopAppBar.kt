package com.example.vocabumate.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vocabumate.R
import com.example.vocabumate.ui.theme.VocabumateTheme

/**
 * App bar to display title and navigation.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TopAppBar(
  navigateToHome: () -> Unit,
  navigateToDaily: () -> Unit,
  navigateToLikes: () -> Unit,
  navigateToDiscover: () -> Unit,
  navigateToAnalytics: () -> Unit,
  navigateToProfile: () -> Unit,
  modifier: Modifier = Modifier,
  scrollBehavior: TopAppBarScrollBehavior? = null
) {
  val menu = listOf(
    MenuData(
      text = "Likes",
      action = navigateToLikes,
      icon = Icons.Filled.Star,
      gradient = Color(0xFF4B33B6) to Color(0xFFAD61D0)
    ),
//    MenuData(
//      text = "Discover",
//      action = navigateToDiscover,
//      icon = Icons.Filled.Home,
//      gradient = Color(0xFFFF7711) to Color(0xFFFFBB88)
//    ),
//    MenuData(
//      text = "Analytics",
//      action = navigateToAnalytics,
//      icon = Icons.Filled.Info,
//      gradient = Color(0xFF68D9FF) to Color(0xFFCFF3FF)
//    ),
    MenuData(
      text = "Profile",
      action = navigateToProfile,
      icon = Icons.Filled.Person,
      gradient = Color(0xFFE575FF) to Color(0xFFF3BEFF)
    )
  )

  var isOpen: Boolean by remember {
    mutableStateOf(false)
  }

  Surface(
    modifier = modifier
      .shadow(
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        spotColor = Color.DarkGray
      )
      .clickable { isOpen = !isOpen }
  ) {
    Column {
      TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
          Text(
            stringResource(R.string.app_name),
            modifier = Modifier.clickable { navigateToHome() },
            style = MaterialTheme.typography.displayLarge
          )
        },
        actions = {
          IconButton(
            onClick = { navigateToDaily() },
            modifier = Modifier.padding(top = 8.dp)
          ) {
            Icon(Icons.Filled.DateRange, contentDescription = "Daily word")
          }
        }
      )
      if (isOpen) {
        FlowRow(
          maxItemsInEachRow = 2,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
          menu.forEach { it ->
            MenuItem(
              data = it,
              modifier = Modifier.weight(1F)
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
  VocabumateTheme {
    TopAppBar(
      navigateToHome = { },
      navigateToDaily = { },
      navigateToLikes = { },
      navigateToDiscover = { },
      navigateToAnalytics = { },
      navigateToProfile = { },
    )
  }
}
