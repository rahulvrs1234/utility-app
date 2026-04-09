package com.rahul.utilityapp.ui.theme

enum class AppThemeMode(
    val title: String,
    val description: String
) {
    Light(
        title = "Light",
        description = "Bright and clean for daytime use."
    ),
    Dark(
        title = "Dark",
        description = "Calm contrast for late-night use."
    ),
    Purple(
        title = "Purple",
        description = "A richer look with a softer purple mood."
    );

    companion object {
        fun fromName(name: String?): AppThemeMode =
            values().firstOrNull { it.name == name } ?: Light
    }
}
