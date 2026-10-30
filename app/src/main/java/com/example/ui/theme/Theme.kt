package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color(0xFF031626),
    primaryContainer = Color(0xFF004D5A),
    onPrimaryContainer = Color(0xFFC7F8FF),
    secondary = MedicalTeal,
    onSecondary = Color(0xFF00382E),
    secondaryContainer = Color(0xFF005143),
    onSecondaryContainer = Color(0xFFB8F6E8),
    tertiary = ElectricBlue,
    onTertiary = Color.White,
    background = DarkNavyBg,
    onBackground = TextWhite,
    surface = DarkNavySurface,
    onSurface = TextWhite,
    surfaceVariant = DarkNavyCard,
    onSurfaceVariant = TextMuted,
    outline = DarkNavyBorder,
    error = EmergencyRed,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF007A8A),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD4F6FC),
    onPrimaryContainer = Color(0xFF002026),
    secondary = Color(0xFF00897B),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCEF4ED),
    onSecondaryContainer = Color(0xFF00201B),
    tertiary = ElectricBlue,
    onTertiary = Color.White,
    background = LightBg,
    onBackground = TextDark,
    surface = LightSurface,
    onSurface = TextDark,
    surfaceVariant = LightCard,
    onSurfaceVariant = Color(0xFF4A627A),
    outline = LightBorder,
    error = EmergencyRed,
    onError = Color.White
)

@Composable
fun GujaratAarogyaTheme(
    darkTheme: Boolean = true, // Default to futuristic dark mode
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
