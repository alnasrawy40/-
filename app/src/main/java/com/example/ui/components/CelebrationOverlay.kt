package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.KidsGoldenStar
import com.example.ui.theme.KidsPrimary
import kotlin.random.Random

data class ConfettiParticle(
    val x: Float,
    val y: Float,
    val radius: Float,
    val color: Color,
    val speedY: Float
)

@Composable
fun CelebrationOverlay(
    visible: Boolean,
    message: String,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(300)) + scaleIn(tween(300)),
        exit = fadeOut(tween(200)) + scaleOut(tween(200))
    ) {
        val bounceScale = remember { Animatable(0.7f) }
        LaunchedEffect(visible) {
            if (visible) {
                bounceScale.animateTo(
                    targetValue = 1.1f,
                    animationSpec = tween(350, easing = FastOutSlowInEasing)
                )
                bounceScale.animateTo(
                    targetValue = 1.0f,
                    animationSpec = tween(200)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.55f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismiss
                )
                .testTag("celebration_overlay"),
            contentAlignment = Alignment.Center
        ) {
            // Animated confetti particles
            Canvas(modifier = Modifier.fillMaxSize()) {
                val colors = listOf(
                    Color(0xFFEF4444),
                    Color(0xFFF59E0B),
                    Color(0xFF10B981),
                    Color(0xFF3B82F6),
                    Color(0xFFEC4899),
                    Color(0xFF8B5CF6)
                )
                val rnd = Random(42)
                for (i in 0..60) {
                    val cx = rnd.nextFloat() * size.width
                    val cy = rnd.nextFloat() * size.height
                    val r = rnd.nextFloat() * 10f + 4f
                    val c = colors[i % colors.size]
                    drawCircle(color = c, radius = r, center = Offset(cx, cy))
                }
            }

            Card(
                modifier = Modifier
                    .padding(32.dp)
                    .scale(bounceScale.value)
                    .testTag("celebration_card"),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎉",
                        fontSize = 54.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star",
                        tint = KidsGoldenStar,
                        modifier = Modifier.size(56.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "ممتاز ورائع!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = KidsPrimary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = message,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF334155),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidsPrimary),
                        modifier = Modifier.testTag("celebration_continue_button")
                    ) {
                        Text(
                            text = "مُتَابَعَة 🚀",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
