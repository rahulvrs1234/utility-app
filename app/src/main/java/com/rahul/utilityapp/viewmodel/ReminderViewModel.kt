package com.rahul.utilityapp.viewmodel

import androidx.lifecycle.ViewModel
import com.rahul.utilityapp.data.model.Reminder
import com.rahul.utilityapp.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReminderViewModel : ViewModel() {

    private val repository = ReminderRepository()

    private val _reminders =
        MutableStateFlow(repository.getReminders())

    val reminders: StateFlow<List<Reminder>> = _reminders

    fun addReminder(reminder: Reminder) {
        repository.addReminder(reminder)
        _reminders.value = repository.getReminders()
    }
}