package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.RecentScan
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanResultScreen(
    scan: RecentScan?,
    onNavigateToHome: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToCommunity: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var isBlocked by remember { mutableStateOf(false) }

    val threatProb = scan?.threatProbability ?: 85
    val query = scan?.query ?: "login-secure-bkash.tk"
    val isSafe = scan?.isSafe ?: false
    val explanation = scan?.explanation ?: "This URL uses a deceptive homograph character. The 'a' in the domain is a Cyrillic character designed to mimic the standard Latin 'a'."

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Scan Result",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSafe) EmeraldGreen else DangerRed
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Navigate back",
                            tint = CyberBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ObsidianBackground
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                activeTab = "Scanner",
                onHomeClick = onNavigateToHome,
                onScannerClick = onNavigateToScanner,
                onReportsClick = onNavigateToReports,
                onCommunityClick = onNavigateToCommunity,
                onProfileClick = onNavigateToProfile
            )
        },
        containerColor = ObsidianBackground,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Gauge Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(200.dp)
                ) {
                    // Dynamic color based on threat probability
                    val arcColor = if (isSafe) EmeraldGreen else DangerRed

                    CircularProgressIndicator(
                        progress = { threatProb / 100f },
                        color = arcColor,
                        strokeWidth = 8.dp,
                        trackColor = Color.White.copy(alpha = 0.05f),
                        modifier = Modifier.size(190.dp)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$threatProb%",
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 42.sp
                            ),
                            color = arcColor
                        )
                        Text(
                            text = if (isSafe) "SAFE PROBABILITY" else "FRAUD PROBABILITY",
                            style = MaterialTheme.typography.labelLarge,
                            color = ObsidianOnSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }

                Text(
                    text = if (isSafe) "Verified Clean & Secure" else "Critical Threat Detected",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
                    fontWeight = FontWeight.Bold,
                    color = if (isSafe) EmeraldGreen else DangerRed,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = query,
                    style = MaterialTheme.typography.bodyLarge,
                    color = ObsidianOnSurface,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                // Tags Layout
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isSafe) {
                        ResultTag(text = "VERIFIED", color = EmeraldGreen)
                        ResultTag(text = "SAFE WEB", color = CyberBlue)
                    } else {
                        ResultTag(text = "PHISHING", color = DangerRed)
                        ResultTag(text = "IMPERSONATION", color = DangerRed)
                        ResultTag(text = "FINANCIAL FRAUD", color = DangerRed)
                    }
                }
            }

            // AI Explanation Bento box
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ai_explanation_card")
                    .border(
                        1.dp,
                        if (isSafe) EmeraldGreen.copy(alpha = 0.15f) else DangerRed.copy(alpha = 0.15f),
                        RoundedCornerShape(28.dp)
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = if (isSafe) Icons.Filled.Verified else Icons.Filled.Warning,
                        contentDescription = "AI Scan Info icon",
                        tint = if (isSafe) EmeraldGreen else DangerRed,
                        modifier = Modifier.size(24.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "AI Explanation",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ObsidianOnSurface
                        )
                        Text(
                            text = explanation,
                            style = MaterialTheme.typography.bodySmall,
                            color = ObsidianOnSurfaceVariant,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Secondary metrics grid (Trust / Domain Age)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("trust_score_bento_box")
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "TRUST SCORE",
                            style = MaterialTheme.typography.labelLarge,
                            color = ObsidianOnSurfaceVariant.copy(alpha = 0.7f)
                        )
                        val score = if (isSafe) (100 - threatProb) else (100 - threatProb)
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = score.toString(),
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp),
                                fontWeight = FontWeight.Bold,
                                color = if (isSafe) EmeraldGreen else DangerRed
                            )
                            Text(
                                text = "/ 100",
                                style = MaterialTheme.typography.bodySmall,
                                color = ObsidianOnSurfaceVariant,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                        LinearProgressIndicator(
                            progress = { score / 100f },
                            color = if (isSafe) EmeraldGreen else DangerRed,
                            trackColor = Color.White.copy(alpha = 0.05f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(99.dp))
                        )
                    }
                }

                GlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("domain_age_bento_box")
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "DOMAIN AGE",
                            style = MaterialTheme.typography.labelLarge,
                            color = ObsidianOnSurfaceVariant.copy(alpha = 0.7f)
                        )
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = if (isSafe) "345" else "2",
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp),
                                fontWeight = FontWeight.Bold,
                                color = CyberBlue
                            )
                            Text(
                                text = "DAYS",
                                style = MaterialTheme.typography.bodySmall,
                                color = ObsidianOnSurfaceVariant,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                        Text(
                            text = if (isSafe) "Established secure history" else "Highly suspicious activity",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 10.sp),
                            color = if (isSafe) EmeraldGreen else DangerRed
                        )
                    }
                }
            }

            // Community reports
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Community Reports",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ObsidianOnSurface
                )
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            brush = Brush.horizontalGradient(
                                colors = listOf(EmeraldGreen, Color.Transparent)
                            ),
                            shape = RoundedCornerShape(28.dp)
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Group,
                            contentDescription = "Community",
                            tint = CyberBlue,
                            modifier = Modifier.size(24.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = if (isSafe) "0 malicious community flags." else "14 users flagged this as a bKash scam.",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = ObsidianOnSurface
                            )
                            Text(
                                text = if (isSafe) "Shield verified secure signature." else "\"They called pretending to release dynamic cash rewards.\" - 2h ago",
                                style = MaterialTheme.typography.bodySmall,
                                color = ObsidianOnSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Recommended Actions
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Recommended Actions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ObsidianOnSurface
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionRowItem(
                        icon = Icons.Filled.Block,
                        title = "Block Sender",
                        description = "Add sender signature to native blocklist",
                        modifier = Modifier.testTag("action_block_sender")
                    )
                    ActionRowItem(
                        icon = Icons.Filled.Gavel,
                        title = "Report to Authorities",
                        description = "Forward phishing trace to central law systems",
                        modifier = Modifier.testTag("action_report_authorities")
                    )
                    ActionRowItem(
                        icon = Icons.Filled.Share,
                        title = "Alert Friends & Family",
                        description = "Distribute caution broadcast immediately",
                        modifier = Modifier.testTag("action_alert_family")
                    )
                }
            }

            // Floating / bottom high-contrast reporting action button
            Button(
                onClick = { isBlocked = !isBlocked },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isBlocked) EmeraldGreen else DangerRed,
                    contentColor = if (isBlocked) ObsidianOnSurface else Color.Black
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 4.dp)
                    .testTag("report_block_primary_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = if (isBlocked) Icons.Filled.CheckCircle else Icons.Filled.ReportGmailerrorred,
                        contentDescription = "Action button icon",
                        tint = if (isBlocked) ObsidianOnSurface else Color.Black
                    )
                    Text(
                        text = if (isBlocked) "SECURED & BLOCKED" else "REPORT & BLOCK",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isBlocked) ObsidianOnSurface else Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ResultTag(
    text: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.15f), RoundedCornerShape(999.dp))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(999.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 10.sp),
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ActionRowItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    GlassCard(
        cornerRadius = 16.dp,
        modifier = modifier,
        onClick = {}
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
                        .size(40.dp)
                        .background(CyberBlue.copy(alpha = 0.15f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = CyberBlue
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = ObsidianOnSurface
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = ObsidianOnSurfaceVariant
                    )
                }
            }
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Action details",
                tint = ObsidianOnSurfaceVariant.copy(alpha = 0.4f)
            )
        }
    }
}
