package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.DigitalTwinData
import com.example.data.model.HabitEntity
import com.example.data.model.HealthRecordEntity
import com.example.data.model.HealthRecordType
import com.example.data.model.HealthSosData
import com.example.data.model.OrganInfo
import com.example.data.model.SosAssessmentResult
import com.example.data.model.UserEntity
import com.example.data.model.VoiceKnowledgeBase
import com.example.data.repository.HealthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

enum class AppScreen {
    WELCOME,
    AUTH,
    DASHBOARD,
    DIGITAL_TWIN,
    HEALTH_SOS,
    HEALTH_VOICE,
    HEALTH_RECORDS,
    HEALTH_EDUCATION,
    PROFILE,
    HABITS_WATER
}

data class VoiceMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class HealthViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    val repository = HealthRepository(db)

    // Navigation
    private val _currentScreen = MutableStateFlow(AppScreen.WELCOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Screen back stack
    private val screenStack = mutableListOf<AppScreen>()

    // Dark/Light theme
    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    // User State
    val currentUser: StateFlow<UserEntity?> = repository.currentUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // Records
    val allRecords: StateFlow<List<HealthRecordEntity>> = repository.allRecords.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Habits
    val todayHabits: StateFlow<List<HabitEntity>> = repository.getTodayHabits().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Water
    private val _waterMl = MutableStateFlow(750)
    val waterMl: StateFlow<Int> = _waterMl.asStateFlow()

    // Digital Twin selected organ
    private val _selectedOrgan = MutableStateFlow<OrganInfo>(DigitalTwinData.organs[1]) // Heart by default
    val selectedOrgan: StateFlow<OrganInfo> = _selectedOrgan.asStateFlow()

    // Health SOS state
    private val _selectedSymptoms = MutableStateFlow<Set<String>>(emptySet())
    val selectedSymptoms: StateFlow<Set<String>> = _selectedSymptoms.asStateFlow()

    private val _sosAssessment = MutableStateFlow<SosAssessmentResult?>(null)
    val sosAssessment: StateFlow<SosAssessmentResult?> = _sosAssessment.asStateFlow()

    private val _durationHours = MutableStateFlow(6)
    val durationHours: StateFlow<Int> = _durationHours.asStateFlow()

    private val _hasSweatingOrVomiting = MutableStateFlow(false)
    val hasSweatingOrVomiting: StateFlow<Boolean> = _hasSweatingOrVomiting.asStateFlow()

    // Health Voice state
    private val _voiceChatHistory = MutableStateFlow<List<VoiceMessage>>(
        listOf(
            VoiceMessage(
                text = "નમસ્તે! હું તમારો ગુજરાતી આરોગ્ય સહાયક છું. તમે સ્વાસ્થ્ય, આહાર, કસરત કે પ્રાથમિક સારવાર અંગે કોઈપણ પ્રશ્ન પૂછી શકો છો.",
                isUser = false
            )
        )
    )
    val voiceChatHistory: StateFlow<List<VoiceMessage>> = _voiceChatHistory.asStateFlow()

    private val _isTtsSpeaking = MutableStateFlow(false)
    val isTtsSpeaking: StateFlow<Boolean> = _isTtsSpeaking.asStateFlow()

    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false

    init {
        viewModelScope.launch {
            repository.ensureDefaultUserAndDemo()
            // Observe water log
            repository.getTodayWater().collect { log ->
                if (log != null) {
                    _waterMl.value = log.currentMl
                }
            }
        }
        initTts(application)
    }

    private fun initTts(context: Context) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isTtsInitialized = true
                // Try Gujarati locale or Hindi fallback
                val guLocale = Locale("gu", "IN")
                val result = tts?.setLanguage(guLocale)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Fallback to Hindi which has excellent phonetic mapping for Indic scripts
                    val hiLocale = Locale("hi", "IN")
                    tts?.setLanguage(hiLocale)
                }
                tts?.setPitch(1.0f)
                tts?.setSpeechRate(0.95f)
            }
        }
    }

    fun speakText(text: String) {
        if (!isTtsInitialized || tts == null) return
        _isTtsSpeaking.value = true
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "AarogyaTts")
    }

    fun stopSpeaking() {
        tts?.stop()
        _isTtsSpeaking.value = false
    }

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun navigateTo(screen: AppScreen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeAt(screenStack.lastIndex)
            return true
        } else if (_currentScreen.value != AppScreen.DASHBOARD && _currentScreen.value != AppScreen.WELCOME) {
            _currentScreen.value = AppScreen.DASHBOARD
            return true
        }
        return false
    }

    fun selectOrgan(organ: OrganInfo) {
        _selectedOrgan.value = organ
    }

    // Health SOS actions
    fun toggleSymptom(symptomId: String) {
        val current = _selectedSymptoms.value.toMutableSet()
        if (current.contains(symptomId)) {
            current.remove(symptomId)
        } else {
            current.add(symptomId)
        }
        _selectedSymptoms.value = current
        calculateSosAssessment()
    }

    fun setDuration(hours: Int) {
        _durationHours.value = hours
        calculateSosAssessment()
    }

    fun setSweatingOrVomiting(flag: Boolean) {
        _hasSweatingOrVomiting.value = flag
        calculateSosAssessment()
    }

    fun clearSymptoms() {
        _selectedSymptoms.value = emptySet()
        _sosAssessment.value = null
    }

    private fun calculateSosAssessment() {
        if (_selectedSymptoms.value.isEmpty()) {
            _sosAssessment.value = null
            return
        }
        _sosAssessment.value = HealthSosData.evaluateAssessment(
            selectedSymptomIds = _selectedSymptoms.value,
            durationHours = _durationHours.value,
            hasVomitingOrSweating = _hasSweatingOrVomiting.value
        )
    }

    fun makePhoneCall(context: Context, number: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$number")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Voice query
    fun askHealthQuestion(query: String) {
        if (query.isBlank()) return
        val userMsg = VoiceMessage(text = query, isUser = true)
        val answer = VoiceKnowledgeBase.findAnswer(query)
        val botMsg = VoiceMessage(text = answer, isUser = false)

        val updated = _voiceChatHistory.value.toMutableList()
        updated.add(userMsg)
        updated.add(botMsg)
        _voiceChatHistory.value = updated

        // Automatically speak answer if requested
        speakText(answer)
    }

    // Habits & Water
    fun toggleHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.toggleHabit(habit)
        }
    }

    fun addWaterGlass() {
        val next = _waterMl.value + 250
        _waterMl.value = next
        viewModelScope.launch {
            repository.setWaterAmount(next)
        }
    }

    fun resetWater() {
        _waterMl.value = 0
        viewModelScope.launch {
            repository.setWaterAmount(0)
        }
    }

    // Records
    fun addHealthRecord(
        type: HealthRecordType,
        val1: Float,
        val2: Float?,
        notes: String
    ) {
        viewModelScope.launch {
            repository.addRecord(type, val1, val2, notes)
        }
    }

    fun deleteRecord(record: HealthRecordEntity) {
        viewModelScope.launch {
            repository.deleteRecord(record)
        }
    }

    fun clearAllRecords() {
        viewModelScope.launch {
            repository.clearAllRecords()
        }
    }

    // Auth actions
    fun login(contact: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            if (contact.isBlank() || pass.isBlank()) {
                onResult(false, "કૃપા કરીને સંપર્ક અને પાસવર્ડ દાખલ કરો.")
                return@launch
            }
            val success = repository.login(contact.trim(), pass)
            if (success) {
                _currentScreen.value = AppScreen.DASHBOARD
                onResult(true, "સફળ પ્રવેશ!")
            } else {
                onResult(false, "ખોટો ઈમેઈલ/મોબાઈલ અથવા પાસવર્ડ.")
            }
        }
    }

    fun loginDemo() {
        viewModelScope.launch {
            repository.loginAsDemo()
            _currentScreen.value = AppScreen.DASHBOARD
        }
    }

    fun register(
        fullName: String,
        contact: String,
        pass: String,
        confirmPass: String,
        ageStr: String,
        emergencyContact: String,
        emergencyName: String,
        bloodGroup: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            if (fullName.isBlank()) {
                onResult(false, "કૃપા કરીને તમારું પૂરું નામ લખો.")
                return@launch
            }
            if (contact.isBlank()) {
                onResult(false, "ઈમેઇલ અથવા મોબાઈલ નંબર દાખલ કરો.")
                return@launch
            }
            if (pass.length < 4) {
                onResult(false, "પાસવર્ડ ઓછામાં ઓછો ૪ અક્ષરોનો હોવો જોઈએ.")
                return@launch
            }
            if (pass != confirmPass) {
                onResult(false, "પાસવર્ડ અને કન્ફર્મ પાસવર્ડ સરખા નથી.")
                return@launch
            }
            val age = ageStr.toIntOrNull() ?: 25
            repository.registerUser(
                fullName = fullName.trim(),
                contact = contact.trim(),
                pass = pass,
                age = age,
                emergencyContact = emergencyContact.trim(),
                emergencyContactName = emergencyName.trim(),
                bloodGroup = bloodGroup
            )
            _currentScreen.value = AppScreen.DASHBOARD
            onResult(true, "ખાતું સફળતાપૂર્વક બની ગયું!")
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _currentScreen.value = AppScreen.AUTH
        }
    }

    fun updateUserProfile(user: UserEntity) {
        viewModelScope.launch {
            repository.updateUserProfile(user)
        }
    }

    override fun onCleared() {
        super.onCleared()
        tts?.stop()
        tts?.shutdown()
    }
}
