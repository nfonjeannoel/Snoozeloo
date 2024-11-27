package com.camgist.snoozeloo.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.camgist.snoozeloo.alarm.domain.AlarmItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.ZoneId
import android.content.BroadcastReceiver
import android.os.PowerManager
import android.util.Log
import android.app.NotificationManager
import android.app.NotificationChannel
import android.provider.Settings
import androidx.core.app.NotificationCompat

class AndroidAlarmScheduler(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            val alarmString = Json.encodeToString(item)
            putExtra("EXTRA_ALARM", alarmString)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Request alarm permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (alarmManager.canScheduleExactAlarms()) {
                scheduleAlarm(item, pendingIntent)
            } else {
                // Request permission
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        } else {
            scheduleAlarm(item, pendingIntent)
        }
    }

    private fun scheduleAlarm(item: AlarmItem, pendingIntent: PendingIntent) {
        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                pendingIntent
            ),
            pendingIntent
        )
    }

    override fun cancel(item: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.id,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}