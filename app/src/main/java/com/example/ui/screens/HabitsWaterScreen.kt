package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.drawscope.Stroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HabitEntity
import com.example.ui.components.CyberTopBar
import com.example.ui.components.GlassCard
import com.example.ui.components.SchoolCreditFooter
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.MedicalTeal
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WarningAmber

@Composable
fun HabitsWaterScreen(
    habits: List<HabitEntity>,
    onToggleHabit: (HabitEntity) -> Unit,
    waterMl: Int,
    onAddWaterGlass: () -> Unit,
    onResetWater: () -> Unit,
    onBackClick: () -> Unit,
    onSosClick: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val targetWaterMl = 3000
    val waterProgress = (waterMl.toFloat() / targetWaterMl).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = waterProgress,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "waterProgress"
    )

    val completedCount = habits.count { it.isCompleted }
    val totalCount = habits.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        CyberTopBar(
            title = "આદતો અને પાણી ટ્રેકર",
            onBackClick = onBackClick,
            onSosClick = onSosClick,
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))

                // Water Intake Interactive WOW Card
                GlassCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.WaterDrop,
                                    contentDescription = null,
                                    tint = NeonCyan,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "દૈનિક પાણી ઇન્ટેક ટ્રેકર",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeonCyan
                                )
                            }

                            Text(
                                text = "રીસેટ",
                                fontSize = 11.5.sp,
                                color = TextMuted,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable(onClick = onResetWater)
                                    .padding(4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Futuristic Glass Cylinder Water Animation
                        Box(
                            modifier = Modifier
                                .size(width = 160.dp, height = 150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val h = size.height

                                // Glass Outline
                                drawRoundRect(
                                    color = Color(0x3300F0FF),
                                    topLeft = Offset(w * 0.15f, 0f),
                                    size = Size(w * 0.7f, h),
                                    cornerRadius = CornerRadius(16f, 16f),
                                    style = Stroke(width = 2.5f)
                                )

                                // Water Liquid Fill
                                val fillH = h * animatedProgress
                                val fillTop = h - fillH
                                drawRoundRect(
                                    brush = Brush.verticalGradient(
                                        listOf(
                                            Color(0xFF00E5FF),
                                            Color(0xFF0072FF)
                                        ),
                                        startY = fillTop,
                                        endY = h
                                    ),
                                    topLeft = Offset(w * 0.16f, fillTop),
                                    size = Size(w * 0.68f, fillH),
                                    cornerRadius = CornerRadius(14f, 14f)
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$waterMl / $targetWaterMl",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "મિલિલીટર (ml)",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                                Text(
                                    text = "${(waterProgress * 100).toInt()}% પૂર્ણ",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeonCyan
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // +250ml Glass Quick Add Button
                        Button(
                            onClick = onAddWaterGlass,
                            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("add_water_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color(0xFF031626),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "+૧ ગ્લાસ પાણી ઉમેરો (+૨૫૦ ml)",
                                color = Color(0xFF031626),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp
                            )
                        }
                    }
                }
            }

            // Habits Progress Header
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = null,
                                    tint = WarningAmber,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "આજની સ્વસ્થ આદતો (Habits)",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextWhite
                                )
                            }

                            Text(
                                text = "$completedCount/$totalCount પૂર્ણ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (completedCount == totalCount) SuccessGreen else NeonCyan
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        val habitRatio = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
                        LinearProgressIndicator(
                            progress = { habitRatio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = NeonCyan,
                            trackColor = Color(0xFF0F264C)
                        )
                    }
                }
            }

            // Habits Checklist Items
            items(habits, key = { it.id }) { habit ->
                val isDone = habit.isCompleted

                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    borderColor = if (isDone) SuccessGreen.copy(alpha = 0.5f) else DarkNavyBorder,
                    backgroundColor = if (isDone) Color(0xFF0C243B) else DarkNavyCard,
                    onClick = { onToggleHabit(habit) }
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
                                .background(if (isDone) SuccessGreen else Color.Transparent)
                                .border(
                                    1.5.dp,
                                    if (isDone) SuccessGreen else NeonCyan,
                                    RoundedCornerShape(6.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isDone) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color(0xFF031626),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = habit.titleGu,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isDone) TextWhite.copy(alpha = 0.7f) else TextWhite
                            )
                            Text(
                                text = habit.subtitleGu,
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF0E2548))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = habit.category,
                                fontSize = 10.sp,
                                color = NeonCyan
                            )
                        }
                    }
                }
            }

            // Sleep & Activity Journal Summary
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "ઊંઘ અને પ્રવૃત્તિ ડાયરી (Journal)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF0C1935))
                                    .padding(10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Bedtime, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("રાત્રિ ઊંઘ", fontSize = 11.sp, color = TextMuted)
                                }
                                Text("૭ કલાક ૪૫ મિનિટ", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                                Text("ગાઢ અને આરામદાયક", fontSize = 10.sp, color = MedicalTeal)
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF0C1935))
                                    .padding(10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.DirectionsRun, contentDescription = null, tint = MedicalTeal, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("દૈનિક પ્રવૃત્તિ", fontSize = 11.sp, color = TextMuted)
                                }
                                Text("૪,૮૫૦ પગલાં", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                                Text("લક્ષ્ય: ૬,૦૦૦ પગલાં", fontSize = 10.sp, color = NeonCyan)
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                SchoolCreditFooter()
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
