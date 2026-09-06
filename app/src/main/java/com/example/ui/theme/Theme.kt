package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val KidsLightColorScheme = lightColorScheme(
    primary = KidsPrimary,
    onPrimary = Color.White,
    primaryContainer = KidsPrimaryContainer,
    onPrimaryContainer = Color(0xFF1B5E20),
    secondary = KidsSecondary,
    onSecondary = Color.White,
    secondaryContainer = KidsSecondaryContainer,
    onSecondaryContainer = KidsNaturalLavenderText,
    tertiary = KidsTertiary,
    onTertiary = Color.White,
    tertiaryContainer = KidsTertiaryContainer,
    onTertiaryContainer = Color(0xFF78350F),
    background = KidsBackground,
    onBackground = KidsTextPrimary,
    surface = KidsSurface,
    onSurface = KidsTextPrimary,
    surfaceVariant = KidsSurfaceVariant,
    onSurfaceVariant = KidsTextSecondary,
    outline = KidsNaturalBorder
)

private val KidsDarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color(0xFF1B5E20),
    primaryContainer = Color(0xFF2E7D32),
    onPrimaryContainer = Color(0xFFE8F5E9),
    secondary = Color(0xFFD0BCFF),
    onSecondary = Color(0xFF381E72),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFFFD54F),
    onTertiary = Color(0xFF3E2723),
    tertiaryContainer = Color(0xFF684E00),
    onTertiaryContainer = Color(0xFFFFF9C4),
    background = Color(0xFF1A1C19),
    onBackground = Color(0xFFE2E3DD),
    surface = Color(0xFF212420),
    onSurface = Color(0xFFE2E3DD),
    surfaceVariant = Color(0xFF2D312B),
    onSurfaceVariant = Color(0xFFC2C8BC),
    outline = Color(0xFF44483E)
)

@Composable
fun ArabicAlphabetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) KidsDarkColorScheme else KidsLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
