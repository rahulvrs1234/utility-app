package com.rahul.utilityapp.ui.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rahul.utilityapp.data.model.Reminder
import com.rahul.utilityapp.utils.AlarmHelper
import com.rahul.utilityapp.viewmodel.ReminderViewModel
import com.rahul.utilityapp.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val weatherVM: WeatherViewModel = viewModel()
    val reminderVM: ReminderViewModel = viewModel()

    val weather = weatherVM.weather.value
    val isLoading = weatherVM.isLoading.value
    val errorMessage = weatherVM.errorMessage.value
    val reminders by reminderVM.reminders.collectAsState()

    var text by rememberSaveable { mutableStateOf("") }
    var selectedTime by rememberSaveable { mutableStateOf<Long?>(null) }

    val dateFormatter = remember {
        SimpleDateFormat("EEE, dd MMM - HH:mm", Locale.getDefault())
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 24.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Utility Hub",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "One place for today's weather snapshot and the reminders you do not want to miss.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Singapore weather",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "Live conditions from Open-Meteo",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(onClick = weatherVM::refreshWeather) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh weather"
                            )
                        }
                    }

                    when {
                        isLoading && weather == null -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(96.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        weather != null -> {
                            Text(
                                text = weatherLabel(weather.current_weather.weathercode),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                WeatherMetric(
                                    modifier = Modifier.fillMaxWidth(),
                                    label = "Temperature",
                                    value = "${weather.current_weather.temperature.toInt()} C"
                                )
                                WeatherMetric(
                                    modifier = Modifier.fillMaxWidth(),
                                    label = "Wind speed",
                                    value = "${weather.current_weather.windspeed.toInt()} km/h"
                                )
                            }
                        }

                        errorMessage != null -> {
                            Text(
                                text = errorMessage,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        else -> {
                            Text(
                                text = "Weather data is not available right now.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Create reminder",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Schedule a local reminder for errands, classes, or study tasks.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Reminder text") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Text(
                        text = selectedTime?.let {
                            "Scheduled for ${dateFormatter.format(Date(it))}"
                        } ?: "No date and time selected yet.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedButton(
                        onClick = {
                            showDateTimePicker(
                                context = context,
                                initialTime = selectedTime,
                                onTimeSelected = { selectedTime = it }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("Choose date and time")
                    }

                    Button(
                        onClick = {
                            val reminderTime = selectedTime ?: return@Button
                            val reminder = Reminder(
                                text = text.trim(),
                                time = reminderTime
                            )
                            reminderVM.addReminder(reminder)
                            AlarmHelper.scheduleReminder(
                                context = context,
                                text = reminder.text,
                                triggerTime = reminder.time
                            )
                            text = ""
                            selectedTime = null
                        },
                        enabled = text.isNotBlank() && selectedTime != null,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("Save reminder")
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Upcoming reminders",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${reminders.size} saved",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (reminders.isEmpty()) {
            item {
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "No reminders yet",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Add one above and it will stay on this device until you remove it.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        items(
            items = reminders,
            key = { "${it.text}-${it.time}" }
        ) { reminder ->
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.padding(end = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = reminder.text,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = dateFormatter.format(Date(reminder.time)),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = {
                            reminderVM.deleteReminder(reminder)
                            AlarmHelper.cancelReminder(
                                context = context,
                                text = reminder.text,
                                triggerTime = reminder.time
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Delete reminder"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherMetric(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    OutlinedCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

private fun showDateTimePicker(
    context: Context,
    initialTime: Long?,
    onTimeSelected: (Long) -> Unit
) {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = initialTime ?: System.currentTimeMillis()
    }

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    onTimeSelected(calendar.timeInMillis)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

private fun weatherLabel(code: Int): String =
    when (code) {
        0 -> "Clear sky"
        1, 2, 3 -> "Partly cloudy"
        45, 48 -> "Foggy"
        51, 53, 55 -> "Light drizzle"
        56, 57 -> "Freezing drizzle"
        61, 63, 65 -> "Rain showers"
        66, 67 -> "Freezing rain"
        71, 73, 75, 77 -> "Snow"
        80, 81, 82 -> "Rain"
        85, 86 -> "Snow showers"
        95, 96, 99 -> "Thunderstorm"
        else -> "Current conditions"
    }
