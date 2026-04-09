package com.rahul.utilityapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.rahul.utilityapp.ui.home.HomeScreen
import com.rahul.utilityapp.ui.navigation.UtilityDestination
import com.rahul.utilityapp.ui.settings.SettingsScreen
import com.rahul.utilityapp.ui.theme.UtilityAppTheme
import com.rahul.utilityapp.utils.NotificationHelper
import com.rahul.utilityapp.utils.SettingsStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        NotificationHelper.createChannel(this)

        setContent {
            val activity = this@MainActivity
            var currentDestination by rememberSaveable {
                mutableStateOf(UtilityDestination.Home)
            }
            var themeMode by rememberSaveable {
                mutableStateOf(SettingsStorage.getThemeMode(activity))
            }

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { }

            LaunchedEffect(Unit) {
                if (
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }

            UtilityAppTheme(themeMode = themeMode) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {
                        NavigationBar(
                            tonalElevation = 0.dp,
                            containerColor = MaterialTheme.colorScheme.surface
                        ) {
                            UtilityDestination.values().forEach { destination ->
                                NavigationBarItem(
                                    selected = currentDestination == destination,
                                    onClick = { currentDestination = destination },
                                    icon = {
                                        Icon(
                                            imageVector = destination.icon,
                                            contentDescription = destination.label
                                        )
                                    },
                                    label = { Text(destination.label) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    when (currentDestination) {
                        UtilityDestination.Home -> HomeScreen(
                            modifier = Modifier
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding)
                        )

                        UtilityDestination.Settings -> SettingsScreen(
                            modifier = Modifier
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding),
                            themeMode = themeMode,
                            onThemeSelected = { selectedTheme ->
                                themeMode = selectedTheme
                                SettingsStorage.setThemeMode(activity, selectedTheme)
                            }
                        )
                    }
                }
            }
        }
    }
}
