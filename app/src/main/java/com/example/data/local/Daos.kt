package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.HabitEntity
import com.example.data.model.HealthRecordEntity
import com.example.data.model.UserEntity
import com.example.data.model.WaterLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    fun getLoggedInUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE contact = :contact LIMIT 1")
    suspend fun getUserByContact(contact: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Long): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET isLoggedIn = 0")
    suspend fun logoutAll()

    @Query("UPDATE users SET isLoggedIn = 1 WHERE id = :id")
    suspend fun setLoggedIn(id: Long)
}

@Dao
interface HealthRecordDao {
    @Query("SELECT * FROM health_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<HealthRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: HealthRecordEntity): Long

    @Update
    suspend fun updateRecord(record: HealthRecordEntity)

    @Delete
    suspend fun deleteRecord(record: HealthRecordEntity)

    @Query("DELETE FROM health_records")
    suspend fun deleteAllRecords()
}

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits WHERE dateString = :date ORDER BY id ASC")
    fun getHabitsForDate(date: String): Flow<List<HabitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabits(habits: List<HabitEntity>)

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Query("SELECT COUNT(*) FROM habits WHERE dateString = :date")
    suspend fun getHabitCountForDate(date: String): Int
}

@Dao
interface WaterLogDao {
    @Query("SELECT * FROM water_logs WHERE dateString = :date LIMIT 1")
    fun getWaterLog(date: String): Flow<WaterLogEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(waterLog: WaterLogEntity)
}
