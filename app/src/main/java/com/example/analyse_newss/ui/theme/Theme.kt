package com.example.analyse_newss.ui.theme

import android.app.Activity
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

private val DarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    secondary = MintGreen,
    tertiary = TextGray,
    background = MidnightBlue,
    surface = DarkSlate,
    onPrimary = MidnightBlue,
    onSecondary = MidnightBlue,
    onTertiary = MidnightBlue,
    onBackground = TextWhite,
    onSurface = TextWhite,
    surfaceVariant = DarkSlate,
    onSurfaceVariant = TextGray,
    error = ErrorRed
)

// We want a dark premium look always, so we might map Light scheme to Dark too, 
// or just slightly lighter versions. For now, let's force the dark palette concept 
// but fully implemented.
private val LightColorScheme = DarkColorScheme // Force Premium Dark Mode for this design

@Composable
fun Analyse_newssTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We disable dynamic color to ensure our Premium Design is used
    dynamicColor: Boolean = false, 
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        
        // Always use DarkColorScheme for that premium finance look
        else -> DarkColorScheme 
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}