package com.example.project.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val PrimaryBlue = Color(0xFF3772E7)

val White: Color
    @Composable
    get() = MaterialTheme.colorScheme.background

val TextPrimary: Color
    @Composable
    get() = MaterialTheme.colorScheme.onBackground

val TextSecondary: Color
    @Composable
    get() = MaterialTheme.colorScheme.onSurfaceVariant

val BackgroundGray: Color
    @Composable
    get() = MaterialTheme.colorScheme.surfaceVariant

val DarkGrey = Color(0xFFB5B5B5)

val SurfaceWhite: Color
    @Composable
    get() = MaterialTheme.colorScheme.background

val ErrorRed = Color(0xFFFF0000)

val SwitchTrackEnabled = Color(0xFF3772E7)

val SwitchTrackDisabled: Color
    @Composable
    get() = MaterialTheme.colorScheme.surfaceVariant

val SwitchThumbEnabled = Color(0xFFFFFFFF)

val SwitchThumbDisabled: Color
    @Composable
    get() = MaterialTheme.colorScheme.onSurfaceVariant
