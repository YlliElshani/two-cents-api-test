package com.yllielshani.twocentsdemo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable


private val DarkColors = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    background = AppBackground,
    onBackground = OnBackground,
    surface = CardBackground,
    onSurface = OnSurface
)


@Composable
fun TwoCentsDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) DarkColors else DarkColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes()
    ) {
        Surface(color = colors.background) {
            content()
        }
    }
}