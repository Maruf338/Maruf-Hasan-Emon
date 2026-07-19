package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.ScamShieldViewModel
import com.example.ui.ShieldScreen
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ScamShieldViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.ui.graphics.Color(0xFF0B1322)
                ) {
                    val scans by viewModel.recentScans.collectAsState()
                    val reports by viewModel.communityReports.collectAsState()

                    // Intercept back presses to navigate smoothly inside our custom stack
                    BackHandler(enabled = viewModel.currentScreen != ShieldScreen.Splash && viewModel.currentScreen != ShieldScreen.Dashboard) {
                        viewModel.navigateBack()
                    }

                    Crossfade(targetState = viewModel.currentScreen, label = "ScreenTransition") { screen ->
                        when (screen) {
                            ShieldScreen.Splash -> {
                                SplashScreen(
                                    onSplashFinished = {
                                        viewModel.navigateTo(ShieldScreen.Dashboard)
                                    }
                                )
                            }
                            ShieldScreen.Dashboard -> {
                                DashboardScreen(
                                    currentScore = 98,
                                    onNavigateToScanner = { viewModel.navigateTo(ShieldScreen.ScannerHub) },
                                    onNavigateToReports = { viewModel.navigateTo(ShieldScreen.SubmitReport) },
                                    onNavigateToCommunity = { viewModel.navigateTo(ShieldScreen.ScannerHub) }, // Reuse scanner for latest cyber indicators
                                    onNavigateToProfile = { viewModel.navigateTo(ShieldScreen.Profile) },
                                    onNavigateToChat = { viewModel.navigateTo(ShieldScreen.AiChat) }
                                )
                            }
                            ShieldScreen.ScannerHub -> {
                                ScannerHubScreen(
                                    recentScans = scans,
                                    isScanning = viewModel.isScanning,
                                    onStartScan = { query, type ->
                                        viewModel.startDeepScan(query, type)
                                    },
                                    onNavigateToResult = { scan ->
                                        viewModel.activeScan = scan
                                        viewModel.navigateTo(ShieldScreen.ScanResult)
                                    },
                                    onNavigateToHome = { viewModel.navigateTo(ShieldScreen.Dashboard) },
                                    onNavigateToReports = { viewModel.navigateTo(ShieldScreen.SubmitReport) },
                                    onNavigateToCommunity = { viewModel.navigateTo(ShieldScreen.ScannerHub) },
                                    onNavigateToProfile = { viewModel.navigateTo(ShieldScreen.Profile) },
                                    onClearHistory = { viewModel.clearAllScans() }
                                )
                            }
                            ShieldScreen.ScanResult -> {
                                ScanResultScreen(
                                    scan = viewModel.activeScan,
                                    onNavigateToHome = { viewModel.navigateTo(ShieldScreen.Dashboard) },
                                    onNavigateToScanner = { viewModel.navigateTo(ShieldScreen.ScannerHub) },
                                    onNavigateToReports = { viewModel.navigateTo(ShieldScreen.SubmitReport) },
                                    onNavigateToCommunity = { viewModel.navigateTo(ShieldScreen.ScannerHub) },
                                    onNavigateToProfile = { viewModel.navigateTo(ShieldScreen.Profile) },
                                    onNavigateBack = { viewModel.navigateBack() }
                                )
                            }
                            ShieldScreen.AiChat -> {
                                ChatScreen(
                                    messages = viewModel.chatMessages,
                                    chatInput = viewModel.chatInput,
                                    isLoading = viewModel.isChatLoading,
                                    onInputChange = { viewModel.chatInput = it },
                                    onSendMessage = { viewModel.sendChatMessage() },
                                    onSendPreset = { viewModel.sendPresetChatMsg(it) },
                                    onNavigateToHome = { viewModel.navigateTo(ShieldScreen.Dashboard) },
                                    onNavigateToScanner = { viewModel.navigateTo(ShieldScreen.ScannerHub) },
                                    onNavigateToReports = { viewModel.navigateTo(ShieldScreen.SubmitReport) },
                                    onNavigateToCommunity = { viewModel.navigateTo(ShieldScreen.ScannerHub) },
                                    onNavigateToProfile = { viewModel.navigateTo(ShieldScreen.Profile) }
                                )
                            }
                            ShieldScreen.SubmitReport -> {
                                SubmitReportScreen(
                                    currentStep = viewModel.reportStep,
                                    scammerDetail = viewModel.reportScammerDetail,
                                    incidentType = viewModel.reportIncidentType,
                                    description = viewModel.reportDescription,
                                    isAnonymous = viewModel.reportIsAnonymous,
                                    showSuccessOverlay = viewModel.showReportSuccessOverlay,
                                    onStepChange = { viewModel.reportStep = it },
                                    onScammerDetailChange = { viewModel.reportScammerDetail = it },
                                    onIncidentTypeChange = { viewModel.reportIncidentType = it },
                                    onDescriptionChange = { viewModel.reportDescription = it },
                                    onAnonymousToggle = { viewModel.reportIsAnonymous = it },
                                    onSubmitReport = { viewModel.submitCommunityReport() },
                                    onResetReport = { viewModel.resetReportForm() },
                                    onNavigateToHome = { viewModel.navigateTo(ShieldScreen.Dashboard) },
                                    onNavigateToScanner = { viewModel.navigateTo(ShieldScreen.ScannerHub) },
                                    onNavigateToCommunity = { viewModel.navigateTo(ShieldScreen.ScannerHub) },
                                    onNavigateToProfile = { viewModel.navigateTo(ShieldScreen.Profile) }
                                )
                            }
                            ShieldScreen.Profile -> {
                                ProfileScreen(
                                    submittedReports = reports,
                                    onNavigateToHome = { viewModel.navigateTo(ShieldScreen.Dashboard) },
                                    onNavigateToScanner = { viewModel.navigateTo(ShieldScreen.ScannerHub) },
                                    onNavigateToReports = { viewModel.navigateTo(ShieldScreen.SubmitReport) },
                                    onNavigateToCommunity = { viewModel.navigateTo(ShieldScreen.ScannerHub) },
                                    onNavigateToProfile = {}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
