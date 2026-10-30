package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.data.model.HabitEntity
import com.example.data.model.HealthRecordEntity
import com.example.data.model.HealthRecordType
import com.example.data.model.UserEntity
import com.example.data.model.WaterLogEntity

class Converters {
    @TypeConverter
    fun fromHealthRecordType(value: HealthRecordType): String {
        return value.name
    }

    @TypeConverter
    fun toHealthRecordType(value: String): HealthRecordType {
        return try {
            HealthRecordType.valueOf(value)
        } catch (e: Exception) {
            HealthRecordType.HEART_RATE
        }
    }
}

@Database(
    entities = [
        UserEntity::class,
        HealthRecordEntity::class,
        HabitEntity::class,
        WaterLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun healthRecordDao(): HealthRecordDao
    abstract fun habitDao(): HabitDao
    abstract fun waterLogDao(): WaterLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gujarat_aarogya_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
