package com.rahul.utilityapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF7C4DFF),
    secondary = Color(0xFFB388FF),
    background = Color(0xFFF3EFFF)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFB388FF),
    secondary = Color(0xFF7C4DFF),
    background = Color(0xFF121212)
)

@Composable
fun UtilityAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography(),
        content = content
    )
}