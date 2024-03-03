package com.example.vocabumate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vocabumate.R

val Pacifico = FontFamily(
  Font(R.font.pacifico_regular)
)

val Oregano = FontFamily(
  Font(R.font.oregano_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
  displayLarge = TextStyle(
    fontFamily = Pacifico,
    fontSize = 24.sp
  ),
  displayMedium = TextStyle(
    fontFamily = Pacifico,
    fontSize = 18.sp
  ),
  displaySmall = TextStyle(
    fontFamily = Oregano
  ),
  headlineLarge = TextStyle(
    fontFamily = Oregano
  ),
  headlineMedium = TextStyle(
    fontFamily = Oregano
  ),
  headlineSmall = TextStyle(
    fontFamily = Oregano
  ),
  titleLarge = TextStyle(
    fontFamily = Oregano,
    fontSize = 24.sp
  ),
  titleMedium = TextStyle(
    fontFamily = Oregano
  ),
  titleSmall = TextStyle(
    fontFamily = Oregano
  ),
  bodyLarge = TextStyle(
    fontFamily = Oregano,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
  ),
  bodyMedium = TextStyle(
    fontFamily = Oregano
  ),
  bodySmall = TextStyle(
    fontFamily = Oregano
  ),
  labelLarge = TextStyle(
    fontFamily = Oregano
  ),
  labelMedium = TextStyle(
    fontFamily = Oregano
  ),
  labelSmall = TextStyle(
    fontFamily = Oregano,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
  ),
)
