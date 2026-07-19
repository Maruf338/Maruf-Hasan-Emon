package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CyberBlue
import com.example.ui.theme.EmeraldGreen

@Composable
fun RadarHUD(
    modifier: Modifier = Modifier,
    isScanning: Boolean = false,
    pulseColor: Color = CyberBlue,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "RadarAnimation")

    // Rotation angle for active radar sweep
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RadarAngle"
    )

    // Pulse size for ambient glow
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "RadarPulse"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Canvas drawing background radar concentric circles and active sweeps
        Canvas(modifier = Modifier.size(240.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2

            // Concentric background grid circles
            drawCircle(
                color = pulseColor.copy(alpha = 0.08f),
                radius = radius * 0.9f,
                style = Stroke(width = 1.dp.toPx())
            )
            drawCircle(
                color = pulseColor.copy(alpha = 0.15f),
                radius = radius * 0.6f,
                style = Stroke(width = 1.dp.toPx())
            )
            drawCircle(
                color = pulseColor.copy(alpha = 0.25f),
                radius = radius * 0.35f,
                style = Stroke(width = 1.5f.dp.toPx())
            )

            if (isScanning) {
                // Outer pulsing beacon halo
                drawCircle(
                    color = pulseColor.copy(alpha = 0.04f * (1.15f - pulseScale)),
                    radius = radius * pulseScale,
                    style = Stroke(width = 8.dp.toPx())
                )

                // Active rotating laser beam sweep
                rotate(degrees = angle, pivot = center) {
                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color.Transparent,
                                pulseColor.copy(alpha = 0.4f),
                                pulseColor.copy(alpha = 0.02f)
                            ),
                            center = center
                        ),
                        startAngle = 0f,
                        sweepAngle = 120f,
                        useCenter = true
                    )
                }
            } else {
                // Static protective safe-glow overlay ring
                drawCircle(
                    color = EmeraldGreen.copy(alpha = 0.05f * pulseScale),
                    radius = radius * pulseScale,
                    style = Stroke(width = 4.dp.toPx())
                )
            }
        }

        // Inner nested content (e.g. Shield image or circular text)
        content()
    }
}
