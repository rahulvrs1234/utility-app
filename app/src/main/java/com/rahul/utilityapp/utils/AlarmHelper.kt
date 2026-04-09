package com.rahul.utilityapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.rahul.utilityapp.ReminderReceiver

object AlarmHelper {
    fun scheduleReminder(
        context: Context,
        text: String,
        triggerTime: Long
    ) {
        val pendingIntent = reminderPendingIntent(
            context = context,
            text = text,
            triggerTime = triggerTime,
            flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }

    fun cancelReminder(
        context: Context,
        text: String,
        triggerTime: Long
    ) {
        val pendingIntent = reminderPendingIntent(
            context = context,
            text = text,
            triggerTime = triggerTime,
            flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun reminderPendingIntent(
        context: Context,
        text: String,
        triggerTime: Long,
        flags: Int
    ): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("REMINDER_TEXT", text)
        }

        return PendingIntent.getBroadcast(
            context,
            reminderRequestCode(text, triggerTime),
            intent,
            flags
        )
    }

    private fun reminderRequestCode(
        text: String,
        triggerTime: Long
    ): Int = 31 * text.hashCode() + triggerTime.hashCode()
}
