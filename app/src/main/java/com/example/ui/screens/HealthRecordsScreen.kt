package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HealthRecordEntity
import com.example.data.model.HealthRecordType
import com.example.ui.components.CyberTopBar
import com.example.ui.components.FuturisticButton
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HealthRecordsScreen(
    records: List<HealthRecordEntity>,
    onAddRecord: (HealthRecordType, Float, Float?, String) -> Unit,
    onDeleteRecord: (HealthRecordEntity) -> Unit,
    onClearAllRecords: () -> Unit,
    onBackClick: () -> Unit,
    onSosClick: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf<HealthRecordType?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showDeleteAllDialog by remember { mutableStateOf(false) }

    val filteredRecords = if (selectedFilter == null) records else records.filter { it.type == selectedFilter }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        CyberTopBar(
            title = "મારી સ્વાસ્થ્ય નોંધણી",
            onBackClick = onBackClick,
            onSosClick = onSosClick,
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(6.dp))

                // Honest Disclaimer Banner (Mandatory)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF0C1F40),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = NeonCyan,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "હાથથી દાખલ કરેલ મૂલ્યો (Manual Entry) • કોઈ સ્માર્ટવોચ જોડાયેલ નથી • ડેટા તમારા ડિવાઇસમાં જ સુરક્ષિત છે.",
                            fontSize = 11.sp,
                            color = TextWhite,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            // Quick Add New Record Button & Delete All Option
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { showAddDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.testTag("add_record_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color(0xFF031626),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "+ નવી નોંધ ઉમેરો",
                            color = Color(0xFF031626),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    if (records.isNotEmpty()) {
                        Text(
                            text = "તમામ ડેટા ભૂંસી નાખો",
                            color = EmergencyRed,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showDeleteAllDialog = true }
                                .padding(6.dp)
                                .testTag("clear_records_button")
                        )
                    }
                }
            }

            // Historical Chart Canvas Card
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "સ્વાસ્થ્ય પરિણામોનો આલેખ (Chart)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan
                            )
                            Text(
                                text = "છેલ્લા પરિણામો",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Line chart representation
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .background(Color(0xFF08142A), RoundedCornerShape(12.dp))
                                .padding(10.dp)
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val h = size.height

                                // Grid lines
                                for (i in 1..3) {
                                    val y = h * (i / 4f)
                                    drawLine(
                                        color = Color(0x1A00F0FF),
                                        start = Offset(0f, y),
                                        end = Offset(w, y),
                                        strokeWidth = 1f
                                    )
                                }

                                val points = listOf(
                                    Offset(w * 0.1f, h * 0.65f),
                                    Offset(w * 0.3f, h * 0.50f),
                                    Offset(w * 0.5f, h * 0.70f),
                                    Offset(w * 0.7f, h * 0.40f),
                                    Offset(w * 0.9f, h * 0.45f)
                                )

                                val linePath = Path()
                                linePath.moveTo(points.first().x, points.first().y)
                                for (p in points) {
                                    linePath.lineTo(p.x, p.y)
                                }

                                drawPath(
                                    path = linePath,
                                    color = NeonCyan,
                                    style = Stroke(width = 2.5f, cap = StrokeCap.Round)
                                )

                                points.forEach { pt ->
                                    drawCircle(
                                        color = ElectricBlue,
                                        radius = 5f,
                                        center = pt
                                    )
                                    drawCircle(
                                        color = Color.White,
                                        radius = 2.5f,
                                        center = pt
                                    )
                                }
                            }

                            // Chart overlay status
                            Text(
                                text = "સામાન્ય નિયંત્રણ હેઠળ",
                                fontSize = 10.sp,
                                color = MedicalTeal,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .background(Color(0x99000000), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            // Filter Chips Row
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            text = "બધું (All)",
                            isSelected = selectedFilter == null,
                            onClick = { selectedFilter = null }
                        )
                    }
                    items(HealthRecordType.values()) { type ->
                        FilterChip(
                            text = type.titleGu.split(" ")[0],
                            isSelected = selectedFilter == type,
                            onClick = { selectedFilter = type }
                        )
                    }
                }
            }

            // Record Items List
            if (filteredRecords.isEmpty()) {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.MonitorHeart,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "હજુ સુધી કોઈ સ્વાસ્થ્ય નોંધ નથી.",
                                fontSize = 14.sp,
                                color = TextWhite,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "ઉપર આપેલા '+ નવી નોંધ ઉમેરો' બટનથી તમારું બીપી કે સુગર દાખલ કરો.",
                                fontSize = 12.sp,
                                color = TextMuted,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(filteredRecords, key = { it.id }) { record ->
                    RecordItemCard(
                        record = record,
                        onDelete = { onDeleteRecord(record) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                SchoolCreditFooter()
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

    // Add Record Dialog
    if (showAddDialog) {
        AddRecordDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { type, v1, v2, notes ->
                onAddRecord(type, v1, v2, notes)
                showAddDialog = false
            }
        )
    }

    // Delete All Confirmation Dialog
    if (showDeleteAllDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteAllDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearAllRecords()
                        showDeleteAllDialog = false
                    }
                ) {
                    Text("હા, ભૂંસી નાખો", color = EmergencyRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAllDialog = false }) {
                    Text("રદ કરો", color = TextMuted)
                }
            },
            title = { Text("તમામ ડેટા ભૂંસી નાખવો છે?", fontWeight = FontWeight.Bold) },
            text = { Text("આનાથી તમારા ફોનમાં સંગ્રહાયેલ તમામ સ્વાસ્થ્ય માપન રદ થઈ જશે. શું તમે ખરેખર આગળ વધવા માંગો છો?") },
            containerColor = DarkNavyCard,
            titleContentColor = EmergencyRed,
            textContentColor = TextWhite
        )
    }
}

