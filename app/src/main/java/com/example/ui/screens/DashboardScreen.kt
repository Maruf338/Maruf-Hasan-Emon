package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    currentScore: Int = 98,
    onNavigateToScanner: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToCommunity: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToChat: () -> Unit
) {
    Scaffold(
        topBar = {
            Surface(
                color = ObsidianBackground.copy(alpha = 0.95f),
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.White.copy(alpha = 0.08f), Color.Transparent)
                        ),
                        shape = RoundedCornerShape(0.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "SYSTEM STATUS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp,
                            color = CyberBlue
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(EmeraldGreen, CircleShape)
                            )
                            Text(
                                text = "Scam Shield AI",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = ObsidianOnSurface
                            )
                        }
                    }
                    IconButton(
                        onClick = onNavigateToChat,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(ObsidianSurfaceContainer)
                            .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape)
                            .testTag("ai_advisor_top_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SupportAgent,
                            contentDescription = "AI Expert Advisor",
                            tint = CyberBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(
                activeTab = "Home",
                onHomeClick = {},
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
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Immersive Hero Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(CyberBlue.copy(alpha = 0.15f), Color.Transparent)
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .clickable { onNavigateToScanner() }
                    .padding(vertical = 32.dp, horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                // Background overlapping circles
                Box(modifier = Modifier.matchParentSize()) {
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .align(Alignment.Center)
                            .border(1.dp, CyberBlue.copy(alpha = 0.15f), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .align(Alignment.Center)
                            .border(1.dp, CyberBlue.copy(alpha = 0.05f), CircleShape)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Center Glowing Shield / Score Icon
                    Box(
                        modifier = Modifier.size(128.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // radial-like glow background
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .background(CyberBlue.copy(alpha = 0.20f), CircleShape)
                        )
                        // Rounded icon container
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(28.dp))
                                .background(CyberBlueContainer)
                                .border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(28.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = currentScore.toString(),
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                )
                                Text(
                                    text = "SECURE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Device Secured",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
                        fontWeight = FontWeight.Bold,
                        color = ObsidianOnSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "AI-Core actively monitoring 482 vectors",
                        style = MaterialTheme.typography.bodySmall,
                        color = ObsidianOnSurfaceVariant
                    )
                }
            }

            // Live Cyber Threats Carousel Header
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
                        text = "Live Cyber Threats",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = ObsidianOnSurface
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "LIVE",
                            style = MaterialTheme.typography.labelLarge,
                            color = CyberBlue,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Red, CircleShape)
                        )
                    }
                }

                // Threats horizontal scroll view
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ThreatFeedCard(
                        severity = "HIGH RISK",
                        time = "2m ago",
                        title = "New bKash Phishing Link Detected",
                        source = "Regional Network",
                        accentColor = DangerRed
                    )
                    ThreatFeedCard(
                        severity = "INFO",
                        time = "15m ago",
                        title = "Zero-Day Browser Patch Required",
                        source = "System Alert",
                        accentColor = CyberBlue
                    )
                    ThreatFeedCard(
                        severity = "WARNING",
                        time = "1h ago",
                        title = "Suspicious API Calls Blocked",
                        source = "AI Shield Engine",
                        accentColor = EmeraldGreen
                    )
                }
            }

            // Quick Stats Grid
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatBentoCard(
                        icon = Icons.Filled.Analytics,
                        title = "Risk Level",
                        value = "Low",
                        accentColor = EmeraldGreen,
                        modifier = Modifier.weight(1f)
                    )
                    StatBentoCard(
                        icon = Icons.Filled.Security,
                        title = "Recent Threats",
                        value = "0",
                        accentColor = CyberBlue,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatBentoCard(
                        icon = Icons.Filled.VpnLock,
                        title = "Safe Browsing",
                        value = "Active",
                        accentColor = CyberBlue,
                        modifier = Modifier.weight(1f)
                    )
                    StatBentoCard(
                        icon = Icons.Filled.Groups,
                        title = "Community Reports",
                        value = "12",
                        accentColor = EmeraldGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Weekly intelligence banner
            GlassCard(
                onClick = onNavigateToScanner,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("weekly_intelligence_banner")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Weekly Intelligence",
                            style = MaterialTheme.typography.titleMedium,
                            color = ObsidianOnSurface
                        )
                        Text(
                            text = "View your personalized security breakdown.",
                            style = MaterialTheme.typography.bodySmall,
                            color = ObsidianOnSurfaceVariant
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.TrendingUp,
                        contentDescription = "Trends Up",
                        tint = CyberBlue,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Immersive Bottom Threat Timeline Panel
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(ObsidianSurfaceContainerLow)
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.White.copy(alpha = 0.05f), Color.Transparent)
                        ),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "THREAT TIMELINE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        color = ObsidianOnSurfaceVariant
                    )
                    Text(
                        text = "View Full Log",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CyberBlue,
                        modifier = Modifier.clickable { onNavigateToScanner() }
                    )
                }

                // Timeline Item 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(EmeraldGreen.copy(alpha = 0.1f))
                            .border(1.dp, EmeraldGreen.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Safe SMS",
                            tint = EmeraldGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Secure SMS verified",
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                                fontWeight = FontWeight.Bold,
                                color = ObsidianOnSurface
                            )
                            Text(
                                text = "2m ago",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                color = ObsidianOnSurfaceVariant.copy(alpha = 0.5f)
                            )
                        }
                        Text(
                            text = "+1 (555) 012-9428 - Safe",
                            style = MaterialTheme.typography.bodySmall,
                            color = ObsidianOnSurfaceVariant
                        )
                    }
                }

                HorizontalDivider(color = Color.White.copy(alpha = 0.05f))

                // Timeline Item 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(DangerRed.copy(alpha = 0.1f))
                            .border(1.dp, DangerRed.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Block,
                            contentDescription = "Malicious Link Blocked",
                            tint = DangerRed,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Malicious Link Blocked",
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                                fontWeight = FontWeight.Bold,
                                color = ObsidianOnSurface
                            )
                            Text(
                                text = "14m ago",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                color = ObsidianOnSurfaceVariant.copy(alpha = 0.5f)
                            )
                        }
                        Text(
                            text = "bit.ly/secure-login-v42 - Phishing",
                            style = MaterialTheme.typography.bodySmall,
                            color = ObsidianOnSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ThreatFeedCard(
    severity: String,
    time: String,
    title: String,
    source: String,
    accentColor: Color
) {
    GlassCard(
        cornerRadius = 16.dp,
        modifier = Modifier
            .width(260.dp)
            .testTag("threat_feed_card_$severity")
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(accentColor.copy(alpha = 0.15f), RoundedCornerShape(999.dp))
                        .border(1.dp, accentColor.copy(alpha = 0.3f), RoundedCornerShape(999.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = severity,
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 10.sp),
                        color = accentColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = ObsidianOnSurfaceVariant.copy(alpha = 0.5f)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = ObsidianOnSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Language,
                    contentDescription = "Threat source",
                    tint = ObsidianOnSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = source,
                    style = MaterialTheme.typography.bodySmall,
                    color = ObsidianOnSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun StatBentoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    GlassCard(
        cornerRadius = 16.dp,
        modifier = modifier.testTag("stat_card_${title.replace(" ", "_").lowercase()}")
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = accentColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = ObsidianOnSurfaceVariant.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    activeTab: String,
    onHomeClick: () -> Unit,
    onScannerClick: () -> Unit,
    onReportsClick: () -> Unit,
    onCommunityClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        color = ObsidianSurfaceContainerLow.copy(alpha = 0.85f),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White.copy(alpha = 0.1f), Color.Transparent)
                ),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Filled.Home,
                label = "Home",
                isActive = activeTab == "Home",
                onClick = onHomeClick,
                modifier = Modifier.testTag("nav_home")
            )
            BottomNavItem(
                icon = Icons.Filled.Radar,
                label = "Scanner",
                isActive = activeTab == "Scanner",
                onClick = onScannerClick,
                modifier = Modifier.testTag("nav_scanner")
            )
            BottomNavItem(
                icon = Icons.Filled.Description,
                label = "Reports",
                isActive = activeTab == "Reports",
                onClick = onReportsClick,
                modifier = Modifier.testTag("nav_reports")
            )
            BottomNavItem(
                icon = Icons.Filled.Group,
                label = "Community",
                isActive = activeTab == "Community",
                onClick = onCommunityClick,
                modifier = Modifier.testTag("nav_community")
            )
            BottomNavItem(
                icon = Icons.Filled.Person,
                label = "Profile",
                isActive = activeTab == "Profile",
                onClick = onProfileClick,
                modifier = Modifier.testTag("nav_profile")
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp, horizontal = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isActive) CyberBlue else ObsidianOnSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 10.sp),
            color = if (isActive) CyberBlue else ObsidianOnSurfaceVariant.copy(alpha = 0.6f),
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}
