package com.example.data.model

enum class SymptomSeverity {
    MILD,
    MODERATE,
    CRITICAL // Red flag warning sign
}

data class Symptom(
    val id: String,
    val nameGu: String,
    val descriptionGu: String,
    val severity: SymptomSeverity,
    val isRedFlag: Boolean = false,
    val categoryGu: String
)

data class SosAssessmentResult(
    val urgencyLevelGu: String,
    val isEmergency: Boolean,
    val headlineGu: String,
    val adviceGu: List<String>,
    val immediateActionsGu: List<String>,
    val followUpDoctorGu: String
)

object HealthSosData {
    val symptomsList = listOf(
        // Critical Red Flags
        Symptom(
            id = "chest_pain",
            nameGu = "તીવ્ર છાતીમાં દુખાવો અથવા ભારે દબાણ",
            descriptionGu = "છાતીની મધ્યમાં ભીંસાવું, ડાબા ખભા કે જડબા તરફ દુખાવો ફેલાવો",
            severity = SymptomSeverity.CRITICAL,
            isRedFlag = true,
            categoryGu = "હૃદય અને શ્વસન"
        ),
        Symptom(
            id = "severe_breathless",
            nameGu = "અચાનક શ્વાસ લેવામાં ભારે તકલીફ",
            descriptionGu = "વાત કરવામાં પણ શ્વાસ ખૂટી જવો અથવા હોઠ વાદળી થવા",
            severity = SymptomSeverity.CRITICAL,
            isRedFlag = true,
            categoryGu = "હૃદય અને શ્વસન"
        ),
        Symptom(
            id = "stroke_signs",
            nameGu = "ચહેરા કે હાથ-પગમાં અચાનક નબળાઈ / લકવો",
            descriptionGu = "બોલવામાં જીભ લથડવી, એક તરફ મોં વાંકું થવું (FAST લક્ષણો)",
            severity = SymptomSeverity.CRITICAL,
            isRedFlag = true,
            categoryGu = "ચેતાતંત્ર (મગજ)"
        ),
        Symptom(
            id = "unconscious",
            nameGu = "બેભાન થવું અથવા અચાનક ચક્કર આવી પડી જવું",
            descriptionGu = "જાગૃત અવસ્થા ગુમાવવી, પ્રતિસાદ ન આપવો",
            severity = SymptomSeverity.CRITICAL,
            isRedFlag = true,
            categoryGu = "ચેતાતંત્ર (મગજ)"
        ),
        Symptom(
            id = "severe_bleeding",
            nameGu = "અનિયંત્રિત રક્તસ્ત્રાવ અથવા ગંભીર ઈજા",
            descriptionGu = "દબાણ આપ્યા પછી પણ લોહી વહેવું બંધ ન થવું",
            severity = SymptomSeverity.CRITICAL,
            isRedFlag = true,
            categoryGu = "ઈજા અને અકસ્માત"
        ),

        // Moderate symptoms
        Symptom(
            id = "high_fever",
            nameGu = "૧૦૨°F થી વધુ તાવ અને સખત ધ્રુજારી",
            descriptionGu = "પેરાસીટામોલ લીધા પછી પણ તાવ ન ઉતરવો",
            severity = SymptomSeverity.MODERATE,
            categoryGu = "તાવ અને ચેપ"
        ),
        Symptom(
            id = "severe_stomach_pain",
            nameGu = "અતિશય પેટમાં ચૂંક અને ઉલટી-ઉબકા",
            descriptionGu = "પેટને સ્પર્શ કરવાથી પણ તીવ્ર પીડા થવી",
            severity = SymptomSeverity.MODERATE,
            categoryGu = "પાચન"
        ),
        Symptom(
            id = "dizziness_weakness",
            nameGu = "ચક્કર આવવા અને અતિશય નબળાઈ",
            descriptionGu = "ઊભા રહેવામાં અસમર્થતા, આંખે અંધારા આવવા",
            severity = SymptomSeverity.MODERATE,
            categoryGu = "સામાન્ય અસ્વસ્થતા"
        ),
        Symptom(
            id = "persistent_cough",
            nameGu = "સતત ખાંસી અને કફ સાથે લોહી",
            descriptionGu = "૨ અઠવાડિયાથી વધુ સમયથી ચાલતી ખાંસી",
            severity = SymptomSeverity.MODERATE,
            categoryGu = "હૃદય અને શ્વસન"
        ),

        // Mild symptoms
        Symptom(
            id = "mild_headache",
            nameGu = "હળવો માથાનો દુખાવો / માનસિક તણાવ",
            descriptionGu = "થાક કે અપૂરતી ઊંઘને કારણે થતો માથાનો દુખાવો",
            severity = SymptomSeverity.MILD,
            categoryGu = "સામાન્ય અસ્વસ્થતા"
        ),
        Symptom(
            id = "common_cold",
            nameGu = "સામાન્ય શરદી, છીંકો અને ગળામાં ખારાશ",
            descriptionGu = "હળવી ઉધરસ અને નાકમાંથી પાણી વહેવું",
            severity = SymptomSeverity.MILD,
            categoryGu = "તાવ અને ચેપ"
        ),
        Symptom(
            id = "mild_acidity",
            nameGu = "એસિડિટી, ગેસ અથવા છાતીમાં બળતરા",
            descriptionGu = "તીખો-તળેલો ખોરાક લીધા પછી પેટમાં અજંપો",
            severity = SymptomSeverity.MILD,
            categoryGu = "પાચન"
        ),
        Symptom(
            id = "muscle_pain",
            nameGu = "શરીરમાં કળતર અથવા સ્નાયુનો દુખાવો",
            descriptionGu = "કસરત કે વધારે પરિશ્રમ પછીનો દુખાવો",
            severity = SymptomSeverity.MILD,
            categoryGu = "સામાન્ય અસ્વસ્થતા"
        )
    )

