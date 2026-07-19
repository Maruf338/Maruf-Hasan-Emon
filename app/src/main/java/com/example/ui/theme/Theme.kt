package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ObsidianColorScheme = darkColorScheme(
    primary = CyberBlue,
    onPrimary = Color(0xFF003543),
    primaryContainer = CyberBlueDim,
    onPrimaryContainer = ObsidianOnSurface,
    secondary = EmeraldGreen,
    onSecondary = EmeraldGreenOn,
    secondaryContainer = EmeraldGreenContainer,
    onSecondaryContainer = ObsidianOnSurface,
    background = ObsidianBackground,
    onBackground = ObsidianOnSurface,
    surface = ObsidianSurface,
    onSurface = ObsidianOnSurface,
    surfaceVariant = ObsidianSurfaceContainer,
    onSurfaceVariant = ObsidianOnSurfaceVariant,
    error = DangerRed,
    onError = DangerRedOn,
    errorContainer = DangerRedContainer,
    onErrorContainer = ObsidianOnSurface
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force dark mode to preserve high-tech brand premium design
    dynamicColor: Boolean = false, // Disable dynamic colors to keep brand's exact styling
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = ObsidianColorScheme,
        typography = Typography,
        content = content
    )
}
