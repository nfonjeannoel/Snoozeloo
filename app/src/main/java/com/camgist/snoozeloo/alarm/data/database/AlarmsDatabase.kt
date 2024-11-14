package com.camgist.snoozeloo.alarm.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AlarmItemEntity::class], version = 1, exportSchema = false)
abstract class AlarmsDatabase : RoomDatabase() {

    abstract fun alarmsDao(): AlarmsDao

    companion object {
        @Volatile
        private var Instance: AlarmsDatabase? = null

        fun getAlarmsDatabase(context: Context): AlarmsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AlarmsDatabase::class.java, "alarms_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}