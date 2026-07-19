package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.CommunityReport
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    submittedReports: List<CommunityReport> = emptyList(),
    onNavigateToHome: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToCommunity: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Sentinel Profile",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CyberBlue
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ObsidianBackground
                ),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "System setup",
                            tint = CyberBlue
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                activeTab = "Profile",
                onHomeClick = onNavigateToHome,
                onScannerClick = onNavigateToScanner,
                onReportsClick = onNavigateToReports,
                onCommunityClick = onNavigateToCommunity,
                onProfileClick = {}
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
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Profile Header containing user portrait
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(112.dp)
                            .background(
                                brush = Brush.sweepGradient(
                                    colors = listOf(CyberBlue, EmeraldGreen, CyberBlue)
                                ),
                                shape = CircleShape
                            )
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(ObsidianSurface, CircleShape)
                                .clip(CircleShape)
                        ) {
                            // Centered generated gorgeous cyber logo acting as user portrait
                            Image(
                                painter = painterResource(id = R.drawable.scam_shield_logo_1784426150794),
                                contentDescription = "Alex Rivera Portrait",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    // Pro Badge overlay
                    Box(
                        modifier = Modifier
                            .background(EmeraldGreenContainer, RoundedCornerShape(99.dp))
                            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(99.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "PRO MEMBER",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 9.sp),
                            color = EmeraldGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Alex Rivera",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = ObsidianOnSurface
                    )
                    Text(
                        text = "alex.rivera@sentinel.ai",
                        style = MaterialTheme.typography.bodySmall,
                        color = ObsidianOnSurfaceVariant
                    )
                }
            }

            // Protection Score Card
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("protection_score_card")
                    .border(
                        1.dp,
                        Brush.horizontalGradient(colors = listOf(CyberBlue.copy(alpha = 0.2f), Color.Transparent)),
                        RoundedCornerShape(28.dp)
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "PROTECTION SCORE",
                            style = MaterialTheme.typography.labelLarge,
                            color = CyberBlue,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "94",
                                style = MaterialTheme.typography.displayLarge.copy(fontSize = 36.sp),
                                fontWeight = FontWeight.Bold,
                                color = ObsidianOnSurface
                            )
                            Text(
                                text = "XP",
                                style = MaterialTheme.typography.titleMedium,
                                color = CyberBlue,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.TrendingUp,
                                contentDescription = "Trend metric",
                                tint = EmeraldGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Stronger than 92% of users",
                                style = MaterialTheme.typography.bodySmall,
                                color = EmeraldGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Progress Ring
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(72.dp)
                    ) {
                        CircularProgressIndicator(
                            progress = { 0.94f },
                            color = CyberBlue,
                            strokeWidth = 4.dp,
                            trackColor = Color.White.copy(alpha = 0.05f),
                            modifier = Modifier.size(68.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.VerifiedUser,
                            contentDescription = "Shield score icon",
                            tint = CyberBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Account settings list section
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Account Settings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ObsidianOnSurfaceVariant.copy(alpha = 0.8f)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SettingsRowItem(icon = Icons.Filled.Person, label = "Personal Info", testTag = "settings_personal_info")
                    SettingsRowItem(icon = Icons.Filled.Shield, label = "Security & Privacy", testTag = "settings_security_privacy")
                    SettingsRowItem(icon = Icons.Filled.NotificationsActive, label = "Notifications", testTag = "settings_notifications")
                    SettingsRowItem(icon = Icons.Filled.Link, label = "Linked Accounts", testTag = "settings_linked_accounts")
                }
            }

            // My submitted reports list
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Submitted Reports",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = ObsidianOnSurfaceVariant.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "View All",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberBlue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {}
                    )
                }

                if (submittedReports.isEmpty()) {
                    Text(
                        text = "You haven't submitted any scam reports yet. Tap the Reports tab below to flag scammers.",
                        style = MaterialTheme.typography.bodySmall,
                        color = ObsidianOnSurfaceVariant,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        submittedReports.forEach { report ->
                            SubmittedReportRowItem(report = report)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsRowItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    testTag: String
) {
    GlassCard(
        cornerRadius = 16.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        onClick = {}
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(CyberBlue.copy(alpha = 0.12f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = CyberBlue
                    )
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = ObsidianOnSurface
                )
            }
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Details",
                tint = ObsidianOnSurfaceVariant.copy(alpha = 0.4f)
            )
        }
    }
}

@Composable
fun SubmittedReportRowItem(
    report: CommunityReport
) {
    GlassCard(
        cornerRadius = 16.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("submitted_report_row_${report.id}")
            .border(
                width = 1.dp,
                color = if (report.isVerified) EmeraldGreen.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(16.dp)
            )
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
                            color = if (report.isVerified) EmeraldGreen.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.05f),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (report.isVerified) Icons.Filled.Verified else Icons.Filled.History,
                        contentDescription = "Verified reports",
                        tint = if (report.isVerified) EmeraldGreen else ObsidianOnSurfaceVariant
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = report.scammerDetail,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = ObsidianOnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = report.incidentType,
                        style = MaterialTheme.typography.bodySmall,
                        color = ObsidianOnSurfaceVariant
                    )
                }
            }

            // Verified / Pending status chip
            Box(
                modifier = Modifier
                    .background(
                        color = if (report.isVerified) EmeraldGreen.copy(alpha = 0.12f) else Color.White.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(99.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (report.isVerified) EmeraldGreen.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(99.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            ) {
                Text(
                    text = if (report.isVerified) "Verified" else "Pending",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 10.sp),
                    color = if (report.isVerified) EmeraldGreen else ObsidianOnSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
