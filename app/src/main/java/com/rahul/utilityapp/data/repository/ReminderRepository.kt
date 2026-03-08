package com.rahul.utilityapp.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.rahul.utilityapp.data.model.Reminder

class ReminderRepository {

    private val reminders = mutableStateListOf<Reminder>()

    fun getReminders() = reminders

    fun addReminder(reminder: Reminder) {
        reminders.add(reminder)
    }

    fun deleteReminder(reminder: Reminder) {
        reminders.remove(reminder)
    }
}