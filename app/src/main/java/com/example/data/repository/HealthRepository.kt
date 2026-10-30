package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.model.HabitEntity
import com.example.data.model.HealthRecordEntity
import com.example.data.model.HealthRecordType
import com.example.data.model.UserEntity
import com.example.data.model.WaterLogEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HealthRepository(private val db: AppDatabase) {
    private val userDao = db.userDao()
    private val recordDao = db.healthRecordDao()
    private val habitDao = db.habitDao()
    private val waterDao = db.waterLogDao()

    val currentUser: Flow<UserEntity?> = userDao.getLoggedInUser()
    val allRecords: Flow<List<HealthRecordEntity>> = recordDao.getAllRecords()

    fun getTodayString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getTodayHabits(): Flow<List<HabitEntity>> {
        return habitDao.getHabitsForDate(getTodayString())
    }

    fun getTodayWater(): Flow<WaterLogEntity?> {
        return waterDao.getWaterLog(getTodayString())
    }

    suspend fun ensureDefaultUserAndDemo() {
        val existingDemo = userDao.getUserByContact("demo@aarogya.gujarat")
        if (existingDemo == null) {
            val demoUser = UserEntity(
                fullName = "સંકેત પટેલ",
                contact = "demo@aarogya.gujarat",
                passwordHash = "demo123",
                age = 28,
                emergencyContact = "9876543210",
                emergencyContactName = "પરિવાર (પિતાશ્રી)",
                bloodGroup = "O+",
                isDemoUser = true,
                isLoggedIn = false
            )
            userDao.insertUser(demoUser)
        }
    }

    suspend fun login(contact: String, pass: String): Boolean {
        val user = userDao.getUserByContact(contact) ?: return false
        if (user.passwordHash == pass) {
            userDao.logoutAll()
            userDao.setLoggedIn(user.id)
            return true
        }
        return false
    }

    suspend fun loginAsDemo(): Boolean {
        ensureDefaultUserAndDemo()
        val demo = userDao.getUserByContact("demo@aarogya.gujarat")
        if (demo != null) {
            userDao.logoutAll()
            userDao.setLoggedIn(demo.id)
            seedSampleRecordsIfEmpty()
            seedHabitsIfEmpty()
            return true
        }
        return false
    }

    suspend fun registerUser(
        fullName: String,
        contact: String,
        pass: String,
        age: Int,
        emergencyContact: String,
        emergencyContactName: String,
        bloodGroup: String
    ): Long {
        userDao.logoutAll()
        val newUser = UserEntity(
            fullName = fullName,
            contact = contact,
            passwordHash = pass,
            age = age,
            emergencyContact = emergencyContact,
            emergencyContactName = emergencyContactName,
            bloodGroup = bloodGroup,
            isDemoUser = false,
            isLoggedIn = true
        )
        val id = userDao.insertUser(newUser)
        seedSampleRecordsIfEmpty()
        seedHabitsIfEmpty()
        return id
    }

    suspend fun updateUserProfile(user: UserEntity) {
        userDao.updateUser(user)
    }

    suspend fun logout() {
        userDao.logoutAll()
    }

    suspend fun addRecord(
        type: HealthRecordType,
        primaryValue: Float,
        secondaryValue: Float?,
        notes: String
    ) {
        val statusTag = when (type) {
            HealthRecordType.BLOOD_PRESSURE -> {
                val sys = primaryValue
                val dia = secondaryValue ?: 80f
                when {
                    sys > 140 || dia > 90 -> "હાઈ પ્રેશર"
                    sys < 90 || dia < 60 -> "લો પ્રેશર"
                    else -> "સામાન્ય (આદર્શ)"
                }
            }
            HealthRecordType.BLOOD_SUGAR -> {
                when {
                    primaryValue < 70 -> "ઓછી સુગર (હાયપો)"
                    primaryValue > 140 -> "વધુ સુગર"
                    else -> "સામાન્ય"
                }
            }
            HealthRecordType.HEART_RATE -> {
                when {
                    primaryValue < 60 -> "ધીમા ધબકારા"
                    primaryValue > 100 -> "ઝડપી ધબકારા"
                    else -> "સામાન્ય (૭૨ આદર્શ)"
                }
            }
            HealthRecordType.WEIGHT_BMI -> {
                val weight = primaryValue
                val heightCm = secondaryValue ?: 170f
                val heightM = heightCm / 100f
                val bmi = if (heightM > 0) weight / (heightM * heightM) else 22f
                when {
                    bmi < 18.5 -> "ઓછું વજન (BMI: ${"%.1f".format(bmi)})"
                    bmi in 18.5..24.9 -> "આદર્શ વજન (BMI: ${"%.1f".format(bmi)})"
                    bmi in 25.0..29.9 -> "વધુ વજન (BMI: ${"%.1f".format(bmi)})"
                    else -> "મેદસ્વીતા (BMI: ${"%.1f".format(bmi)})"
                }
            }
            HealthRecordType.BODY_TEMP -> {
                when {
                    primaryValue > 99.5f -> "તાવ (Fever)"
                    primaryValue < 96.0f -> "ઓછું તાપમાન"
                    else -> "સામાન્ય (98.6°F)"
                }
            }
        }

        val record = HealthRecordEntity(
            type = type,
            primaryValue = primaryValue,
            secondaryValue = secondaryValue,
            statusTagGu = statusTag,
            notes = notes,
            timestamp = System.currentTimeMillis()
        )
        recordDao.insertRecord(record)
    }

    suspend fun deleteRecord(record: HealthRecordEntity) {
        recordDao.deleteRecord(record)
    }

    suspend fun clearAllRecords() {
        recordDao.deleteAllRecords()
    }

    suspend fun seedSampleRecordsIfEmpty() {
        // Provide rich sample historical records so charts look stunning on first launch
        val now = System.currentTimeMillis()
        val oneDay = 86400000L
        val sampleRecords = listOf(
            HealthRecordEntity(
                type = HealthRecordType.BLOOD_PRESSURE,
                primaryValue = 120f,
                secondaryValue = 80f,
                statusTagGu = "સામાન્ય (આદર્શ)",
                notes = "સવારે નયણે કોઠે માપેલ",
                timestamp = now - (oneDay * 3)
            ),
            HealthRecordEntity(
                type = HealthRecordType.BLOOD_PRESSURE,
                primaryValue = 124f,
                secondaryValue = 82f,
                statusTagGu = "સામાન્ય",
                notes = "સાંજે વોકિંગ બાદ",
                timestamp = now - (oneDay * 2)
            ),
            HealthRecordEntity(
                type = HealthRecordType.BLOOD_PRESSURE,
                primaryValue = 118f,
                secondaryValue = 78f,
                statusTagGu = "સામાન્ય (આદર્શ)",
                notes = "આરામદાયક સ્થિતિમાં",
                timestamp = now - oneDay
            ),
            HealthRecordEntity(
                type = HealthRecordType.HEART_RATE,
                primaryValue = 72f,
                statusTagGu = "સામાન્ય (૭૨ આદર્શ)",
                notes = "નિયમિત પલ્સ",
                timestamp = now - (oneDay * 2)
            ),
            HealthRecordEntity(
                type = HealthRecordType.BLOOD_SUGAR,
                primaryValue = 98f,
                statusTagGu = "સામાન્ય",
                notes = "ફાસ્ટિંગ શુગર",
                timestamp = now - oneDay
            ),
            HealthRecordEntity(
                type = HealthRecordType.WEIGHT_BMI,
                primaryValue = 68.5f,
                secondaryValue = 172f,
                statusTagGu = "આદર્શ વજન (BMI: 23.2)",
                notes = "સવારનું વજન",
                timestamp = now - (oneDay * 4)
            )
        )
        sampleRecords.forEach { recordDao.insertRecord(it) }
    }

    suspend fun seedHabitsIfEmpty() {
        val today = getTodayString()
        val count = habitDao.getHabitCountForDate(today)
        if (count == 0) {
            val defaultHabits = listOf(
                HabitEntity(
                    titleGu = "સૂર્યનમસ્કાર / ૧૫ મિનિટ કસરત",
                    subtitleGu = "શરીરમાં તાજગી અને સ્ફૂર્તિ માટે",
                    category = "વ્યાયામ",
                    isCompleted = true,
                    dateString = today
                ),
                HabitEntity(
                    titleGu = "૩ લિટર શુદ્ધ પાણી પીવું",
                    subtitleGu = "હાઇડ્રેશન અને ઝેરી તત્વો દૂર કરવા",
                    category = "પાણી",
                    isCompleted = false,
                    dateString = today
                ),
                HabitEntity(
                    titleGu = "લીલા શાકભાજી અને ફળોનો આહાર",
                    subtitleGu = "વિટામિન્સ અને ફાઇબર માટે",
                    category = "આહાર",
                    isCompleted = false,
                    dateString = today
                ),
                HabitEntity(
                    titleGu = "૧૦ મિનિટ પ્રાણાયામ / ધ્યાન",
                    subtitleGu = "માનસિક શાંતિ અને ફેફસાંના સ્વાસ્થ્ય માટે",
                    category = "ધ્યાન",
                    isCompleted = true,
                    dateString = today
                ),
                HabitEntity(
                    titleGu = "રાત્રે ૮ કલાકની ગાઢ ઊંઘ",
                    subtitleGu = "કોષોના પુનર્નિર્માણ અને ઊર્જા માટે",
                    category = "ઊંઘ",
                    isCompleted = false,
                    dateString = today
                )
            )
            habitDao.insertHabits(defaultHabits)
        }
    }

    suspend fun toggleHabit(habit: HabitEntity) {
        habitDao.updateHabit(habit.copy(isCompleted = !habit.isCompleted))
    }

    suspend fun addWater(amountMl: Int = 250) {
        val today = getTodayString()
        val current = waterDao.getWaterLog(today)
        // We will insert or update
        val updated = WaterLogEntity(
            dateString = today,
            currentMl = 250, // default if none
            targetMl = 3000
        )
        // In ViewModel we will fetch and update
    }

    suspend fun setWaterAmount(newAmount: Int) {
        val today = getTodayString()
        waterDao.insertOrUpdate(
            WaterLogEntity(
                dateString = today,
                currentMl = newAmount.coerceIn(0, 5000),
                targetMl = 3000
            )
        )
    }
}
