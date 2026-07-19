package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitReportScreen(
    currentStep: Int = 1,
    scammerDetail: String = "",
    incidentType: String = "Phone Number",
    description: String = "",
    isAnonymous: Boolean = false,
    showSuccessOverlay: Boolean = false,
    onStepChange: (Int) -> Unit,
    onScammerDetailChange: (String) -> Unit,
    onIncidentTypeChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAnonymousToggle: (Boolean) -> Unit,
    onSubmitReport: () -> Unit,
    onResetReport: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToCommunity: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Submit a Report",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CyberBlue
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ObsidianBackground
                ),
                actions = {
                    IconButton(onClick = onResetReport) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Reset report form",
                            tint = CyberBlue
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                activeTab = "Reports",
                onHomeClick = onNavigateToHome,
                onScannerClick = onNavigateToScanner,
                onReportsClick = {},
                onCommunityClick = onNavigateToCommunity,
                onProfileClick = onNavigateToProfile
            )
        },
        containerColor = ObsidianBackground,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Header Intro
                Text(
                    text = "Shield the Community",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
                    fontWeight = FontWeight.Bold,
                    color = ObsidianOnSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Every verified flag feeds scam indicators into the Neural Shield database to warn others.",
                    style = MaterialTheme.typography.bodySmall,
                    color = ObsidianOnSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                // Step progress bar indicators
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StepProgressIndicator(isActive = currentStep >= 1, modifier = Modifier.weight(1f))
                    StepProgressIndicator(isActive = currentStep >= 2, modifier = Modifier.weight(1f))
                    StepProgressIndicator(isActive = currentStep >= 3, modifier = Modifier.weight(1f))
                }

                // Step content routing
                AnimatedContent(
                    targetState = currentStep,
                    transitionSpec = {
                        slideInHorizontally { width -> if (targetState > initialState) width else -width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> if (targetState > initialState) -width else width } + fadeOut()
                    },
                    label = "ReportFormTransition"
                ) { step ->
                    when (step) {
                        1 -> Step1IncidentTypeSelection(
                            selectedType = incidentType,
                            onSelect = { type ->
                                onIncidentTypeChange(type)
                                onStepChange(2)
                            }
                        )
                        2 -> Step2IncidentDetailsForm(
                            scammerDetail = scammerDetail,
                            description = description,
                            onDetailChange = onScammerDetailChange,
                            onDescChange = onDescriptionChange,
                            onNext = { onStepChange(3) },
                            onBack = { onStepChange(1) }
                        )
                        3 -> Step3EvidenceAndSubmit(
                            isAnonymous = isAnonymous,
                            onAnonToggle = onAnonymousToggle,
                            onSubmit = onSubmitReport,
                            onBack = { onStepChange(2) }
                        )
                    }
                }
            }

            // Success dialog overlay
            if (showSuccessOverlay) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ObsidianBackground.copy(alpha = 0.95f))
                        .testTag("report_success_overlay"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .background(EmeraldGreen.copy(alpha = 0.15f), CircleShape)
                                .border(1.dp, EmeraldGreen.copy(alpha = 0.3f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Report secured",
                                tint = EmeraldGreen,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        Text(
                            text = "Report Secured",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldGreen,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Your report #SH-4892 has been securely transmitted. Our AI shield is analyzing indicators to flag scammers immediately.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = ObsidianOnSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = {
                                onResetReport()
                                onNavigateToHome()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberBlue),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("return_home_success_button")
                        ) {
                            Text("Return Home", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepProgressIndicator(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(4.dp)
            .clip(RoundedCornerShape(99.dp))
            .background(if (isActive) CyberBlue else Color.White.copy(alpha = 0.1f))
    )
}

@Composable
fun Step1IncidentTypeSelection(
    selectedType: String,
    onSelect: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "1. Select Incident Type",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = ObsidianOnSurface
        )

        val items = listOf(
            Triple("Phone Number", "SMS scams or fraudulent numbers", Icons.Filled.Phone),
            Triple("Bank Account", "Wire fraud, online bank links, or phishing sites", Icons.Filled.AccountBalance),
            Triple("Social Media Page", "Fake profiles and active online imposters", Icons.Filled.Share),
            Triple("Investment Scam", "Fake trading portals, crypto, or forex scams", Icons.Filled.ShowChart),
            Triple("Job Fraud", "Fake remote work schemes or hiring deposit scams", Icons.Filled.Work)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { (title, subtitle, icon) ->
                GlassCard(
                    cornerRadius = 16.dp,
                    onClick = { onSelect(title) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("report_category_$title")
                        .border(
                            width = 1.2.dp,
                            color = if (selectedType == title) CyberBlue else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(CyberBlue.copy(alpha = 0.12f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = title,
                                tint = CyberBlue
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = ObsidianOnSurface
                            )
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = ObsidianOnSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Step2IncidentDetailsForm(
    scammerDetail: String,
    description: String,
    onDetailChange: (String) -> Unit,
    onDescChange: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    GlassCard(
        cornerRadius = 28.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("step_2_details_card")
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "2. Incident Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = ObsidianOnSurface
            )

            OutlinedTextField(
                value = scammerDetail,
                onValueChange = onDetailChange,
                label = { Text("Scammer ID / Account / Handle") },
                placeholder = { Text("e.g. +880 1712 112233") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = ObsidianOnSurface,
                    unfocusedTextColor = ObsidianOnSurface,
                    focusedBorderColor = CyberBlue,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.1f)
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scammer_detail_input")
            )

            OutlinedTextField(
                value = description,
                onValueChange = onDescChange,
                label = { Text("Description of Scam Incident") },
                placeholder = { Text("Detail how the fraud happened, what links were clicked, or requested amounts...") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = ObsidianOnSurface,
                    unfocusedTextColor = ObsidianOnSurface,
                    focusedBorderColor = CyberBlue,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.1f)
                ),
                minLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scammer_desc_input")
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onBack,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ObsidianOnSurfaceVariant),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text("Go Back")
                }
                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CyberBlue, contentColor = Color.Black),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("step_2_details_next")
                ) {
                    Text("Continue", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun Step3EvidenceAndSubmit(
    isAnonymous: Boolean,
    onAnonToggle: (Boolean) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "3. Evidence & Privacy",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = ObsidianOnSurface
        )

        // Upload Screenshots or Documents
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(28.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CloudUpload,
                    contentDescription = "Upload screenshots",
                    tint = CyberBlue,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "Attach Evidence Documents",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = ObsidianOnSurface
                )
                Text(
                    text = "Drag screenshots, SMS images, or PDF claims up to 10MB.",
                    style = MaterialTheme.typography.bodySmall,
                    color = ObsidianOnSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Privacy Toggle Option card
        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (isAnonymous) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = "Privacy Icon",
                        tint = if (isAnonymous) EmeraldGreen else CyberBlue,
                        modifier = Modifier.size(24.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "Report Anonymously",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = ObsidianOnSurface
                        )
                        Text(
                            text = "Hide your identifier from public community stream",
                            style = MaterialTheme.typography.bodySmall,
                            color = ObsidianOnSurfaceVariant
                        )
                    }
                }
                Switch(
                    checked = isAnonymous,
                    onCheckedChange = onAnonToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = EmeraldGreen,
                        uncheckedThumbColor = ObsidianOnSurfaceVariant,
                        uncheckedTrackColor = Color.White.copy(alpha = 0.05f)
                    ),
                    modifier = Modifier.testTag("anonymous_switch")
                )
            }
        }

        // Submitting buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onBack,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ObsidianOnSurfaceVariant),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text("Go Back")
            }
            Button(
                onClick = onSubmit,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen, contentColor = Color.Black),
                modifier = Modifier
                    .weight(2f)
                    .height(56.dp)
                    .testTag("submit_verified_report_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Submit check",
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Text("Submit Verified Report", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
