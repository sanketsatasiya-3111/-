package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DigitalTwinScreen
import com.example.ui.screens.HabitsWaterScreen
import com.example.ui.screens.HealthEducationScreen
import com.example.ui.screens.HealthRecordsScreen
import com.example.ui.screens.HealthSosScreen
import com.example.ui.screens.HealthVoiceScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.GujaratAarogyaTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.HealthViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: HealthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            val currentScreen by viewModel.currentScreen.collectAsState()
            val currentUser by viewModel.currentUser.collectAsState()
            val allRecords by viewModel.allRecords.collectAsState()
            val todayHabits by viewModel.todayHabits.collectAsState()
            val waterMl by viewModel.waterMl.collectAsState()
            val selectedOrgan by viewModel.selectedOrgan.collectAsState()
            val selectedSymptoms by viewModel.selectedSymptoms.collectAsState()
            val sosAssessment by viewModel.sosAssessment.collectAsState()
            val durationHours by viewModel.durationHours.collectAsState()
            val hasSweatingOrVomiting by viewModel.hasSweatingOrVomiting.collectAsState()
            val voiceChatHistory by viewModel.voiceChatHistory.collectAsState()
            val isTtsSpeaking by viewModel.isTtsSpeaking.collectAsState()

            GujaratAarogyaTheme(darkTheme = isDarkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // System Back Handler for sub-screens
                    if (currentScreen != AppScreen.WELCOME && currentScreen != AppScreen.DASHBOARD) {
                        BackHandler {
                            viewModel.navigateBack()
                        }
                    }

                    when (currentScreen) {
                        AppScreen.WELCOME -> {
                            WelcomeScreen(
                                onStartClick = { viewModel.navigateTo(AppScreen.AUTH) },
                                isDarkTheme = isDarkTheme
                            )
                        }

                        AppScreen.AUTH -> {
                            AuthScreen(
                                onLoginSuccess = { viewModel.navigateTo(AppScreen.DASHBOARD) },
                                onLoginClick = { contact, pass, callback ->
                                    viewModel.login(contact, pass, callback)
                                },
                                onRegisterClick = { name, contact, pass, confirm, age, emContact, emName, bg, callback ->
                                    viewModel.register(name, contact, pass, confirm, age, emContact, emName, bg, callback)
                                },
                                onDemoLoginClick = { viewModel.loginDemo() },
                                isDarkTheme = isDarkTheme
                            )
                        }

                        AppScreen.DASHBOARD -> {
                            DashboardScreen(
                                user = currentUser,
                                records = allRecords,
                                habits = todayHabits,
                                waterMl = waterMl,
                                onNavigateTo = { screen -> viewModel.navigateTo(screen) },
                                onSosClick = { viewModel.navigateTo(AppScreen.HEALTH_SOS) },
                                isDarkTheme = isDarkTheme,
                                onToggleTheme = { viewModel.toggleTheme() }
                            )
                        }

                        AppScreen.DIGITAL_TWIN -> {
                            DigitalTwinScreen(
                                selectedOrgan = selectedOrgan,
                                onSelectOrgan = { organ -> viewModel.selectOrgan(organ) },
                                onBackClick = { viewModel.navigateBack() },
                                onSosClick = { viewModel.navigateTo(AppScreen.HEALTH_SOS) },
                                isDarkTheme = isDarkTheme,
                                onToggleTheme = { viewModel.toggleTheme() }
                            )
                        }

                        AppScreen.HEALTH_SOS -> {
                            HealthSosScreen(
                                selectedSymptoms = selectedSymptoms,
                                onToggleSymptom = { id -> viewModel.toggleSymptom(id) },
                                durationHours = durationHours,
                                onDurationChange = { hrs -> viewModel.setDuration(hrs) },
                                hasSweatingOrVomiting = hasSweatingOrVomiting,
                                onSweatingChange = { flag -> viewModel.setSweatingOrVomiting(flag) },
                                assessmentResult = sosAssessment,
                                onClearSymptoms = { viewModel.clearSymptoms() },
                                currentUser = currentUser,
                                onCallNumber = { ctx, num -> viewModel.makePhoneCall(ctx, num) },
                                onBackClick = { viewModel.navigateBack() },
                                isDarkTheme = isDarkTheme,
                                onToggleTheme = { viewModel.toggleTheme() }
                            )
                        }

                        AppScreen.HEALTH_VOICE -> {
                            HealthVoiceScreen(
                                chatHistory = voiceChatHistory,
                                onAskQuestion = { q -> viewModel.askHealthQuestion(q) },
                                onSpeakAnswer = { text -> viewModel.speakText(text) },
                                onStopSpeaking = { viewModel.stopSpeaking() },
                                isSpeaking = isTtsSpeaking,
                                onBackClick = { viewModel.navigateBack() },
                                onSosClick = { viewModel.navigateTo(AppScreen.HEALTH_SOS) },
                                isDarkTheme = isDarkTheme,
                                onToggleTheme = { viewModel.toggleTheme() }
                            )
                        }

                        AppScreen.HEALTH_RECORDS -> {
                            HealthRecordsScreen(
                                records = allRecords,
                                onAddRecord = { type, v1, v2, notes ->
                                    viewModel.addHealthRecord(type, v1, v2, notes)
                                },
                                onDeleteRecord = { record -> viewModel.deleteRecord(record) },
                                onClearAllRecords = { viewModel.clearAllRecords() },
                                onBackClick = { viewModel.navigateBack() },
                                onSosClick = { viewModel.navigateTo(AppScreen.HEALTH_SOS) },
                                isDarkTheme = isDarkTheme,
                                onToggleTheme = { viewModel.toggleTheme() }
                            )
                        }

                        AppScreen.HABITS_WATER -> {
                            HabitsWaterScreen(
                                habits = todayHabits,
                                onToggleHabit = { habit -> viewModel.toggleHabit(habit) },
                                waterMl = waterMl,
                                onAddWaterGlass = { viewModel.addWaterGlass() },
                                onResetWater = { viewModel.resetWater() },
                                onBackClick = { viewModel.navigateBack() },
                                onSosClick = { viewModel.navigateTo(AppScreen.HEALTH_SOS) },
                                isDarkTheme = isDarkTheme,
                                onToggleTheme = { viewModel.toggleTheme() }
                            )
                        }

                        AppScreen.HEALTH_EDUCATION -> {
                            HealthEducationScreen(
                                onBackClick = { viewModel.navigateBack() },
                                onSosClick = { viewModel.navigateTo(AppScreen.HEALTH_SOS) },
                                isDarkTheme = isDarkTheme,
                                onToggleTheme = { viewModel.toggleTheme() }
                            )
                        }

                        AppScreen.PROFILE -> {
                            ProfileScreen(
                                user = currentUser,
                                onUpdateProfile = { updated -> viewModel.updateUserProfile(updated) },
                                onLogout = { viewModel.logout() },
                                onBackClick = { viewModel.navigateBack() },
                                onSosClick = { viewModel.navigateTo(AppScreen.HEALTH_SOS) },
                                isDarkTheme = isDarkTheme,
                                onToggleTheme = { viewModel.toggleTheme() }
                            )
                        }
                    }
                }
            }
        }
    }
}
