package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Reusable animation state for kid-friendly bouncy tapping.
 * Drives scale, slight playful rotation, and vertical jump (translationY).
 */
class KidsBounceState(
    val scale: Animatable<Float, AnimationVector1D>,
    val rotation: Animatable<Float, AnimationVector1D>,
    val translationY: Animatable<Float, AnimationVector1D>
) {
    suspend fun triggerBounce(
        targetScaleDown: Float = 0.88f,
        targetScaleUp: Float = 1.16f,
        wobbleDegrees: Float = 6f,
        jumpOffsetPx: Float = -16f
    ) {
        coroutineScope {
            // Scale animation: press down, spring pop overshoot, then settle
            launch {
                scale.animateTo(targetScaleDown, tween(60, easing = FastOutSlowInEasing))
                scale.animateTo(
                    targetScaleUp,
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }

            // Playful rotation wobble
            if (wobbleDegrees != 0f) {
                launch {
                    rotation.animateTo(-wobbleDegrees, tween(60))
                    rotation.animateTo(
                        wobbleDegrees,
                        spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                    )
                    rotation.animateTo(0f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                }
            }

            // Playful vertical jump
            if (jumpOffsetPx != 0f) {
                launch {
                    translationY.animateTo(jumpOffsetPx, tween(70, easing = FastOutSlowInEasing))
                    translationY.animateTo(
                        0f,
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun rememberKidsBounceState(): KidsBounceState {
    val scale = remember { Animatable(1f) }
    val rotation = remember { Animatable(0f) }
    val translationY = remember { Animatable(0f) }
    return remember(scale, rotation, translationY) {
        KidsBounceState(scale, rotation, translationY)
    }
}

/**
 * Modifier extension to apply bouncy spring physics to any interactive Composable.
 */
fun Modifier.kidsBouncyClick(
    interactionSource: MutableInteractionSource? = null,
    wobbleDegrees: Float = 5f,
    jumpOffsetPx: Float = -12f,
    onClick: () -> Unit
): Modifier = composed {
    val bounceState = rememberKidsBounceState()
    val scope = rememberCoroutineScope()
    val source = interactionSource ?: remember { MutableInteractionSource() }

    this
        .graphicsLayer {
            scaleX = bounceState.scale.value
            scaleY = bounceState.scale.value
            rotationZ = bounceState.rotation.value
            translationY = bounceState.translationY.value
        }
        .clickable(
            interactionSource = source,
            indication = null
        ) {
            scope.launch {
                bounceState.triggerBounce(
                    wobbleDegrees = wobbleDegrees,
                    jumpOffsetPx = jumpOffsetPx
                )
            }
            onClick()
        }
}
