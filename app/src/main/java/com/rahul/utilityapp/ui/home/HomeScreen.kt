package com.rahul.utilityapp.ui.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rahul.utilityapp.data.model.Reminder
import com.rahul.utilityapp.utils.AlarmHelper
import com.rahul.utilityapp.viewmodel.ReminderViewModel
import com.rahul.utilityapp.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val weatherVM: WeatherViewModel = viewModel()
    val reminderVM: ReminderViewModel = viewModel()

    val weather = weatherVM.weather.value
    val reminders by reminderVM.reminders.collectAsState()

    var text by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf(System.currentTimeMillis()) }

    val calendar = Calendar.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Daily Utility Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth()) {

            Column(modifier = Modifier.padding(16.dp)) {

                Text("Current Weather")

                Spacer(modifier = Modifier.height(8.dp))

                weather?.let {

                    Text("Temperature: ${it.current_weather.temperature} °C")
                    Text("Wind Speed: ${it.current_weather.windspeed} km/h")

                } ?: Text("Loading weather...")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Reminders",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Reminder text") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row {

            Button(onClick = {

                DatePickerDialog(
                    context,
                    { _, year, month, day ->

                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, day)

                        TimePickerDialog(
                            context,
                            { _, hour, minute ->

                                calendar.set(Calendar.HOUR_OF_DAY, hour)
                                calendar.set(Calendar.MINUTE, minute)

                                selectedTime = calendar.timeInMillis

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

            }) {

                Text("Pick Date & Time")
            }

            Spacer(modifier = Modifier.width(10.dp))

            FloatingActionButton(
                onClick = {

                    if (text.isNotBlank()) {

                        reminderVM.addReminder(
                            Reminder(
                                text = text,
                                time = selectedTime
                            )
                        )

                        AlarmHelper.scheduleReminder(
                            context,
                            text,
                            selectedTime
                        )

                        text = ""
                    }
                }
            ) {

                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(reminders) { reminder ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = reminder.text,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = SimpleDateFormat(
                                "dd MMM yyyy • HH:mm",
                                Locale.getDefault()
                            ).format(Date(reminder.time)),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}