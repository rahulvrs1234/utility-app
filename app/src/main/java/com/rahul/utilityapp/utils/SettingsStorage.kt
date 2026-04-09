package com.rahul.utilityapp.utils

import android.content.Context
import com.rahul.utilityapp.ui.theme.AppThemeMode

object SettingsStorage {
    private const val PREFERENCES_NAME = "utility_preferences"
    private const val LEGACY_DARK_MODE_KEY = "dark_mode_enabled"
    private const val THEME_MODE_KEY = "theme_mode"

    fun getThemeMode(context: Context): AppThemeMode {
        val preferences = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

        val storedTheme = preferences.getString(THEME_MODE_KEY, null)
        if (storedTheme != null) {
            return AppThemeMode.fromName(storedTheme)
        }

        val legacyDarkMode = preferences.getBoolean(LEGACY_DARK_MODE_KEY, false)
        return if (legacyDarkMode) AppThemeMode.Dark else AppThemeMode.Light
    }

    fun setThemeMode(
        context: Context,
        themeMode: AppThemeMode
    ) {
        context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        ).edit()
            .remove(LEGACY_DARK_MODE_KEY)
            .putString(THEME_MODE_KEY, themeMode.name)
            .apply()
    }
}
