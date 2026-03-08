package com.rahul.utilityapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val text = intent?.getStringExtra("REMINDER_TEXT") ?: "Reminder"

        val channelId = "reminder_channel"

        val channel = NotificationChannel(
            channelId,
            "Reminder Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Reminder")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}