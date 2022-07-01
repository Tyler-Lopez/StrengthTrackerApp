package com.himanshoe.kalendar.common.theme
/*
* MIT License
*
* Copyright (c) 2022 Himanshu Singh
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme
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


    tertiary = Violet80,
    onTertiary = Violet20,
    tertiaryContainer = Violet30,
    onTertiaryContainer = Violet90,

    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,

    background = GreyBlue10,
    onBackground = GreyBlue90,

    surface = GreyBlue30,
    onSurface = GreyBlue80,
    inverseSurface = GreyBlue90,
    inverseOnSurface = GreyBlue10,

    surfaceVariant = GreyBlue30,
    onSurfaceVariant = GreyBlue80,
    outline = GreyBlue80
)

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

    surfaceVariant = GreyTeal90,
    onSurfaceVariant = GreyTeal30,

    outline = GreyTeal50






    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
     */
)

object KalendarTheme {
    val colors: KalendarColor = colorPalette()
}

@Composable
fun ProvideKalendarTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        content = content
    )
}

@Composable
fun KalendarTheme(
    content: @Composable () -> Unit,
) {
    val colors = when {
        isSystemInDarkTheme() -> DarkColorPalette
        else -> LightColorPalette
    }
    ProvideKalendarTheme {
        MaterialTheme(
            colorScheme = colors,
            content = content
        )
    }
}
