package com.rahul.utilityapp.data.repository

import android.content.Context
import com.rahul.utilityapp.data.model.Reminder
import com.rahul.utilityapp.utils.ReminderStorage

class ReminderRepository(
    private val context: Context
) {
    private val reminders = ReminderStorage.load(context)

    fun getReminders(): List<Reminder> =
        reminders.sortedBy { it.time }

    fun addReminder(reminder: Reminder) {
        reminders.add(reminder)
        persist()
    }

    fun deleteReminder(reminder: Reminder) {
        reminders.remove(reminder)
        persist()
    }

    private fun persist() {
        ReminderStorage.save(context, reminders)
    }
}
