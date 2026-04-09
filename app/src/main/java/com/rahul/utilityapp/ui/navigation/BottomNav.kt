package com.rahul.utilityapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class UtilityDestination(
    val label: String,
    val icon: ImageVector
) {
    Home(
        label = "Home",
        icon = Icons.Default.Home
    ),
    Settings(
        label = "Settings",
        icon = Icons.Default.Settings
    )
}
