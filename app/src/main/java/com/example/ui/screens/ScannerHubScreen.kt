package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.RecentScan
import com.example.ui.components.GlassCard
import com.example.ui.components.RadarHUD
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerHubScreen(
    recentScans: List<RecentScan> = emptyList(),
    isScanning: Boolean = false,
    onStartScan: (String, String) -> Unit,
    onNavigateToResult: (RecentScan) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToCommunity: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onClearHistory: () -> Unit
) {
    var searchInput by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Website URL") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Scanner Hub",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CyberBlue
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ObsidianBackground
                ),
                actions = {
                    if (recentScans.isNotEmpty()) {
                        IconButton(onClick = onClearHistory) {
                            Icon(
                                imageVector = Icons.Filled.DeleteSweep,
                                contentDescription = "Clear scan history",
                                tint = DangerRed
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                activeTab = "Scanner",
                onHomeClick = onNavigateToHome,
                onScannerClick = {},
                onReportsClick = onNavigateToReports,
                onCommunityClick = onNavigateToCommunity,
                onProfileClick = onNavigateToProfile
            )
        },
        containerColor = ObsidianBackground,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Big Central Scan Button
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    RadarHUD(
                        isScanning = isScanning,
                        pulseColor = if (isScanning) CyberBlue else EmeraldGreen,
                        modifier = Modifier.size(240.dp)
                    ) {
                        Button(
                            onClick = {
                                val query = searchInput.ifEmpty { "login-secure-bkash.tk" }
                                onStartScan(query, selectedCategory)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isScanning) CyberBlueDim else CyberBlue,
                                contentColor = Color.Black
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .size(160.dp)
                                .testTag("deep_ai_scan_button")
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = if (isScanning) Icons.Filled.Sync else Icons.Filled.Radar,
                                    contentDescription = "Radar HUD",
                                    tint = if (isScanning) Color.White else Color.Black,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (isScanning) "Scanning..." else "Deep AI Scan",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isScanning) Color.White else Color.Black
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isScanning) "SHIELD AI DETECTING SCAMS..." else "SHIELD AI SYSTEM ONLINE",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isScanning) CyberBlue else ObsidianOnSurfaceVariant.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Quick Input bar
            item {
                OutlinedTextField(
                    value = searchInput,
                    onValueChange = { searchInput = it },
                    label = { Text("Paste suspicious URL, phone number, or text...") },
                    placeholder = { Text("e.g. login-secure-bkash.tk") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = ObsidianOnSurface,
                        unfocusedTextColor = ObsidianOnSurface,
                        focusedBorderColor = CyberBlue,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                        focusedLabelColor = CyberBlue,
                        unfocusedLabelColor = ObsidianOnSurfaceVariant
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("scan_query_input"),
                    shape = RoundedCornerShape(16.dp),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (searchInput.isNotEmpty()) {
                                    onStartScan(searchInput, selectedCategory)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "Perform scan action",
                                tint = CyberBlue
                            )
                        }
                    }
                )
            }

            // Tip Card
            item {
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CyberBlue.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(CyberBlue.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Lightbulb,
                                contentDescription = "Tips",
                                tint = CyberBlue
                            )
                        }
                        Text(
                            text = "Tip: Scanning a screenshot is the fastest way to detect fake text scams.",
                            style = MaterialTheme.typography.bodySmall,
                            color = ObsidianOnSurface
                        )
                    }
                }
            }

            // Scanner Categories Grid of 8 options
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Scanner Categories",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = ObsidianOnSurface
                    )

                    // Categories Grid Rows
                    val categories = listOf(
                        "Phone Number" to Icons.Filled.Phone,
                        "bKash/Nagad" to Icons.Filled.AccountBalanceWallet,
                        "Website URL" to Icons.Filled.Language,
                        "Screenshot" to Icons.Filled.Screenshot,
                        "Voice Message" to Icons.Filled.KeyboardVoice,
                        "QR Code" to Icons.Filled.QrCode2,
                        "SMS Scan" to Icons.Filled.Sms,
                        "Social Page" to Icons.Filled.Chat
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        for (i in categories.indices step 2) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CategoryGridItem(
                                    label = categories[i].first,
                                    icon = categories[i].second,
                                    isSelected = selectedCategory == categories[i].first,
                                    onClick = { selectedCategory = categories[i].first },
                                    modifier = Modifier.weight(1f)
                                )
                                if (i + 1 < categories.size) {
                                    CategoryGridItem(
                                        label = categories[i + 1].first,
                                        icon = categories[i + 1].second,
                                        isSelected = selectedCategory == categories[i + 1].first,
                                        onClick = { selectedCategory = categories[i + 1].first },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Recent Scans Feed
            item {
                Text(
                    text = "Recent Scans",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ObsidianOnSurface
                )
            }

            if (recentScans.isEmpty()) {
                item {
                    Text(
                        text = "No recent scans. Run a Deep AI Scan above to initialize security diagnostics.",
                        style = MaterialTheme.typography.bodySmall,
                        color = ObsidianOnSurfaceVariant,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            } else {
                items(recentScans) { scan ->
                    RecentScanRowItem(
                        scan = scan,
                        onClick = { onNavigateToResult(scan) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryGridItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(
        cornerRadius = 16.dp,
        modifier = modifier
            .testTag("category_item_$label")
            .border(
                width = 1.2.dp,
                color = if (isSelected) CyberBlue else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) CyberBlue else ObsidianOnSurfaceVariant,
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 11.sp),
                color = if (isSelected) CyberBlue else ObsidianOnSurfaceVariant,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun RecentScanRowItem(
    scan: RecentScan,
    onClick: () -> Unit
) {
    GlassCard(
        cornerRadius = 16.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("recent_scan_item_${scan.id}"),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = if (scan.isSafe) EmeraldGreen.copy(alpha = 0.15f) else DangerRed.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (scan.isSafe) Icons.Filled.VerifiedUser else Icons.Filled.ReportGmailerrorred,
                        contentDescription = "Scan icon",
                        tint = if (scan.isSafe) EmeraldGreen else DangerRed
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = scan.query,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = ObsidianOnSurface,
                        maxLines = 1
                    )
                    Text(
                        text = scan.scanType,
                        style = MaterialTheme.typography.bodySmall,
                        color = ObsidianOnSurfaceVariant
                    )
                }
            }

            // Safe / Scam Status badge
            Box(
                modifier = Modifier
                    .background(
                        color = if (scan.isSafe) EmeraldGreen.copy(alpha = 0.15f) else DangerRed.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(99.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (scan.isSafe) EmeraldGreen.copy(alpha = 0.3f) else DangerRed.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(99.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(
                                color = if (scan.isSafe) EmeraldGreen else DangerRed,
                                shape = CircleShape
                            )
                    )
                    Text(
                        text = if (scan.isSafe) "Safe" else "Scam",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (scan.isSafe) EmeraldGreen else DangerRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
