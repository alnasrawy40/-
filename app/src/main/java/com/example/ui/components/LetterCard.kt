package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ArabicLetter
import com.example.ui.theme.KidsGoldenStar
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun LetterCard(
    letter: ArabicLetter,
    isMastered: Boolean,
    onCardClick: () -> Unit,
    onSpeakClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardScale = remember { Animatable(1f) }
    val letterScale = remember { Animatable(1f) }
    val letterRotation = remember { Animatable(0f) }
    val letterOffsetY = remember { Animatable(0f) }
    val speakerScale = remember { Animatable(1f) }

    val scope = rememberCoroutineScope()
    val baseColor = Color(letter.colorHex)
    val lightBg = baseColor.copy(alpha = 0.12f)

    suspend fun animateLetterPop() {
        coroutineScope {
            launch {
                letterScale.animateTo(1.22f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
                letterScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
            launch {
                letterRotation.animateTo(-7f, tween(60))
                letterRotation.animateTo(7f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                letterRotation.animateTo(0f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
            launch {
                letterOffsetY.animateTo(-10f, tween(70))
                letterOffsetY.animateTo(0f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
            }
        }
    }

    Card(
        modifier = modifier
            .graphicsLayer {
                scaleX = cardScale.value
                scaleY = cardScale.value
            }
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                scope.launch {
                    launch {
                        cardScale.animateTo(0.90f, tween(70))
                        cardScale.animateTo(1.05f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
                        cardScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                    }
                    animateLetterPop()
                }
                onCardClick()
            }
            .testTag("letter_card_${letter.id}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = lightBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top row: Star status and Pronunciation button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isMastered) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(KidsGoldenStar, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "متقن",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else {
                    Box(modifier = Modifier.size(28.dp))
                }

                IconButton(
                    onClick = {
                        scope.launch {
                            launch {
                                speakerScale.animateTo(0.80f, tween(60))
                                speakerScale.animateTo(1.25f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                                speakerScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                            }
                            animateLetterPop()
                        }
                        onSpeakClick()
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .graphicsLayer {
                            scaleX = speakerScale.value
                            scaleY = speakerScale.value
                        }
                        .background(baseColor.copy(alpha = 0.22f), CircleShape)
                        .testTag("speak_button_${letter.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.VolumeUp,
                        contentDescription = "استمع للحرف",
                        tint = baseColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Big Arabic Letter Glyph with dynamic Spring Bounce & Wobble
            Text(
                text = letter.char,
                fontSize = 44.sp,
                fontWeight = FontWeight.Black,
                color = baseColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = letterScale.value
                        scaleY = letterScale.value
                        rotationZ = letterRotation.value
                        translationY = letterOffsetY.value
                    }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        scope.launch {
                            animateLetterPop()
                        }
                        onSpeakClick()
                    }
            )

            // Letter Sound (صوت الحرف: بَ) with bounce on tap
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = letterScale.value
                        scaleY = letterScale.value
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(baseColor.copy(alpha = 0.18f))
                    .clickable {
                        scope.launch {
                            animateLetterPop()
                        }
                        onSpeakClick()
                    }
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "صَوْتُ: ${letter.soundPhonic}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = baseColor,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Example Word & Emoji Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = letter.primaryEmoji,
                        fontSize = 18.sp
                    )
                    Text(
                        text = letter.primaryWord,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E293B)
                    )
                }
            }
        }
    }
}
