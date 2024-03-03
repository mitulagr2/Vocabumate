package com.example.vocabumate.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vocabumate.data.Word
import com.example.vocabumate.ui.theme.VocabumateTheme

data class CardData(
  val info: Word,
  val likeAction: () -> Unit,
  val icon: ImageVector,
  val isRevise: Boolean = false,
  val surfaceAction: () -> Unit = {}
)

@Composable
fun FlashCard(
  data: CardData,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .padding(32.dp)
      .border(width = 1.dp, color = Color(0xA3DDDDDD), shape = RoundedCornerShape(8.dp))
      .clickable(enabled = data.isRevise) { data.surfaceAction() }
  ) {
    Column(
      modifier = Modifier
        .padding(32.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .padding(bottom = 16.dp)
          .fillMaxWidth()
      ) {
        Text(
          text = capitalize(data.info.word),
          style = MaterialTheme.typography.displayMedium
        )
        IconButton(
          onClick = data.likeAction,
          modifier = Modifier.padding(top = 4.dp)
        ) {
          Icon(data.icon, contentDescription = "Like")
        }
      }
      Text(
        text = data.info.meaning,
        style = MaterialTheme.typography.bodyLarge
      )
    }
  }
}

private fun capitalize(input: String) =
  input.substring(0, 1).uppercase() + input.substring(1).lowercase()

@Preview(showBackground = true)
@Composable
fun FlashCardPreview() {
  VocabumateTheme {
    FlashCard(
      data = CardData(
        info = Word("Word", "Lorem ipsum."),
        likeAction = { },
        icon = Icons.Filled.FavoriteBorder
      )
    )
  }
}
