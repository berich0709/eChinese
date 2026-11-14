package com.echinese.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Brand = Color(0xFF0B5D52)
val BrandSoft = Color(0xFFE8F1EE)
val Ink = Color(0xFF152421)
val Muted = Color(0xFF7C8A84)
val Danger = Color(0xFFE5484D)
val Success = Color(0xFF2FA36B)

private val Light = lightColorScheme(primary = Brand, onPrimary = Color.White,
    background = Color(0xFFF7F9F8), surface = Color.White, onSurface = Ink)

@Composable
fun EChineseTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColorScheme(primary = Brand) else Light, content = content)
}
