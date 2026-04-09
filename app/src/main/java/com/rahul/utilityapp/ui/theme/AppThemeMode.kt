package com.rahul.utilityapp.ui.theme

enum class AppThemeMode(
    val title: String,
    val description: String
) {
    Light(
        title = "Light",
        description = "Bright and clean."
    ),
    Dark(
        title = "Dark",
        description = "Calm for late-night use."
    ),
    Purple(
        title = "Purple",
        description = "Soft purple mood."
    );

    companion object {
        fun fromName(name: String?): AppThemeMode =
            values().firstOrNull { it.name == name } ?: Light
    }
}
