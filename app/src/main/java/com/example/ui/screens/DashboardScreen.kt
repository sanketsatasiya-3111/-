package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HabitEntity
import com.example.data.model.HealthRecordEntity
import com.example.data.model.UserEntity
import com.example.ui.components.CyberTopBar
import com.example.ui.components.GlassCard
import com.example.ui.components.SchoolCreditFooter
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalTeal
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WarningAmber
import com.example.ui.viewmodel.AppScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    user: UserEntity?,
    records: List<HealthRecordEntity>,
    habits: List<HabitEntity>,
    waterMl: Int,
    onNavigateTo: (AppScreen) -> Unit,
    onSosClick: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val userName = user?.fullName ?: "સંકેત પટેલ"

    // Formatted current date in Gujarati
    val sdfDate = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("gu", "IN"))
    val todayGujarati = try {
        sdfDate.format(Date())
    } catch (e: Exception) {
        "૨૬ ઓક્ટોબર ૨૦૨૬"
    }

    val infiniteTransition = rememberInfiniteTransition(label = "dashPulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Scaffold(
        topBar = {
            CyberTopBar(
                title = "ગુજરાત આરોગ્ય કેન્દ્ર",
                onSosClick = onSosClick,
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
            )
        },
        bottomBar = {
            // Responsive M3 Bottom Navigation for Mobile
            NavigationBar(
                containerColor = Color(0xFF071228),
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        androidx.compose.foundation.BorderStroke(0.8.dp, DarkNavyBorder)
                    )
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Already on dashboard */ },
                    icon = { Icon(Icons.Default.MonitorHeart, contentDescription = "ડેશબોર્ડ") },
                    label = { Text("ડેશબોર્ડ", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF031626),
                        indicatorColor = NeonCyan,
                        selectedTextColor = NeonCyan,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateTo(AppScreen.DIGITAL_TWIN) },
                    icon = { Icon(Icons.Default.AccessibilityNew, contentDescription = "ડિજિટલ ટ્વીન") },
                    label = { Text("શરીર ટ્વીન", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateTo(AppScreen.HEALTH_SOS) },
                    icon = { Icon(Icons.Default.Security, contentDescription = "SOS", tint = EmergencyRed) },
                    label = { Text("હેલ્થ SOS", fontSize = 10.sp, color = EmergencyRed) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = EmergencyRed,
                        unselectedTextColor = EmergencyRed
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateTo(AppScreen.HEALTH_VOICE) },
                    icon = { Icon(Icons.Default.RecordVoiceOver, contentDescription = "વાણી") },
                    label = { Text("આરોગ્ય વાણી", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateTo(AppScreen.PROFILE) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "પ્રોફાઇલ") },
                    label = { Text("પ્રોફાઇલ", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    )
                )
            }
        },
        containerColor = DarkNavyBg
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Welcome Header Card
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "નમસ્તે, $userName 👋",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextWhite
                            )
                            Text(
                                text = todayGujarati,
                                fontSize = 12.sp,
                                color = NeonCyan
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(NeonCyan, ElectricBlue)))
                                .clickable { onNavigateTo(AppScreen.PROFILE) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userName.take(1),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF031626)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Status Pill
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(MedicalTeal)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "તમારું સ્વાસ્થ્ય પ્રોફાઇલ સક્રિય અને સામાન્ય છે",
                            fontSize = 11.5.sp,
                            color = MedicalTeal,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Quick Vitals Strip (Latest Record summary)
            val latestBp = records.firstOrNull { it.type == com.example.data.model.HealthRecordType.BLOOD_PRESSURE }
            val latestHeart = records.firstOrNull { it.type == com.example.data.model.HealthRecordType.HEART_RATE }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickVitalCard(
                    title = "હૃદયના ધબકારા",
                    value = if (latestHeart != null) "${latestHeart.primaryValue.toInt()} BPM" else "૭૨ BPM",
                    status = "સામાન્ય લય",
                    icon = Icons.Default.Favorite,
                    iconColor = EmergencyRed,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTo(AppScreen.HEALTH_RECORDS) }
                )

                QuickVitalCard(
                    title = "બ્લડ પ્રેશર (BP)",
                    value = if (latestBp != null) "${latestBp.primaryValue.toInt()}/${latestBp.secondaryValue?.toInt() ?: 80}" else "૧૨૦/૮૦",
                    status = "આદર્શ માપ",
                    icon = Icons.Default.MonitorHeart,
                    iconColor = NeonCyan,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTo(AppScreen.HEALTH_RECORDS) }
                )
            }

            // Emergency Banner Shortcut
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF330C19),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, EmergencyRed.copy(alpha = pulseAlpha)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onSosClick)
                    .testTag("dashboard_emergency_banner")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(EmergencyRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Call, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "હેલ્થ SOS ૧૧૨ / ૧૦૮",
                                fontSize = 14.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "કટોકટી માર્ગદર્શિકા & ત્વરિત કૉલ",
                                fontSize = 11.5.sp,
                                color = Color(0xFFFFB3BA)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(EmergencyRed)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "ખોલો →",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // 6 Core Feature Grid Cards
            Text(
                text = "આરોગ્ય મોડ્યુલ્સ:",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan
            )

            // Grid Row 1: Digital Twin & Health Voice
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FeatureTile(
                    title = "My Body Digital Twin",
                    titleGu = "માનવ શરીર રચના",
                    subtitleGu = "ઇન્ટરેક્ટિવ અવલોકન",
                    icon = Icons.Default.AccessibilityNew,
                    iconColor = NeonCyan,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTo(AppScreen.DIGITAL_TWIN) }
                )

                FeatureTile(
                    title = "Gujarati Health Voice",
                    titleGu = "આરોગ્ય વાણી",
                    subtitleGu = "વોઇસ આસિસ્ટન્ટ",
                    icon = Icons.Default.RecordVoiceOver,
                    iconColor = MedicalTeal,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTo(AppScreen.HEALTH_VOICE) }
                )
            }

            // Grid Row 2: Records & Habits/Water
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FeatureTile(
                    title = "Health Records",
                    titleGu = "સ્વાસ્થ્ય નોંધણી",
                    subtitleGu = "માપન & આલેખ",
                    icon = Icons.Default.MonitorHeart,
                    iconColor = ElectricBlue,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTo(AppScreen.HEALTH_RECORDS) }
                )

                FeatureTile(
                    title = "Habits & Water",
                    titleGu = "આદતો & પાણી",
                    subtitleGu = "દૈનિક ટ્રેકર (${waterMl}ml)",
                    icon = Icons.Default.WaterDrop,
                    iconColor = Color(0xFF00E5FF),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTo(AppScreen.HABITS_WATER) }
                )
            }

            // Grid Row 3: Education & Profile
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FeatureTile(
                    title = "Health Education",
                    titleGu = "સ્વાસ્થ્ય શિક્ષણ",
                    subtitleGu = "સરકારી યોજના & માર્ગદર્શન",
                    icon = Icons.Default.MenuBook,
                    iconColor = WarningAmber,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTo(AppScreen.HEALTH_EDUCATION) }
                )

                FeatureTile(
                    title = "My Profile",
                    titleGu = "મારી પ્રોફાઇલ",
                    subtitleGu = "સંપર્ક & સેટિંગ્સ",
                    icon = Icons.Default.Person,
                    iconColor = Color(0xFF9C27B0),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTo(AppScreen.PROFILE) }
                )
            }

            // School & Student Credits at bottom
            SchoolCreditFooter()

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun QuickVitalCard(
    title: String,
    value: String,
    status: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = title, fontSize = 11.5.sp, color = TextMuted)
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
            }
            Text(text = value, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextWhite)
            Text(text = status, fontSize = 10.5.sp, color = MedicalTeal)
        }
    }
}

@Composable
private fun FeatureTile(
    title: String,
    titleGu: String,
    subtitleGu: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconColor.copy(alpha = 0.18f))
                    .border(1.dp, iconColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = titleGu,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                maxLines = 1
            )

            Text(
                text = subtitleGu,
                fontSize = 11.sp,
                color = TextMuted,
                maxLines = 1
            )
        }
    }
}
