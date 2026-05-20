package com.example.project.data.preferences

import android.content.Context

class ThemePreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isDarkThemeEnabled(): Boolean = sharedPreferences.getBoolean(KEY_DARK_THEME, false)

    fun setDarkThemeEnabled(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_DARK_THEME, isEnabled).apply()
    }

    private companion object {
        const val PREFS_NAME = "theme_preferences"
        const val KEY_DARK_THEME = "dark_theme_enabled"
    }
}
