package com.camgist.snoozeloo.alarmManager

// AlarmReceiver.kt
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import android.app.NotificationManager
import android.app.NotificationChannel
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import com.camgist.snoozeloo.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "onReceive")

        if (context == null || intent == null) return
        val alarmExtra = intent.getStringExtra("EXTRA_ALARM") ?: return

        // Acquire wake lock
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "Snoozeloo::AlarmWakeLock"
        )
        wakeLock.acquire(10*60*1000L /*10 minutes*/)

        // Create notification channel
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            "alarm_channel",
            "Alarm Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableVibration(true)
            setBypassDnd(true)
        }
        notificationManager.createNotificationChannel(channel)

        // Create intent for opening the app
        val navigationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("EXTRA_NAVIGATION_TARGET", "TriggerScreen")
            putExtra("EXTRA_ALARM", alarmExtra)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            navigationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Show notification
        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Alarm")
            .setContentText("Time to wake up!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(pendingIntent, true)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)

        // Start activity
        context.startActivity(navigationIntent)

        // Release wake lock
        wakeLock.release()
    }
}