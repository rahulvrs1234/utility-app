package com.rahul.utilityapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = OceanBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD8EEF5),
    onPrimaryContainer = LightText,
    secondary = MintGreen,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDCEFEA),
    onSecondaryContainer = LightText,
    tertiary = SkyTint,
    background = LightBackground,
    onBackground = LightText,
    surface = LightSurface,
    onSurface = LightText,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = Color(0xFF4B5D6E),
    outline = LightOutline
)

private val DarkColors = darkColorScheme(
    primary = SkyTint,
    onPrimary = DeepOcean,
    primaryContainer = Color(0xFF11374A),
    onPrimaryContainer = DarkText,
    secondary = Color(0xFF83D0BA),
    onSecondary = Color(0xFF0B2C23),
    secondaryContainer = Color(0xFF143C33),
    onSecondaryContainer = DarkText,
    tertiary = Color(0xFF6FC7E7),
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkSurface,
    onSurface = DarkText,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFB3C7D8),
    outline = DarkOutline
)

private val PurpleColors = darkColorScheme(
    primary = PurplePrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF45206B),
    onPrimaryContainer = PurpleText,
    secondary = PurpleSecondary,
    onSecondary = Color(0xFF2A133F),
    secondaryContainer = Color(0xFF4D2673),
    onSecondaryContainer = PurpleText,
    tertiary = Color(0xFFF0ABFC),
    background = PurpleBackground,
    onBackground = PurpleText,
    surface = PurpleSurface,
    onSurface = PurpleText,
    surfaceVariant = PurpleSurfaceVariant,
    onSurfaceVariant = Color(0xFFE0D0F5),
    outline = PurpleOutline
)

@Composable
fun UtilityAppTheme(
    themeMode: AppThemeMode,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = when (themeMode) {
            AppThemeMode.Light -> LightColors
            AppThemeMode.Dark -> DarkColors
            AppThemeMode.Purple -> PurpleColors
        },
        typography = UtilityTypography,
        shapes = UtilityShapes,
        content = content
    )
}
