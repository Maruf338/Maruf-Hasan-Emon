package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.ChatMessage
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    messages: List<ChatMessage> = emptyList(),
    chatInput: String = "",
    isLoading: Boolean = false,
    onInputChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    onSendPreset: (String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToCommunity: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val listState = rememberLazyListState()

    // Scroll to bottom on load or new messages
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Profile Avatar
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(CyberBlue.copy(alpha = 0.15f))
                                .border(1.dp, CyberBlue.copy(alpha = 0.3f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.SupportAgent,
                                contentDescription = "AI Expert",
                                tint = CyberBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "AI Security Expert",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                ),
                                color = ObsidianOnSurface
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(EmeraldGreen, CircleShape)
                                )
                                Text(
                                    text = "ONLINE",
                                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 9.sp),
                                    color = EmeraldGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ObsidianBackground
                ),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Security,
                            contentDescription = "Advisor encryption active",
                            tint = CyberBlue
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                activeTab = "Home", // Keeping Advisor linked on Home Flow tab
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
        ) {
            // Chat messages list
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                items(messages) { msg ->
                    ChatBubble(message = msg)
                }

                if (isLoading) {
                    item {
                        ThinkingIndicator()
                    }
                }
            }

            // Suggestions Carousel and Input panel
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ObsidianBackground)
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Preset Suggestion Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    PresetChip(text = "Is this link safe?", onClick = { onSendPreset("Is this link safe?") })
                    PresetChip(text = "Help with OTP fraud", onClick = { onSendPreset("Help with OTP fraud") })
                    PresetChip(text = "Secure my Facebook", onClick = { onSendPreset("Secure my Facebook account") })
                }

                // Chat text input bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .background(ObsidianSurfaceContainer, RoundedCornerShape(16.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Attach details",
                            tint = ObsidianOnSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }

                    TextField(
                        value = chatInput,
                        onValueChange = onInputChange,
                        placeholder = { Text("Type a secure message...", color = ObsidianOnSurfaceVariant.copy(alpha = 0.5f)) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = ObsidianOnSurface,
                            unfocusedTextColor = ObsidianOnSurface,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = { onSendMessage() }),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chat_input_text")
                    )

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Image,
                            contentDescription = "Upload screenshot to scan",
                            tint = ObsidianOnSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = "Upload voice to scan",
                            tint = ObsidianOnSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }

                    // High contrast send action button
                    IconButton(
                        onClick = onSendMessage,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(CyberBlue)
                            .size(40.dp)
                            .testTag("chat_send_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Send message",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleShape = if (message.isUser) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 0.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp)
    }

    val bubbleBgColor = if (message.isUser) ObsidianSurfaceContainer else CyberBlue.copy(alpha = 0.05f)
    val borderBrush = if (message.isUser) {
        Brush.linearGradient(colors = listOf(Color.White.copy(alpha = 0.05f), Color.White.copy(alpha = 0.02f)))
    } else {
        Brush.linearGradient(colors = listOf(CyberBlue.copy(alpha = 0.3f), Color.Transparent))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(if (message.isUser) "user_message" else "ai_message"),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(bubbleShape)
                .background(bubbleBgColor)
                .border(1.dp, borderBrush, bubbleShape)
                .padding(14.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
                color = ObsidianOnSurface
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (message.isUser) "YOU" else "SCAM SHIELD AI",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 9.sp),
            color = ObsidianOnSurfaceVariant.copy(alpha = 0.4f),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun ThinkingIndicator() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp))
                .background(CyberBlue.copy(alpha = 0.03f))
                .border(1.dp, CyberBlue.copy(alpha = 0.1f), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp))
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CircularProgressIndicator(
                    color = CyberBlue,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Analyzing threat vector...",
                    style = MaterialTheme.typography.bodySmall,
                    color = ObsidianOnSurfaceVariant.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun PresetChip(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(99.dp))
            .background(Color(0x55101827))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(99.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = ObsidianOnSurfaceVariant
        )
    }
}
