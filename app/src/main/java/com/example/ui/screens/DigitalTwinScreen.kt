package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DigitalTwinData
import com.example.data.model.OrganInfo
import com.example.ui.components.CyberTopBar
import com.example.ui.components.GlassCard
import com.example.ui.components.SchoolCreditFooter
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.MedicalTeal
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WarningAmber

@Composable
fun DigitalTwinScreen(
    selectedOrgan: OrganInfo,
    onSelectOrgan: (OrganInfo) -> Unit,
    onBackClick: () -> Unit,
    onSosClick: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "twinPulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val scanLineY by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scanLineY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        CyberTopBar(
            title = "મારું શરીર: ડિજિટલ ટ્વીન",
            onBackClick = onBackClick,
            onSosClick = onSosClick,
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
            // Mandatory Clear Educational Notice (per requirements)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = WarningAmber.copy(alpha = 0.12f),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarningAmber.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = WarningAmber,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "શૈક્ષણિક વિઝ્યુલાઇઝેશન: આ માત્ર માનવ શરીર રચના અને તંદુરસ્ત માહિતી સમજવા માટે છે, કોઈ તબીબી સ્કેન કે રોગનિદાન નથી.",
                        fontSize = 11.5.sp,
                        color = Color(0xFFFFD54F),
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Organ selection chips
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(DigitalTwinData.organs) { organ ->
                    val isSelected = organ.id == selectedOrgan.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) NeonCyan else Color(0xFF0C1F42))
                            .border(
                                1.dp,
                                if (isSelected) Color.White else NeonCyan.copy(alpha = 0.4f),
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { onSelectOrgan(organ) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("organ_chip_${organ.id}")
                    ) {
                        Text(
                            text = organ.nameGu.split(" ")[0],
                            fontSize = 12.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color(0xFF031626) else TextWhite
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Holographic Body Digital Twin Canvas Container
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Futuristic Cyber Body Canvas
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        val w = size.width
                        val h = size.height

                        // Grid Lines Background
                        for (y in 0..h.toInt() step 40) {
                            drawLine(
                                color = Color(0x1500F0FF),
                                start = Offset(0f, y.toFloat()),
                                end = Offset(w, y.toFloat()),
                                strokeWidth = 1f
                            )
                        }

                        // Holographic Scan Line
                        val scanY = h * scanLineY
                        drawLine(
                            brush = Brush.horizontalGradient(
                                listOf(Color.Transparent, NeonCyan.copy(alpha = 0.8f), Color.Transparent)
                            ),
                            start = Offset(w * 0.15f, scanY),
                            end = Offset(w * 0.85f, scanY),
                            strokeWidth = 2.5f
                        )

                        // Stylized Futuristic Human Silhouette
                        val bodyPath = Path()
                        val cx = w / 2

                        // Head
                        bodyPath.addOval(
                            androidx.compose.ui.geometry.Rect(
                                left = cx - 26f,
                                top = h * 0.06f,
                                right = cx + 26f,
                                bottom = h * 0.18f
                            )
                        )
                        // Neck
                        bodyPath.moveTo(cx - 10f, h * 0.18f)
                        bodyPath.lineTo(cx - 10f, h * 0.22f)
                        // Shoulders
                        bodyPath.lineTo(cx - 65f, h * 0.25f)
                        // Torso left
                        bodyPath.lineTo(cx - 48f, h * 0.52f)
                        // Waist
                        bodyPath.lineTo(cx - 38f, h * 0.60f)
                        // Hips / Pelvis
                        bodyPath.lineTo(cx - 46f, h * 0.68f)
                        // Left Leg
                        bodyPath.lineTo(cx - 36f, h * 0.94f)
                        bodyPath.lineTo(cx - 10f, h * 0.94f)
                        bodyPath.lineTo(cx - 6f, h * 0.70f)
                        // Groin center
                        bodyPath.lineTo(cx, h * 0.68f)
                        // Right Leg
                        bodyPath.lineTo(cx + 6f, h * 0.70f)
                        bodyPath.lineTo(cx + 10f, h * 0.94f)
                        bodyPath.lineTo(cx + 36f, h * 0.94f)
                        bodyPath.lineTo(cx + 46f, h * 0.68f)
                        // Waist right
                        bodyPath.lineTo(cx + 38f, h * 0.60f)
                        bodyPath.lineTo(cx + 48f, h * 0.52f)
                        // Shoulders right
                        bodyPath.lineTo(cx + 65f, h * 0.25f)
                        bodyPath.lineTo(cx + 10f, h * 0.22f)
                        bodyPath.lineTo(cx + 10f, h * 0.18f)
                        bodyPath.close()

                        // Draw Body Silhouette
                        drawPath(
                            path = bodyPath,
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0x3300F0FF),
                                    Color(0x220072FF),
                                    Color(0x1100D2B4)
                                )
                            )
                        )
                        drawPath(
                            path = bodyPath,
                            color = NeonCyan.copy(alpha = 0.5f),
                            style = Stroke(width = 1.6f)
                        )

                        // Draw Anatomical Nodes for Each Organ
                        DigitalTwinData.organs.forEach { organ ->
                            val isSelected = organ.id == selectedOrgan.id
                            val nodeX = w * organ.canvasRelativePos.x
                            val nodeY = h * organ.canvasRelativePos.y

                            if (isSelected) {
                                // Pulsing Aura around selected organ
                                drawCircle(
                                    color = NeonCyan.copy(alpha = pulseAlpha * 0.5f),
                                    radius = 24f,
                                    center = Offset(nodeX, nodeY)
                                )
                                drawCircle(
                                    color = Color.White,
                                    radius = 8f,
                                    center = Offset(nodeX, nodeY)
                                )
                                drawCircle(
                                    color = NeonCyan,
                                    radius = 12f,
                                    center = Offset(nodeX, nodeY),
                                    style = Stroke(width = 2.5f)
                                )
                            } else {
                                drawCircle(
                                    color = ElectricBlue.copy(alpha = 0.6f),
                                    radius = 6f,
                                    center = Offset(nodeX, nodeY)
                                )
                                drawCircle(
                                    color = Color.White,
                                    radius = 3f,
                                    center = Offset(nodeX, nodeY)
                                )
                            }
                        }
                    }

                    // Floating target HUD indicator for selected organ
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(14.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xE608142A))
                            .border(1.dp, NeonCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "સક્રિય અંગ ટ્રેકિંગ",
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                        Text(
                            text = selectedOrgan.nameGu.split(" ")[0],
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Detailed Interactive Organ Card
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = selectedOrgan.nameGu,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan
                            )
                            Text(
                                text = "શરીર ક્રિયા વિજ્ઞાન (Physiology)",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF0E264E))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "સક્રિય સ્થિતિ",
                                fontSize = 11.sp,
                                color = MedicalTeal,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Role
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF091730))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "મુખ્ય કાર્ય (Function):",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = selectedOrgan.roleGu,
                            fontSize = 13.sp,
                            color = TextWhite.copy(alpha = 0.9f),
                            lineHeight = 19.sp
                        )
                    }

                    // Healthy Metrics
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF091730))
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Science,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "આદર્શ તંદુરસ્ત માપદંડ:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = selectedOrgan.healthyMetricsGu,
                            fontSize = 12.5.sp,
                            color = TextWhite
                        )
                    }

                    // Interesting Health Facts
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = WarningAmber,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "રસપ્રદ તથ્યો (Fascinating Facts):",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = WarningAmber
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        selectedOrgan.factsGu.forEach { fact ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "• ",
                                    color = NeonCyan,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = fact,
                                    fontSize = 12.sp,
                                    color = TextWhite.copy(alpha = 0.9f),
                                    lineHeight = 17.sp
                                )
                            }
                        }
                    }

                    // Care tips
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MedicalTeal,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "તંદુરસ્તી માટેની દૈનિક ટિપ્સ:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedicalTeal
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        selectedOrgan.tipsGu.forEach { tip ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "✓ ",
                                    color = MedicalTeal,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = tip,
                                    fontSize = 12.sp,
                                    color = TextWhite.copy(alpha = 0.9f)
                                )
                            }
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