    fun evaluateAssessment(
        selectedSymptomIds: Set<String>,
        durationHours: Int,
        hasVomitingOrSweating: Boolean
    ): SosAssessmentResult {
        val selected = symptomsList.filter { it.id in selectedSymptomIds }
        val hasCritical = selected.any { it.isRedFlag || it.severity == SymptomSeverity.CRITICAL } ||
                (hasVomitingOrSweating && selected.any { it.id == "chest_pain" || it.id == "dizziness_weakness" })

        if (hasCritical) {
            return SosAssessmentResult(
                urgencyLevelGu = "તાત્કાલિક કટોકટી (CRITICAL EMERGENCY)",
                isEmergency = true,
                headlineGu = "ચેતવણી: તાત્કાલિક તબીબી સહાય અને એમ્બ્યુલન્સની જરૂર છે!",
                adviceGu = listOf(
                    "આ લક્ષણો ગંભીર કટોકટી સૂચવી શકે છે. સહેજ પણ વિલંબ ન કરો.",
                    "દર્દીને આરામદાયક સ્થિતિમાં બેસાડો અથવા સુવડાવો, ભીડ એકઠી ન થવા દો.",
                    "દર્દીને ખાવા-પીવા માટે કશું ન આપો.",
                    "તાત્કાલિક ૧૧૨ અથવા ૧૦૮ ડાયલ કરો અથવા નજીકની હોસ્પિટલના ઇમરજન્સી વિભાગમાં લઈ જાઓ."
                ),
                immediateActionsGu = listOf(
                    "૧૧૨ (રાષ્ટ્રીય ઈમરજન્સી) અથવા ૧૦૮ (ગુજરાત એમ્બ્યુલન્સ) પર કૉલ કરો",
                    "પરિવારના ઇમરજન્સી સંપર્કને જાણ કરો",
                    "નજીકના પ્રાથમિક આરોગ્ય કેન્દ્ર / હોસ્પિટલમાં પહોંચો"
                ),
                followUpDoctorGu = "હોસ્પિટલ ઇમરજન્સી રુમ (Casualty / ICU) માં તાત્કાલિક દાખલ થવું."
            )
        }

        val hasModerate = selected.any { it.severity == SymptomSeverity.MODERATE } || durationHours > 48
        if (hasModerate) {
            return SosAssessmentResult(
                urgencyLevelGu = "મધ્યમ કક્ષા - ડૉક્ટરની સલાહ જરૂરી (MODERATE)",
                isEmergency = false,
                headlineGu = "આજે જ નજીકના ડૉક્ટર અથવા હેલ્થ સેન્ટરની મુલાકાત લો.",
                adviceGu = listOf(
                    "તમારા લક્ષણો સતત ચાલુ છે અથવા વધી રહ્યા છે, જેથી તબીબી તપાસ જરૂરી છે.",
                    "પૂરતો આરામ કરો અને શરીરને હાઇડ્રેટેડ રાખવા ORS, નારિયેળ પાણી અથવા ગરમ પાણી લો.",
                    "ડૉક્ટરની સલાહ વગર જાતે વધુ પડતી દવાઓ (Self-medication) ન લો."
                ),
                immediateActionsGu = listOf(
                    "૧૦૪ હેલ્પલાઇન (ગુજરાત સરકાર આરોગ્ય માર્ગદર્શન) પર સલાહ મેળવો",
                    "નજીકના પ્રાથમિક આરોગ્ય કેન્દ્ર (PHC) અથવા ફેમિલી ડૉક્ટર પાસે જાઓ"
                ),
                followUpDoctorGu = "આગામી ૨૪ કલાકમાં જનરલ ફિઝિશિયન પાસે ચેકઅપ કરાવો."
            )
        }

        return SosAssessmentResult(
            urgencyLevelGu = "હળવા લક્ષણો - ઘરેલું કાળજી અને આરામ (MILD)",
            isEmergency = false,
            headlineGu = "હળવી અસ્વસ્થતા. પૂરતો આરામ અને કાળજી લો.",
            adviceGu = listOf(
                "પૂરતી ઊંઘ (૭-૮ કલાક) લો અને આરામ કરો.",
                "હળવો, સુપાચ્ય ખોરાક જેમ કે મગની દાળની ખીચડી, સૂપ અને ગરમ પ્રવાહી લો.",
                "જો આ લક્ષણો ૨૪ થી ૪૮ કલાકમાં ન મટે અથવા વધે, તો ડૉક્ટરને જરૂર બતાવો."
            ),
            immediateActionsGu = listOf(
                "પુષ્કળ પાણી અને લીંબુ પાણી અથવા તુલસીનો ઉકાળો પીવો",
                "જો તાવ કે દુખાવો વધે તો તાપમાન અને બીપી માપતા રહો"
            ),
            followUpDoctorGu = "જો ૨ દિવસમાં સુધારો ન થાય તો નજીકના ક્લિનિકની મુલાકાત લો."
        )
    }
}
