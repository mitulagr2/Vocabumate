package com.example.vocabumate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vocabumate.ui.theme.VocabumateTheme

data class MenuData(
  val text: String,
  val action: () -> Unit,
  val icon: ImageVector,
  val gradient: Pair<Color, Color>
)

@Composable
fun MenuItem(
  data: MenuData,
  modifier: Modifier = Modifier
) {
  Button(
    onClick = data.action,
    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
    modifier = modifier
      .height(124.dp)
      .padding(start = 16.dp, top = 24.dp, end = 16.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(
        brush = Brush.linearGradient(
          colors = listOf(data.gradient.first, data.gradient.second),
          start = Offset(0f, Float.POSITIVE_INFINITY),
          end = Offset(Float.POSITIVE_INFINITY, 0f)
        )
      )
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(
        imageVector = data.icon,
        contentDescription = data.text,
        modifier = Modifier.size(32.dp)
      )
      Text(text = data.text, style = MaterialTheme.typography.titleLarge)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MenuItemPreview() {
  VocabumateTheme {
    MenuItem(
      MenuData(
        text = "Likes",
        action = { },
        icon = Icons.Filled.Star,
        gradient = Color(0xFFFF7711) to Color(0xFFFFBB88)
      ),
      modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
    )
  }
}
