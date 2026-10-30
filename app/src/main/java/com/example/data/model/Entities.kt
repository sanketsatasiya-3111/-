package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val contact: String, // email or mobile
    val passwordHash: String,
    val age: Int,
    val emergencyContact: String = "",
    val emergencyContactName: String = "",
    val bloodGroup: String = "B+",
    val isDemoUser: Boolean = false,
    val isLoggedIn: Boolean = false
)

enum class HealthRecordType(val titleGu: String, val unitGu: String) {
    BLOOD_PRESSURE("બ્લડ પ્રેશર (BP)", "mmHg"),
    BLOOD_SUGAR("બ્લડ સુગર (ગ્લુકોઝ)", "mg/dL"),
    HEART_RATE("હૃદયના ધબકારા (Pulse)", "BPM"),
    WEIGHT_BMI("શરીરનું વજન અને BMI", "kg"),
    BODY_TEMP("શરીરનું તાપમાન", "°F")
}

@Entity(tableName = "health_records")
data class HealthRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: HealthRecordType,
    val primaryValue: Float, // e.g., Systolic, Glucose, BPM, Weight, Temp
    val secondaryValue: Float? = null, // e.g., Diastolic, Height (cm)
    val statusTagGu: String = "સામાન્ય",
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titleGu: String,
    val subtitleGu: String,
    val category: String,
    val isCompleted: Boolean = false,
    val dateString: String // YYYY-MM-DD
)

@Entity(tableName = "water_logs")
data class WaterLogEntity(
    @PrimaryKey
    val dateString: String, // YYYY-MM-DD
    val currentMl: Int = 0,
    val targetMl: Int = 3000
)
