package com.rahul.utilityapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rahul.utilityapp.data.model.Reminder
import com.rahul.utilityapp.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReminderViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository =
        ReminderRepository(application.applicationContext)

    private val _reminders =
        MutableStateFlow(repository.getReminders())

    val reminders: StateFlow<List<Reminder>> = _reminders.asStateFlow()

    fun addReminder(reminder: Reminder) {
        repository.addReminder(reminder)
        refresh()
    }

    fun deleteReminder(reminder: Reminder) {
        repository.deleteReminder(reminder)
        refresh()
    }

    private fun refresh() {
        _reminders.value = repository.getReminders()
    }
}
