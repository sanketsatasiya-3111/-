package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

data class EducationArticle(
    val id: String,
    val titleGu: String,
    val categoryGu: String,
    val summaryGu: String,
    val pointsGu: List<String>
)

@Composable
fun HealthEducationScreen(
    onBackClick: () -> Unit,
    onSosClick: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val articles = listOf(
        EducationArticle(
            id = "scheme",
            titleGu = "આયુષ્માન ભારત અને 'મા' (MAA) અમૃતમ યોજના",
            categoryGu = "સરકારી સહાય",
            summaryGu = "ગુજરાતના નાગરિકો માટે ₹૫ થી ૧૦ લાખ સુધીની કેશલેસ તબીબી સારવાર યોજના.",
            pointsGu = listOf(
                "ગંભીર બીમારીઓ જેવી કે કેન્સર, હાર્ટ સર્જરી, કિડની ડાયાલિસિસ અને ન્યુરો સર્જરીમાં વિનામૂલ્યે સારવાર.",
                "ગુજરાતની સરકારી અને માન્યતા પ્રાપ્ત ખાનગી હોસ્પિટલોમાં માન્ય.",
                "આરોગ્ય કેન્દ્ર (PHC / CHC) માંથી ઈ-કાર્ડ કઢાવી શકાય છે.",
                "માહિતી માટે ટોલ-ફ્રી નંબર ૧૪૫૫૫ અથવા ૧૦૪ પર સંપર્ક કરી શકો છો."
            )
        ),
        EducationArticle(
            id = "lifestyle",
            titleGu = "ઋતુચર્યા: શિયાળા અને ઉનાળામાં સ્વાસ્થ્ય રક્ષણ",
            categoryGu = "આયુર્વેદ & જીવનશૈલી",
            summaryGu = "ભારતીય ઋતુઓ પ્રમાણે આહાર અને વિહારના શાસ્ત્રોક્ત નિયમો.",
            pointsGu = listOf(
                "શિયાળામાં પાચનશક્તિ તેજ હોય છે, તેથી અડદિયા, સૂકો મેવો અને તલ-ગોળનું સેવન ગુણકારી છે.",
                "ઉનાળામાં ડીહાઇડ્રેશન ટાળવા લીંબુ પાણી, વરિયાળીનું શરબત, કાચી કેરીનો બાફલો અને છાશ પીવી.",
                "ચોમાસામાં ગરમ અને ઉકાળેલું પાણી પીવું તથા બહારનો ખુલ્લો ખોરાક ટાળવો."
            )
        ),
        EducationArticle(
            id = "first_aid",
            titleGu = "પ્રાથમિક સારવાર (First Aid) ની સુવર્ણ માર્ગદર્શિકા",
            categoryGu = "ઇમરજન્સી માર્ગદર્શન",
            summaryGu = "ઈજા, દાઝી જવું કે બેભાન અવસ્થામાં શું કરવું અને શું ન કરવું.",
            pointsGu = listOf(
                "દાઝી ગયા હોવ તો તરત સામાન્ય નળના ઠંડા વહેતા પાણી નીચે ૧૫ મિનિટ અંગ રાખો. બરફ કે ટૂથપેસ્ટ ન લગાવો.",
                "ફેક્ચર કે હાડકાની ઈજામાં તે અંગને હલાવ્યા વગર કાર્ડબોર્ડ કે લાકડી વડે સ્થિર (Splint) કરો.",
                "સાપ કે વીંછી કરડે ત્યારે દોરીથી અતિશય ટાઈટ ન બાંધો અને તરત દર્દીને હોસ્પિટલ પહોંચાડો."
            )
        ),
        EducationArticle(
            id = "mental",
            titleGu = "વિદ્યાર્થીઓ અને યુવાનો માટે તણાવ મુક્તિ (Stress Free)",
            categoryGu = "માનસિક સ્વાસ્થ્ય",
            summaryGu = "પરીક્ષા અને જીવનની દોડધામમાં મનની એકાગ્રતા વધારવાના ઉપાય.",
            pointsGu = listOf(
                "રોજ સવારે ૧૫ મિનિટ ધ્યાનમાં બેસો અને ધીમા ઊંડા શ્વાસ લો.",
                "મોબાઈલનો સ્ક્રીન ટાઈમ મર્યાદિત કરો અને દરરોજ ૧ કલાક રમત-ગમતમાં વિતાવો.",
                "કોઈપણ ચિંતા કે મુશ્કેલી હોય તો માતા-પિતા કે શિક્ષક સાથે ખુલ્લા મને વાત કરો."
            )
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        CyberTopBar(
            title = "સ્વાસ્થ્ય શિક્ષણ અને જાગૃતિ",
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
                Text(
                    text = "પ્રમાણિત ગુજરાતી આરોગ્ય માહિતી:",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan
                )
            }

            items(articles, key = { it.id }) { article ->
                var expanded by remember { mutableStateOf(false) }

                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    onClick = { expanded = !expanded }
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF0D254C))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = article.categoryGu,
                                    fontSize = 11.sp,
                                    color = MedicalTeal,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Icon(
                                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = NeonCyan
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = article.titleGu,
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = article.summaryGu,
                            fontSize = 12.sp,
                            color = TextMuted,
                            lineHeight = 17.sp
                        )

                        AnimatedVisibility(visible = expanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF08142A))
                                    .padding(10.dp)
                            ) {
                                article.pointsGu.forEach { pt ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 3.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("✓ ", color = NeonCyan, fontWeight = FontWeight.Bold)
                                        Text(text = pt, fontSize = 12.sp, color = TextWhite, lineHeight = 17.sp)
                                    }
                                }
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
