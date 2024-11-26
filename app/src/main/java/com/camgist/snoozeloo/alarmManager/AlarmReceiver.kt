package com.camgist.snoozeloo.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.graphics.vector.DefaultTrimPathStart
import com.camgist.snoozeloo.MainActivity
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.Navigator


class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("AlarmReceiver", "onReceive")

        if (context == null || intent == null) return
        val alarmExtra = intent.getStringExtra("EXTRA_ALARM") ?: return

        val navigationIntent = Intent(context, MainActivity::class.java).apply {
            // Add these flags to ensure the activity is brought to front
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP

            // Add your extras
            putExtra("EXTRA_NAVIGATION_TARGET", "TriggerScreen")
            putExtra("EXTRA_ALARM", alarmExtra)
        }


        context.startActivity(navigationIntent)
    }
}