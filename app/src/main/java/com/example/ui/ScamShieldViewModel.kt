package com.example.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CommunityReport
import com.example.data.GeminiContent
import com.example.data.GeminiPart
import com.example.data.RecentScan
import com.example.data.ScamShieldDatabase
import com.example.data.ScamShieldRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ShieldScreen {
    Splash,
    Dashboard,
    ScannerHub,
    ScanResult,
    AiChat,
    SubmitReport,
    Profile
}

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class ScamShieldViewModel(application: Application) : AndroidViewModel(application) {

    private val db = ScamShieldDatabase.getDatabase(application)
    private val repository = ScamShieldRepository(db.dao())

    var currentScreen by mutableStateOf(ShieldScreen.Splash)
        private set

    // Backstack for navigation
    private val backstack = mutableListOf<ShieldScreen>()

    var activeScan by mutableStateOf<RecentScan?>(null)
    var isScanning by mutableStateOf(false)
    var scanQueryInput by mutableStateOf("")
    var selectedScanType by mutableStateOf("Phone Number")

    // Chat state
    var chatMessages by mutableStateOf<List<ChatMessage>>(emptyList())
    var chatInput by mutableStateOf("")
    var isChatLoading by mutableStateOf(false)

    // Report form step management
    var reportStep by mutableStateOf(1)
    var reportScammerDetail by mutableStateOf("")
    var reportIncidentType by mutableStateOf("Phone Number")
    var reportDescription by mutableStateOf("")
    var reportIsAnonymous by mutableStateOf(false)
    var showReportSuccessOverlay by mutableStateOf(false)

    // Room Database Flows
    val recentScans: StateFlow<List<RecentScan>> = repository.recentScans.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val communityReports: StateFlow<List<CommunityReport>> = repository.communityReports.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        // Pre-populate default items if empty to secure gorgeous visual first impressions
        viewModelScope.launch {
            repository.recentScans.collect { list ->
                if (list.isEmpty()) {
                    db.dao().insertScan(RecentScan(
                        query = "+880 1712 345678",
                        scanType = "Phone Scan",
                        timestamp = System.currentTimeMillis() - 120000,
                        isSafe = true,
                        threatProbability = 4,
                        explanation = "Clean Record: No reports or malicious activity linked to this phone number. Highly likely to be safe."
                    ))
                    db.dao().insertScan(RecentScan(
                        query = "login-secure-bkash.tk",
                        scanType = "URL Scan",
                        timestamp = System.currentTimeMillis() - 900000,
                        isSafe = false,
                        threatProbability = 85,
                        explanation = "Critical Threat: Domain resolved to a known phishing portal mimicking bank details. Cryptic .tk suffix is extremely untrusted."
                    ))
                    db.dao().insertScan(RecentScan(
                        query = "Screenshot_20231024.png",
                        scanType = "Media Scan",
                        timestamp = System.currentTimeMillis() - 3600000,
                        isSafe = true,
                        threatProbability = 8,
                        explanation = "Image analyzed successfully. No phishing templates, suspicious text patterns, or QR redirection risks detected."
                    ))
                }
            }
        }

        viewModelScope.launch {
            repository.communityReports.collect { list ->
                if (list.isEmpty()) {
                    db.dao().insertReport(CommunityReport(
                        scammerDetail = "Suspicious bKash Link",
                        incidentType = "Bank Account",
                        timestamp = System.currentTimeMillis() - 172800000,
                        description = "They asked for my OTP claiming to be official customer support in order to release a reward prize.",
                        isAnonymous = false,
                        isVerified = true
                    ))
                    db.dao().insertReport(CommunityReport(
                        scammerDetail = "+880 1712... SMS",
                        incidentType = "Phone Number",
                        timestamp = System.currentTimeMillis() - 10800000,
                        description = "Sent lottery victory message with links to cash out.",
                        isAnonymous = true,
                        isVerified = false
                    ))
                    db.dao().insertReport(CommunityReport(
                        scammerDetail = "Fake Banking Portal",
                        incidentType = "Bank Account",
                        timestamp = System.currentTimeMillis() - 604800000,
                        description = "Cyrillic homograph login screen cloned from legitimate financial systems.",
                        isAnonymous = false,
                        isVerified = true
                    ))
                }
            }
        }

        // Add starting chat messages
        chatMessages = listOf(
            ChatMessage(
                text = "Hello. I am your 24/7 Security Advisor. I'm currently monitoring your digital perimeter. How can I assist you today?",
                isUser = false
            )
        )
    }

    fun navigateTo(screen: ShieldScreen) {
        if (currentScreen != ShieldScreen.Splash) {
            backstack.add(currentScreen)
        }
        currentScreen = screen
    }

    fun navigateBack(): Boolean {
        if (backstack.isNotEmpty()) {
            currentScreen = backstack.removeAt(backstack.size - 1)
            return true
        }
        return false
    }

    fun resetToSplash() {
        backstack.clear()
        currentScreen = ShieldScreen.Splash
    }

    fun startDeepScan(query: String, type: String) {
        if (query.trim().isEmpty()) return
        isScanning = true
        scanQueryInput = query
        selectedScanType = type
        navigateTo(ShieldScreen.ScannerHub)

        viewModelScope.launch {
            // Simulate neural scanning feedback animation
            kotlinx.coroutines.delay(2500)
            val result = repository.performScan(query, type)
            activeScan = result
            isScanning = false
            navigateTo(ShieldScreen.ScanResult)
        }
    }

    fun submitCommunityReport() {
        if (reportScammerDetail.trim().isEmpty() || reportDescription.trim().isEmpty()) return
        viewModelScope.launch {
            repository.submitReport(
                scammerDetail = reportScammerDetail,
                incidentType = reportIncidentType,
                description = reportDescription,
                isAnonymous = reportIsAnonymous
            )
            showReportSuccessOverlay = true
        }
    }

    fun resetReportForm() {
        reportStep = 1
        reportScammerDetail = ""
        reportIncidentType = "Phone Number"
        reportDescription = ""
        reportIsAnonymous = false
        showReportSuccessOverlay = false
    }

    fun sendChatMessage() {
        val text = chatInput.trim()
        if (text.isEmpty()) return

        val userMsg = ChatMessage(text = text, isUser = true)
        chatMessages = chatMessages + userMsg
        chatInput = ""
        isChatLoading = true

        // Create Gemini content history
        val history = chatMessages.take(chatMessages.size - 1).map {
            GeminiContent(
                parts = listOf(GeminiPart(text = it.text))
            )
        }

        viewModelScope.launch {
            val reply = repository.performChatCall(text, history)
            chatMessages = chatMessages + ChatMessage(text = reply, isUser = false)
            isChatLoading = false
        }
    }

    fun sendPresetChatMsg(text: String) {
        chatInput = text
        sendChatMessage()
    }

    fun clearAllScans() {
        viewModelScope.launch {
            repository.clearScans()
        }
    }
}
