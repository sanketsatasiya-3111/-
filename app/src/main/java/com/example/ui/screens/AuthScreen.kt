package com.example.ui.screens

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FuturisticButton
import com.example.ui.components.GlassCard
import com.example.ui.components.SchoolCreditFooter
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.MedicalTeal
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun AuthScreen(
    onLoginSuccess: () -> Unit,
    onLoginClick: (String, String, (Boolean, String) -> Unit) -> Unit,
    onRegisterClick: (String, String, String, String, String, String, String, String, (Boolean, String) -> Unit) -> Unit,
    onDemoLoginClick: () -> Unit,
    isDarkTheme: Boolean = true
) {
    var isRegisterMode by remember { mutableStateOf(false) }

    // Form states
    var contact by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    // Registration extra fields
    var fullName by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var ageStr by remember { mutableStateOf("") }
    var emergencyContact by remember { mutableStateOf("") }
    var emergencyName by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("B+") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    val bloodGroups = listOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Logo & Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(NeonCyan, ElectricBlue))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ગુ",
                        color = Color(0xFF031626),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "ગુજરાત આરોગ્ય કેન્દ્ર",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "સુરક્ષિત આરોગ્ય સેવા પોર્ટલ",
                        fontSize = 12.sp,
                        color = NeonCyan
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Demo Mode Notice Banner
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF0A2242),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.35f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "ડેમો મોડ: સ્થાનિક સુરક્ષિત સંગ્રહ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan
                        )
                        Text(
                            text = "માહિતી તમારા ડિવાઇસમાં સુરક્ષિત રીતે સંગ્રહિત થશે. પૂર્વાવલોકન માટે ડેમો બટન વાપરી શકો છો.",
                            fontSize = 11.sp,
                            color = TextMuted,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tabs for Login / Register
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0C1935))
                    .padding(4.dp)
            ) {
                val loginBgModifier = if (!isRegisterMode) {
                    Modifier.background(Brush.horizontalGradient(listOf(NeonCyan, ElectricBlue)))
                } else {
                    Modifier.background(Color.Transparent)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .then(loginBgModifier)
                        .clickable {
                            isRegisterMode = false
                            errorMessage = null
                            successMessage = null
                        }
                        .padding(vertical = 10.dp)
                        .testTag("tab_login"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "પ્રવેશ (Login)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (!isRegisterMode) Color(0xFF031626) else TextMuted
                    )
                }

                val registerBgModifier = if (isRegisterMode) {
                    Modifier.background(Brush.horizontalGradient(listOf(NeonCyan, ElectricBlue)))
                } else {
                    Modifier.background(Color.Transparent)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .then(registerBgModifier)
                        .clickable {
                            isRegisterMode = true
                            errorMessage = null
                            successMessage = null
                        }
                        .padding(vertical = 10.dp)
                        .testTag("tab_register"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "નવું ખાતું (Register)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (isRegisterMode) Color(0xFF031626) else TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Error / Success feedback
            if (errorMessage != null) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = EmergencyRed.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, EmergencyRed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Text(
                        text = errorMessage ?: "",
                        color = Color(0xFFFF859B),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (successMessage != null) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = SuccessGreen.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Text(
                        text = successMessage ?: "",
                        color = SuccessGreen,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Input Form Card
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    if (isRegisterMode) {
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = { Text("પૂરું નામ (Full Name)") },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null, tint = NeonCyan)
                            },
                            colors = getTextFieldColors(),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("register_fullname_input")
                        )
                    }

                    OutlinedTextField(
                        value = contact,
                        onValueChange = { contact = it },
                        label = { Text("ઈમેઇલ અથવા મોબાઈલ નંબર") },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = NeonCyan)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = getTextFieldColors(),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_contact_input")
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("પાસવર્ડ (Password)") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = NeonCyan)
                        },
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (showPassword) "પાસવર્ડ છુપાવો" else "પાસવર્ડ જુઓ",
                                    tint = TextMuted
                                )
                            }
                        },
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = getTextFieldColors(),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_password_input")
                    )

                    if (isRegisterMode) {
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("પાસવર્ડની પુષ્ટિ (Confirm Password)") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = NeonCyan)
                            },
                            trailingIcon = {
                                IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                                    Icon(
                                        imageVector = if (showConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = null,
                                        tint = TextMuted
                                    )
                                }
                            },
                            visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            colors = getTextFieldColors(),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("register_confirm_password_input")
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = ageStr,
                                onValueChange = { ageStr = it.take(3) },
                                label = { Text("ઉંમર (Age)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = getTextFieldColors(),
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("register_age_input")
                            )

                            // Blood Group Chip selector
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "બ્લડ ગ્રૂપ: $bloodGroup",
                                    fontSize = 12.sp,
                                    color = NeonCyan,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    listOf("B+", "O+", "A+", "AB+").forEach { bg ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (bloodGroup == bg) NeonCyan else Color(0xFF132448))
                                                .clickable { bloodGroup = bg }
                                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = bg,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (bloodGroup == bg) Color(0xFF031626) else TextWhite
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        OutlinedTextField(
                            value = emergencyContact,
                            onValueChange = { emergencyContact = it },
                            label = { Text("ઇમરજન્સી સંપર્ક નંબર (વૈકલ્પિક)") },
                            leadingIcon = {
                                Icon(Icons.Default.ContactPhone, contentDescription = null, tint = EmergencyRed)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            colors = getTextFieldColors(),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("register_emergency_input")
                        )

                        OutlinedTextField(
                            value = emergencyName,
                            onValueChange = { emergencyName = it },
                            label = { Text("સંપર્કનું નામ (દા.ત. પિતા, માતા)") },
                            colors = getTextFieldColors(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Submit Button
                    FuturisticButton(
                        text = if (isRegisterMode) "ખાતું બનાવો (Create Account)" else "પ્રવેશ કરો (Login) →",
                        onClick = {
                            errorMessage = null
                            if (isRegisterMode) {
                                onRegisterClick(
                                    fullName,
                                    contact,
                                    password,
                                    confirmPassword,
                                    ageStr,
                                    emergencyContact,
                                    emergencyName,
                                    bloodGroup
                                ) { ok, msg ->
                                    if (!ok) errorMessage = msg
                                }
                            } else {
                                onLoginClick(contact, password) { ok, msg ->
                                    if (!ok) errorMessage = msg
                                }
                            }
                        },
                        testTag = if (isRegisterMode) "register_submit_button" else "login_submit_button"
                    )

                    // Quick Demo Login Button
                    Button(
                        onClick = onDemoLoginClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF102852)
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("demo_access_button")
                    ) {
                        Text(
                            text = "⚡ ડેમો તરીકે સીધા આગળ વધો (Demo Mode)",
                            color = NeonCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SchoolCreditFooter()

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun getTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = NeonCyan,
    unfocusedBorderColor = DarkNavyBorder,
    focusedLabelColor = NeonCyan,
    unfocusedLabelColor = TextMuted,
    focusedTextColor = TextWhite,
    unfocusedTextColor = TextWhite,
    cursorColor = NeonCyan
)
