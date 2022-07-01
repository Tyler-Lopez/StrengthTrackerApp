package com.company.strengthtracker.ui.theme
//OLD IMPORTS
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.darkColors
//import androidx.compose.material.lightColors

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
private val DarkColorPalette = darkColorScheme(
    primary = Grey80,
    onPrimary = Grey20,
    primaryContainer = Grey30,
    onPrimaryContainer = Grey90,
    inversePrimary = Grey40,

    secondary = DarkGrey80,
    onSecondary = DarkGrey20,
    secondaryContainer = DarkGrey30,
    onSecondaryContainer = DarkGrey90,


    tertiary = Grey80,
    onTertiary = Grey20,
    tertiaryContainer = Grey40,
    onTertiaryContainer = Grey90,

    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,

    background = GreyBlue10,
    onBackground = GreyBlue90,

    surface = GreyTeal30,
    onSurface = GreyTeal80,

    inverseSurface = GreyBlue90,
    inverseOnSurface = GreyBlue10,

    surfaceVariant = GreyTeal30,
    onSurfaceVariant = GreyTeal80,
    outline = GreyBlue80
)

private val LightColorPalette = lightColors(
    primary = Color.DarkGray,
    primaryVariant = paleBlue,
    secondary = Teal200,
private val LightColorPalette = lightColorScheme(
    primary = Grey40,
    onPrimary = Color.White,
    primaryContainer = Grey90,
    onPrimaryContainer = Grey10,
    inversePrimary = Grey80,

    secondary = DarkGrey40,
    onSecondary = Color.White,
    secondaryContainer = DarkGrey90,
    onSecondaryContainer = DarkGrey10,

    tertiary = Violet40,
    onTertiary = Color.White,
    tertiaryContainer = Violet90,
    onTertiaryContainer = Violet10,

    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,

    background = GreyBlue95,
    onBackground = GreyBlue10,

    surface = GreyTeal90,
    onSurface = GreyTeal30,

    inverseSurface = GreyBlue20,
    inverseOnSurface = GreyBlue95,

    surfaceVariant = GreyBlue90,
    onSurfaceVariant = GreyBlue30,

    outline = GreyTeal50






    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,

     */
    onSurface = Color.Black

)

@Composable
fun StrengthTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    val colors = when {
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}