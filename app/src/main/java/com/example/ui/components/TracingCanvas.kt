package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ArabicLetter
import com.example.ui.theme.KidsSuccess

data class LineStroke(
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Float = 24f
)

@Composable
fun TracingCanvas(
    letter: ArabicLetter,
    onCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    val strokes = remember { mutableStateListOf<LineStroke>() }
    var currentPoints by remember { mutableStateOf(listOf<Offset>()) }

    val crayonColors = listOf(
        Color(0xFFEF4444), // Red
        Color(0xFF3B82F6), // Blue
        Color(0xFF10B981), // Green
        Color(0xFFF59E0B), // Amber
        Color(0xFF8B5CF6), // Purple
        Color(0xFFEC4899)  // Pink
    )
    var selectedColor by remember { mutableStateOf(crayonColors[1]) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tracing Board Canvas (School Slate Board style)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF1E293B)) // Slate Dark Blackboard
                .border(4.dp, Color(0xFF94A3B8), RoundedCornerShape(24.dp))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentPoints = listOf(offset)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            currentPoints = currentPoints + change.position
                        },
                        onDragEnd = {
                            if (currentPoints.isNotEmpty()) {
                                strokes.add(LineStroke(currentPoints, selectedColor))
                                currentPoints = emptyList()
                            }
                        }
                    )
                }
                .testTag("tracing_slate_canvas"),
            contentAlignment = Alignment.Center
        ) {
            // Subtle Arabic Ruled Line Guide
            Canvas(modifier = Modifier.fillMaxSize()) {
                val midY = size.height * 0.62f
                drawLine(
                    color = Color.White.copy(alpha = 0.25f),
                    start = Offset(20f, midY),
                    end = Offset(size.width - 20f, midY),
                    strokeWidth = 3f
                )

                // Background Letter Guide (translucent template for child to trace over)
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.argb(80, 255, 255, 255)
                    textSize = size.height * 0.65f
                    textAlign = android.graphics.Paint.Align.CENTER
                    isFakeBoldText = true
                }
                drawContext.canvas.nativeCanvas.drawText(
                    letter.char,
                    size.width / 2f,
                    size.height * 0.72f,
                    paint
                )

                // Drawn strokes
                for (stroke in strokes) {
                    if (stroke.points.size > 1) {
                        val path = Path()
                        path.moveTo(stroke.points.first().x, stroke.points.first().y)
                        for (i in 1 until stroke.points.size) {
                            path.lineTo(stroke.points[i].x, stroke.points[i].y)
                        }
                        drawPath(
                            path = path,
                            color = stroke.color,
                            style = Stroke(
                                width = stroke.strokeWidth,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }

                // Current dragging stroke
                if (currentPoints.size > 1) {
                    val path = Path()
                    path.moveTo(currentPoints.first().x, currentPoints.first().y)
                    for (i in 1 until currentPoints.size) {
                        path.lineTo(currentPoints[i].x, currentPoints[i].y)
                    }
                    drawPath(
                        path = path,
                        color = selectedColor,
                        style = Stroke(
                            width = 24f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Crayon Palette & Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Colors
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                crayonColors.forEach { color ->
                    val isSelected = selectedColor == color
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (isSelected) 3.dp else 0.dp,
                                color = if (isSelected) Color(0xFF0F172A) else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { selectedColor = color }
                            .testTag("crayon_color_${color.value}")
                    )
                }
            }

            // Clear Button
            IconButton(
                onClick = {
                    strokes.clear()
                    currentPoints = emptyList()
                },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFE2E8F0), CircleShape)
                    .testTag("clear_tracing_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "مسح اللوحة",
                    tint = Color(0xFF475569)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Completion / Validation Button
        Button(
            onClick = {
                onCompleted()
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = KidsSuccess),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("complete_tracing_button")
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "أحسنت",
                tint = Color.White
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "كَتَبْتُ الحَرْفَ! أَحْسَنْتُ ⭐",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
