package com.camgist.snoozeloo.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.vector.DefaultTrimPathStart
import com.camgist.snoozeloo.MainActivity
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.Navigator


class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (context == null || intent == null) return
        val alarmTitle = intent.getStringExtra("EXTRA_TITLE") ?: return

        val navigationIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("EXTRA_NAVIGATION_TARGET", "TriggerScreen")
            putExtra("EXTRA_ALARM_TITLE", alarmTitle)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(navigationIntent)
    }
}