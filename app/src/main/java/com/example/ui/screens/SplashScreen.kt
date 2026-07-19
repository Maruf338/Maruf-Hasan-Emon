package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.CyberBlue
import com.example.ui.theme.ObsidianOnSurfaceVariant
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "SplashTransition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "LogoScale"
    )

    LaunchedEffect(Unit) {
        delay(2500) // Delay to simulate neural scanning initialization
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Ambient Aura Glow behind shield
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(240.dp)
            ) {
                // Centered generated gorgeous cyber shield logo
                Image(
                    painter = painterResource(id = R.drawable.scam_shield_logo_1784426150794),
                    contentDescription = "Scam Shield AI Cyber Logo",
                    modifier = Modifier
                        .size(180.dp)
                        .scale(scale)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Branding Title
            Text(
                text = "Scam Shield AI",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Premium Slogan
            Text(
                text = "PROTECT BEFORE YOU TRUST",
                style = MaterialTheme.typography.labelLarge,
                color = CyberBlue,
                letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("splash_slogan")
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Simulated progress bar indicator
            LinearProgressIndicator(
                color = CyberBlue,
                trackColor = Color.White.copy(alpha = 0.1f),
                modifier = Modifier
                    .width(140.dp)
                    .height(2.dp)
                    .clip(RoundedCornerShape(999.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Utility status label
            Text(
                text = "Initializing Neural Defense...",
                style = MaterialTheme.typography.labelLarge,
                color = ObsidianOnSurfaceVariant.copy(alpha = 0.4f),
                textAlign = TextAlign.Center
            )
        }
    }
}
