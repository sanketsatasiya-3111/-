package com.example.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HealthSosData
import com.example.data.model.SosAssessmentResult
import com.example.data.model.Symptom
import com.example.data.model.SymptomSeverity
import com.example.data.model.UserEntity
import com.example.ui.components.CyberTopBar
import com.example.ui.components.FuturisticButton
import com.example.ui.components.GlassCard
import com.example.ui.components.SchoolCreditFooter
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalTeal
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WarningAmber

@Composable
fun HealthSosScreen(
    selectedSymptoms: Set<String>,
    onToggleSymptom: (String) -> Unit,
    durationHours: Int,
    onDurationChange: (Int) -> Unit,
    hasSweatingOrVomiting: Boolean,
    onSweatingChange: (Boolean) -> Unit,
    assessmentResult: SosAssessmentResult?,
    onClearSymptoms: () -> Unit,
    currentUser: UserEntity?,
    onCallNumber: (Context, String) -> Unit,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        CyberTopBar(
            title = "હેલ્થ SOS — સુરક્ષા માર્ગદર્શિકા",
            onBackClick = onBackClick,
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Direct Emergency Assistance Action Strip
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = EmergencyRed.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, EmergencyRed),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = EmergencyRed,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "તાત્કાલિક ઈમરજન્સી હેલ્પલાઇન",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmergencyRed
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onCallNumber(context, "112") },
                            colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("call_112_button")
                        ) {
                            Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("૧૧૨ કૉલ (112)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        Button(
                            onClick = { onCallNumber(context, "108") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("call_108_button")
                        ) {
                            Icon(Icons.Default.LocalHospital, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("૧૦૮ એમ્બ્યુલન્સ", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }

                    // Emergency Contact shortcut
                    val emergencyPhone = currentUser?.emergencyContact?.takeIf { it.isNotBlank() } ?: "104"
                    val contactLabel = currentUser?.emergencyContactName?.takeIf { it.isNotBlank() } ?: "ગુજરાત હેલ્થ ૧૦૪"
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { onCallNumber(context, emergencyPhone) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF132F5C)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .testTag("call_family_button")
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "પરિવાર / રક્ષક સંપર્ક: $contactLabel ($emergencyPhone)",
                            color = NeonCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mandatory Safety Disclaimer
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF0B1936),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "માર્ગદર્શિકા પારદર્શક નિયમો પર આધારિત છે. આ એપ કોઈ રોગનિદાન કરતી નથી કે ચોક્કસ ભવિષ્ય ભાખતી નથી.",
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Symptom Selection Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "૧. હાલના લક્ષણો પસંદ કરો:",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan
                )
                if (selectedSymptoms.isNotEmpty()) {
                    Text(
                        text = "બધું સાફ કરો",
                        fontSize = 12.sp,
                        color = EmergencyRed,
                        modifier = Modifier
                            .clickable(onClick = onClearSymptoms)
                            .padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Symptoms List grouped with visual Red Flag indicators
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HealthSosData.symptomsList.forEach { symptom ->
                    val isChecked = selectedSymptoms.contains(symptom.id)
                    val cardBorder = when {
                        isChecked && symptom.isRedFlag -> EmergencyRed
                        isChecked -> NeonCyan
                        symptom.isRedFlag -> EmergencyRed.copy(alpha = 0.35f)
                        else -> DarkNavyBorder
                    }

                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = cardBorder,
                        backgroundColor = if (isChecked) Color(0xFF0F264E) else DarkNavyCard,
                        onClick = { onToggleSymptom(symptom.id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (isChecked) {
                                            if (symptom.isRedFlag) EmergencyRed else NeonCyan
                                        } else Color.Transparent
                                    )
                                    .border(
                                        1.5.dp,
                                        if (symptom.isRedFlag) EmergencyRed else NeonCyan,
                                        RoundedCornerShape(6.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isChecked) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = if (symptom.isRedFlag) Color.White else Color(0xFF031626),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (symptom.isRedFlag) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(EmergencyRed)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "રેડ ફ્લેગ (કટોકટી)",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                    Text(
                                        text = symptom.nameGu,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (symptom.isRedFlag) Color(0xFFFF859B) else TextWhite
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = symptom.descriptionGu,
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Follow-up Questions Card
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "૨. ફોલો-અપ પ્રશ્નો (અવધિ અને સહ-લક્ષણો):",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan
                    )

                    // Duration Slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "લક્ષણ કેટલા સમયથી છે?",
                                fontSize = 12.sp,
                                color = TextWhite
                            )
                            Text(
                                text = "$durationHours કલાક",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan
                            )
                        }
                        Slider(
                            value = durationHours.toFloat(),
                            onValueChange = { onDurationChange(it.toInt()) },
                            valueRange = 1f..72f,
                            colors = SliderDefaults.colors(
                                thumbColor = NeonCyan,
                                activeTrackColor = NeonCyan,
                                inactiveTrackColor = DarkNavyBorder
                            )
                        )
                    }

                    // Vomiting / Sweating Checkbox
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSweatingChange(!hasSweatingOrVomiting) }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = hasSweatingOrVomiting,
                            onCheckedChange = { onSweatingChange(it) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = NeonCyan,
                                uncheckedColor = DarkNavyBorder
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "શું અતિશય પરસેવો, ગભરામણ કે ઉલટી થઈ રહી છે?",
                            fontSize = 12.5.sp,
                            color = TextWhite
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Transparent Rule-based Assessment Output Card
            AnimatedVisibility(visible = assessmentResult != null) {
                if (assessmentResult != null) {
                    val resultColor = if (assessmentResult.isEmergency) EmergencyRed else MedicalTeal

                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = resultColor,
                        backgroundColor = if (assessmentResult.isEmergency) Color(0x33FF2E63) else Color(0x3300D2B4)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (assessmentResult.isEmergency) Icons.Default.Warning else Icons.Default.Info,
                                    contentDescription = null,
                                    tint = resultColor,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = assessmentResult.urgencyLevelGu,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = resultColor
                                )
                            }

                            Text(
                                text = assessmentResult.headlineGu,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )

                            // Advice list
                            Column {
                                Text(
                                    text = "સુરક્ષા માર્ગદર્શન:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = NeonCyan
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                assessmentResult.adviceGu.forEach { advice ->
                                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                        Text("• ", color = NeonCyan, fontWeight = FontWeight.Bold)
                                        Text(text = advice, fontSize = 12.sp, color = TextWhite, lineHeight = 16.sp)
                                    }
                                }
                            }

                            // Immediate Actions
                            Column {
                                Text(
                                    text = "ત્વરિત પગલાં:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = WarningAmber
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                assessmentResult.immediateActionsGu.forEach { action ->
                                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                        Text("→ ", color = WarningAmber, fontWeight = FontWeight.Bold)
                                        Text(text = action, fontSize = 12.sp, color = TextWhite)
                                    }
                                }
                            }

                            // Follow-up
                            Text(
                                text = "તબીબ ભલામણ: ${assessmentResult.followUpDoctorGu}",
                                fontSize = 11.5.sp,
                                color = TextMuted
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            SchoolCreditFooter()
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
