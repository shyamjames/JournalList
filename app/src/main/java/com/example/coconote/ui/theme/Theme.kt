package com.example.coconote.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DesertSolaceColorScheme = lightColorScheme(
    primary = DesertPrimary,
    onPrimary = DesertOnPrimary,
    primaryContainer = DesertPrimaryContainer,
    onPrimaryContainer = DesertOnPrimaryContainer,
    secondary = DesertSecondary,
    secondaryContainer = DesertSecondaryContainer,
    onSecondaryContainer = DesertOnSecondaryContainer,
    tertiary = DesertTertiary,
    tertiaryContainer = DesertTertiaryContainer,
    onTertiaryContainer = DesertOnTertiaryContainer,
    background = DesertSurface,
    onBackground = DesertOnSurface,
    surface = DesertSurface,
    onSurface = DesertOnSurface,
    surfaceVariant = DesertSurfaceContainerHighest,
    onSurfaceVariant = DesertOnSurfaceVariant,
    outline = DesertOutline,
    outlineVariant = DesertOutlineVariant
)

@Composable
fun DesertSolaceTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DesertSolaceColorScheme,
        typography = Typography,
        content = content
    )
}