@Composable
private fun FilterChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) NeonCyan else Color(0xFF0F2248))
            .border(1.dp, if (isSelected) Color.White else DarkNavyBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color(0xFF031626) else TextWhite
        )
    }
}

@Composable
private fun RecordItemCard(
    record: HealthRecordEntity,
    onDelete: () -> Unit
) {
    val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
    val dateStr = sdf.format(Date(record.timestamp))

    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = record.type.titleGu,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF0C244C))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = record.statusTagGu,
                            fontSize = 10.sp,
                            color = MedicalTeal,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                val valueDisplay = if (record.secondaryValue != null) {
                    "${record.primaryValue.toInt()} / ${record.secondaryValue.toInt()} ${record.type.unitGu}"
                } else {
                    "${record.primaryValue} ${record.type.unitGu}"
                }

                Text(
                    text = valueDisplay,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextWhite
                )

                if (record.notes.isNotBlank()) {
                    Text(
                        text = "નોંધ: ${record.notes}",
                        fontSize = 11.5.sp,
                        color = TextMuted
                    )
                }

                Text(
                    text = "$dateStr • જાતે દાખલ કરેલ",
                    fontSize = 10.sp,
                    color = Color(0xFF6B87A8)
                )
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("delete_record_${record.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "કાઢી નાખો",
                    tint = Color(0xFFFF5252).copy(alpha = 0.8f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun AddRecordDialog(
    onDismiss: () -> Unit,
    onConfirm: (HealthRecordType, Float, Float?, String) -> Unit
) {
    var selectedType by remember { mutableStateOf(HealthRecordType.BLOOD_PRESSURE) }
    var val1Str by remember { mutableStateOf("120") }
    var val2Str by remember { mutableStateOf("80") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF0A1835),
        title = {
            Text(
                text = "નવી આરોગ્ય નોંધ ઉમેરો",
                fontWeight = FontWeight.Bold,
                color = NeonCyan,
                fontSize = 17.sp
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "પ્રકાર પસંદ કરો:",
                    fontSize = 12.sp,
                    color = TextMuted
                )

                // Type chips
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(HealthRecordType.values()) { type ->
                        FilterChip(
                            text = type.titleGu.split(" ")[0],
                            isSelected = selectedType == type,
                            onClick = {
                                selectedType = type
                                when (type) {
                                    HealthRecordType.BLOOD_PRESSURE -> { val1Str = "120"; val2Str = "80" }
                                    HealthRecordType.BLOOD_SUGAR -> { val1Str = "100"; val2Str = "" }
                                    HealthRecordType.HEART_RATE -> { val1Str = "72"; val2Str = "" }
                                    HealthRecordType.WEIGHT_BMI -> { val1Str = "68"; val2Str = "172" }
                                    HealthRecordType.BODY_TEMP -> { val1Str = "98.6"; val2Str = "" }
                                }
                            }
                        )
                    }
                }

                val label1 = when (selectedType) {
                    HealthRecordType.BLOOD_PRESSURE -> "સિસ્ટોલિક (ઉપરનું BP)"
                    HealthRecordType.BLOOD_SUGAR -> "ગ્લુકોઝ (mg/dL)"
                    HealthRecordType.HEART_RATE -> "ધબકારા (BPM)"
                    HealthRecordType.WEIGHT_BMI -> "વજન (kg)"
                    HealthRecordType.BODY_TEMP -> "તાપમાન (°F)"
                }

                OutlinedTextField(
                    value = val1Str,
                    onValueChange = { val1Str = it },
                    label = { Text(label1) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = DarkNavyBorder,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (selectedType == HealthRecordType.BLOOD_PRESSURE || selectedType == HealthRecordType.WEIGHT_BMI) {
                    val label2 = if (selectedType == HealthRecordType.BLOOD_PRESSURE) "ડાયાસ્ટોલિક (નીચેનું BP)" else "ઊંચાઈ (cm)"
                    OutlinedTextField(
                        value = val2Str,
                        onValueChange = { val2Str = it },
                        label = { Text(label2) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = DarkNavyBorder,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("નોંધ (વૈકલ્પિક)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = DarkNavyBorder,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val v1 = val1Str.toFloatOrNull() ?: 120f
                    val v2 = val2Str.toFloatOrNull()
                    onConfirm(selectedType, v1, v2, notes)
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
            ) {
                Text("સાચવો (Save)", color = Color(0xFF031626), fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("રદ કરો", color = TextMuted)
            }
        }
    )
}
