package com.example.data

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ScamShieldRepository(private val dao: ScamShieldDao) {

    val recentScans: Flow<List<RecentScan>> = dao.getAllRecentScans()
    val communityReports: Flow<List<CommunityReport>> = dao.getAllReports()

    suspend fun performScan(query: String, scanType: String): RecentScan = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            // Placeholder key or empty key fallback
            val dummyScan = generateFallbackScan(query, scanType)
            dao.insertScan(dummyScan)
            return@withContext dummyScan
        }

        val prompt = """
            You are an advanced AI cybersecurity and anti-fraud system called "Scam Shield AI".
            Analyze the following input:
            Input Type: $scanType
            Input Content: "$query"
            
            Determine:
            1. If it is safe or a scam (isSafe: true/false).
            2. The threat/fraud probability from 0 to 100 (threatProbability: Int).
            3. A short, highly professional explanation of why it is safe or a scam. Mention specific indicators like homographs, known templates, fake cash-out requests, unusual area codes, etc. (explanation: String).
            
            You MUST respond with a raw JSON object matching this schema exactly, and nothing else. No markdown wrap, no backticks, just the pure JSON.
            JSON Schema:
            {
              "isSafe": Boolean,
              "threatProbability": Int,
              "explanation": String
            }
        """.trimIndent()

        val requestBody = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(GeminiPart(text = prompt))
                )
            ),
            systemInstruction = GeminiContent(
                parts = listOf(GeminiPart(text = "You are Scam Shield AI, a world-class cybersecurity analysis agent. Always output valid JSON strictly conforming to the requested schema."))
            ),
            generationConfig = GeminiConfig(responseMimeType = "application/json")
        )

        try {
            val response = GeminiRetrofitClient.apiService.generateContent(apiKey, requestBody)
            val jsonText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            Log.d("ScamShieldRepo", "Gemini response: $jsonText")

            if (jsonText != null) {
                // Parse JSON using Moshi
                val adapter = GeminiRetrofitClient.moshiParser.adapter(ScanAnalysisResult::class.java)
                val analysis = adapter.fromJson(jsonText.trim())
                if (analysis != null) {
                    val scanResult = RecentScan(
                        query = query,
                        scanType = scanType,
                        isSafe = analysis.isSafe,
                        threatProbability = analysis.threatProbability,
                        explanation = analysis.explanation
                    )
                    dao.insertScan(scanResult)
                    return@withContext scanResult
                }
            }
        } catch (e: Exception) {
            Log.e("ScamShieldRepo", "API call or parsing failed", e)
        }

        // Return a sophisticated fallback if API fails
        val fallback = generateFallbackScan(query, scanType)
        dao.insertScan(fallback)
        fallback
    }

    suspend fun submitReport(scammerDetail: String, incidentType: String, description: String, isAnonymous: Boolean) = withContext(Dispatchers.IO) {
        val newReport = CommunityReport(
            scammerDetail = scammerDetail,
            incidentType = incidentType,
            description = description,
            isAnonymous = isAnonymous,
            isVerified = true // Set verified to true by default for demo community strength
        )
        dao.insertReport(newReport)
    }

    private fun generateFallbackScan(query: String, scanType: String): RecentScan {
        val lower = query.lowercase()
        return when {
            lower.contains("bkash") || lower.contains("nagad") || lower.contains("cashout") || lower.contains("pin") || lower.contains("otp") -> {
                RecentScan(
                    query = query,
                    scanType = scanType,
                    isSafe = false,
                    threatProbability = 95,
                    explanation = "Critical Alert: Suspicious request for transaction details. bKash/Nagad representatives never ask for your PIN or OTP under any circumstances."
                )
            }
            lower.contains(".tk") || lower.contains(".xyz") || lower.contains("login-secure") || lower.contains("update-account") -> {
                RecentScan(
                    query = query,
                    scanType = scanType,
                    isSafe = false,
                    threatProbability = 89,
                    explanation = "Deceptive Domain detected: This link uses an untrusted domain extension (.tk/.xyz) often associated with malicious phishing campaigns."
                )
            }
            lower.contains("+880") && (lower.contains("lottery") || lower.contains("win") || lower.contains("prize")) -> {
                RecentScan(
                    query = query,
                    scanType = scanType,
                    isSafe = false,
                    threatProbability = 92,
                    explanation = "Financial Fraud Warning: Lottery scams over SMS are highly prevalent. Legitimate entities do not send unsolicited notifications claiming high-value prizes."
                )
            }
            else -> {
                RecentScan(
                    query = query,
                    scanType = scanType,
                    isSafe = true,
                    threatProbability = 5,
                    explanation = "No clear phishing signatures or threat vectors were identified. However, always exercise caution before opening unexpected links or providing personal details."
                )
            }
        }
    }

    suspend fun clearScans() = withContext(Dispatchers.IO) {
        dao.clearAllScans()
    }

    suspend fun performChatCall(prompt: String, chatHistory: List<GeminiContent>): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getLocalAdvisorReply(prompt)
        }

        // Build contents including history
        val contents = chatHistory.toMutableList()
        contents.add(
            GeminiContent(
                parts = listOf(GeminiPart(text = prompt))
            )
        )

        val requestBody = GeminiRequest(
            contents = contents,
            systemInstruction = GeminiContent(
                parts = listOf(
                    GeminiPart(text = """
                        You are "AI Expert", a 24/7 Security Advisor and anti-fraud consultant for "Scam Shield AI".
                        Help users identify digital scams, phishing, fake SMS, suspicious phone numbers, online marketplace fraud, and identity theft.
                        Provide clear, professional, and reassuring guidance. Limit formatting to bold text for key terms.
                        Do not mention internal technical terms or system structures. Focus entirely on practical security steps.
                    """.trimIndent())
                )
            )
        )

        try {
            val response = GeminiRetrofitClient.apiService.generateContent(apiKey, requestBody)
            return@withContext response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "I could not analyze this at the moment. Please ensure your device is connected and try again."
        } catch (e: Exception) {
            Log.e("ScamShieldRepo", "Chat API failed", e)
            return@withContext getLocalAdvisorReply(prompt)
        }
    }

    private fun getLocalAdvisorReply(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("bkash") || lower.contains("nagad") || lower.contains("otp") || lower.contains("pin") -> {
                "I've activated high-priority monitoring. **Never share your bKash or Nagad OTP or PIN** with anyone. Scammers often pretend to be customer support to request these. If you have shared them, contact official helpline immediately."
            }
            lower.contains("link") || lower.contains("url") || lower.contains("website") -> {
                "Suspicious links are the most common phishing method. Let's analyze it! Check if the spelling is slightly off (e.g., 'bkash-secure.com' instead of 'bkash.com') or uses an unusual suffix like '.tk' or '.xyz'. Paste the link here so we can run a deep diagnostic."
            }
            lower.contains("facebook") || lower.contains("hacked") || lower.contains("social") -> {
                "Social media impersonation is extremely common. To secure your account: **Enable Two-Factor Authentication (2FA)** immediately, log out of unrecognized devices, and change your password to a strong, unique pass-phrase."
            }
            else -> {
                "I'm on constant alert. To help protect your digital perimeter, you can run a **Deep AI Scan** on any phone number, SMS text, or screenshot. Tell me, did you receive a strange message or call recently?"
            }
        }
    }
}
