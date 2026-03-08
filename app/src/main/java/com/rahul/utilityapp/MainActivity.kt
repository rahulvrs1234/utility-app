package com.rahul.utilityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.rahul.utilityapp.ui.home.HomeScreen
import com.rahul.utilityapp.ui.settings.SettingsScreen
import com.rahul.utilityapp.ui.theme.UtilityAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var screen by remember { mutableStateOf("home") }
            var darkMode by remember { mutableStateOf(false) }

            UtilityAppTheme(darkTheme = darkMode) {

                Scaffold(

                    bottomBar = {

                        NavigationBar {

                            NavigationBarItem(
                                selected = screen == "home",
                                onClick = { screen = "home" },
                                icon = { Icon(Icons.Default.Home, null) },
                                label = { Text("Home") }
                            )

                            NavigationBarItem(
                                selected = screen == "settings",
                                onClick = { screen = "settings" },
                                icon = { Icon(Icons.Default.Settings, null) },
                                label = { Text("Settings") }
                            )
                        }
                    }

                ) {

                    when (screen) {

                        "home" -> HomeScreen()

                        "settings" -> SettingsScreen(
                            darkMode = darkMode,
                            onToggle = { darkMode = it }
                        )
                    }
                }
            }
        }
    }
}