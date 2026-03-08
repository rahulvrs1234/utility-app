package com.rahul.utilityapp.utils

import android.app.*
import android.content.Context
import android.content.Intent
import com.rahul.utilityapp.ReminderReceiver

object AlarmHelper {

    fun scheduleReminder(
        context: Context,
        text: String,
        triggerTime: Long
    ) {

        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra("REMINDER_TEXT", text)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE)
                    as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }
}