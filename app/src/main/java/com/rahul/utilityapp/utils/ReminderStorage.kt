package com.rahul.utilityapp.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rahul.utilityapp.data.model.Reminder

object ReminderStorage {
    fun save(
        context: Context,
        list: List<Reminder>
    ) {
        val prefs = context.getSharedPreferences(
            "reminders",
            Context.MODE_PRIVATE
        )

        prefs.edit()
            .putString("data", Gson().toJson(list))
            .apply()
    }

    fun load(context: Context): MutableList<Reminder> {
        val prefs = context.getSharedPreferences(
            "reminders",
            Context.MODE_PRIVATE
        )

        val json = prefs.getString("data", null)
            ?: return mutableListOf()

        val type = object : TypeToken<MutableList<Reminder>>() {}.type

        return Gson().fromJson(json, type)
    }
}
