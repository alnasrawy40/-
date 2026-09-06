package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.AlphabetRepository
import com.example.model.ArabicLetter
import com.example.model.HarakaInfo
import com.example.ui.theme.KidsNaturalBorder
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

data class HarakaLesson(
    val name: String,
    val symbol: String,
    val mouthShapeEmoji: String,
    val ruleDescription: String,
    val phoneticSound: String,
    val soundSamples: String,
    val containerColor: Color,
    val accentColor: Color
)

@Composable
fun HarakatEducationDialog(
    onDismiss: () -> Unit,
    onPlayHarakaLesson: (String) -> Unit,
    onPlayHarakaLetter: (ArabicLetter, HarakaInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val lessons = remember {
        listOf(
            HarakaLesson(
                name = "الْفَتْحَة",
                symbol = "ـَ",
                mouthShapeEmoji = "👄",
                ruleDescription = "نَفْتَحُ الْفَمَ لِلْأَعْلَى عِنْدَ النُّطْق",
                phoneticSound = "صَوْتُهَا قَصِير: «ـَـَا»",
                soundSamples = "أَ.. بَ.. تَ.. ثَ",
                containerColor = Color(0xFFEFF6FF), // Sky blue tint
                accentColor = Color(0xFF0284C7)
            ),
            HarakaLesson(
                name = "الضَّمَّة",
                symbol = "ـُ",
                mouthShapeEmoji = "😗",
                ruleDescription = "نَضُمُّ الشَّفَتَيْنِ لِلْأَمَام كَدَائِرَة",
                phoneticSound = "صَوْتُهَا قَصِير: «ـُـُو»",
                soundSamples = "أُ.. بُ.. تُ.. ثُ",
                containerColor = Color(0xFFFFFBEB), // Amber tint
                accentColor = Color(0xFFD97706)
            ),
            HarakaLesson(
                name = "الْكَسْرَة",
                symbol = "ـِ",
                mouthShapeEmoji = "😁",
                ruleDescription = "نَبْتَسِمُ وَنَخْفِضُ الْفَكَّ لِلْأَسْفَل",
                phoneticSound = "صَوْتُهَا قَصِير: «ـِـِي»",
                soundSamples = "إِ.. بِ.. تِ.. ثِ",
                containerColor = Color(0xFFF0FDF4), // Green tint
                accentColor = Color(0xFF16A34A)
            ),
            HarakaLesson(
                name = "السُّكُون",
                symbol = "ـْ",
                mouthShapeEmoji = "🤐",
                ruleDescription = "ثَبَاتٌ وَوُقُوفٌ عَلَى صَوْتِ الْحَرْف دُونَ حَرَكَة",
                phoneticSound = "صَوْتٌ سَاكِنٌ خَفِيف",
                soundSamples = "أْ.. بْ.. تْ.. ثْ",
                containerColor = Color(0xFFFAF5FF), // Purple tint
                accentColor = Color(0xFF9333EA)
            )
        )
    }

    // Sample letters for the interactive Harakat laboratory
    val practiceLetters = remember {
        listOf(
            AlphabetRepository.letters.find { it.id == 2 } ?: AlphabetRepository.letters[1], // ب
            AlphabetRepository.letters.find { it.id == 1 } ?: AlphabetRepository.letters[0], // أ
            AlphabetRepository.letters.find { it.id == 3 } ?: AlphabetRepository.letters[2], // ت
            AlphabetRepository.letters.find { it.id == 24 } ?: AlphabetRepository.letters[23], // م
            AlphabetRepository.letters.find { it.id == 10 } ?: AlphabetRepository.letters[9], // ر
            AlphabetRepository.letters.find { it.id == 12 } ?: AlphabetRepository.letters[11] // س
        )
    }

    var selectedPracticeIndex by remember { mutableIntStateOf(0) }
    val currentLetter = practiceLetters.getOrNull(selectedPracticeIndex) ?: practiceLetters[0]

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.92f)
                .testTag("harakat_education_dialog"),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF38BDF8), Color(0xFF6366F1))
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.CenterStart),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "🎵 تَعْلِيمُ أَصْوَاتِ الْحَرَكَات",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = "تَعَلَّمْ أَصْوَاتَ الفَتْحَةِ والضَّمَّةِ والكَسْرَةِ مع نُطْقِ الْحُرُوف",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .background(Color.White.copy(alpha = 0.25f), CircleShape)
                            .size(34.dp)
                            .testTag("harakat_dialog_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "إغلاق",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 14.dp),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Educational introduction banner
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                            border = BorderStroke(1.dp, KidsNaturalBorder)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = "💡",
                                    fontSize = 26.sp
                                )
                                Text(
                                    text = "فِي اللُّغَةِ العَرَبِيَّة، لا نَنْطِقُ اسْمَ الحَرْفِ فَقَط، بَلْ نَنْطِقُ صَوْتَهُ حَسَبَ الحَرَكَة: الفَتْحَة (ـَ)، الضَّمَّة (ـُ)، الكَسْرَة (ـِ).",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF334155),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }

                    // 4 Lessons Cards
                    items(lessons) { lesson ->
                        InteractiveLessonCard(
                            lesson = lesson,
                            onPlayLesson = { onPlayHarakaLesson(lesson.name) }
                        )
                    }

                    // Interactive Laboratory Section: Pick a letter and hear all 3 harakat sounds
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("harakat_practice_lab"),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                            border = BorderStroke(1.5.dp, Color(0xFFCBD5E1))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(text = "🔬", fontSize = 20.sp)
                                    Text(
                                        text = "مُخْتَبَرُ أَصْوَاتِ الحُرُوف (جَرِّبْ بِنَفْسِك):",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF1E293B)
                                    )
                                }

                                Text(
                                    text = "اخْتَرْ حَرْفًا وَاضْغَطْ عَلَى الحَرَكَة لِتَسْمَعَ صَوْتَهُ الصَّحِيح:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF64748B)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // Letter selection chips
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(practiceLetters.size) { idx ->
                                        val let = practiceLetters[idx]
                                        val isSelected = selectedPracticeIndex == idx
                                        PracticeLetterChip(
                                            letter = let,
                                            isSelected = isSelected,
                                            onSelect = { selectedPracticeIndex = idx }
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // The 3 Harakat buttons for current letter
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    currentLetter.harakat.forEach { haraka ->
                                        val isFatha = haraka.name.contains("فتحة")
                                        val isDamma = haraka.name.contains("ضمة")
                                        val isKasra = haraka.name.contains("كسرة")
                                        val chipColor = when {
                                            isFatha -> Color(0xFF0284C7)
                                            isDamma -> Color(0xFFD97706)
                                            isKasra -> Color(0xFF16A34A)
                                            else -> Color(0xFF9333EA)
                                        }

                                        InteractivePracticeHarakaCard(
                                            haraka = haraka,
                                            chipColor = chipColor,
                                            onPlay = { onPlayHarakaLetter(currentLetter, haraka) },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InteractiveLessonCard(
    lesson: HarakaLesson,
    onPlayLesson: () -> Unit
) {
    val cardScale = remember { Animatable(1f) }
    val symbolScale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    suspend fun animateLessonTap() {
        coroutineScope {
            launch {
                cardScale.animateTo(0.93f, tween(60))
                cardScale.animateTo(1.03f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                cardScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
            launch {
                symbolScale.animateTo(1.25f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                symbolScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = cardScale.value
                scaleY = cardScale.value
            }
            .clickable {
                scope.launch { animateLessonTap() }
                onPlayLesson()
            }
            .testTag("haraka_lesson_card_${lesson.name}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = lesson.containerColor),
        border = BorderStroke(1.5.dp, lesson.accentColor.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Symbol badge
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .graphicsLayer {
                                scaleX = symbolScale.value
                                scaleY = symbolScale.value
                            }
                            .background(lesson.accentColor, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = lesson.symbol,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = lesson.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = lesson.accentColor
                            )
                            Text(
                                text = lesson.mouthShapeEmoji,
                                fontSize = 20.sp
                            )
                        }
                        Text(
                            text = lesson.phoneticSound,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF475569)
                        )
                    }
                }

                // Button to play explanation sound
                Button(
                    onClick = {
                        scope.launch { animateLessonTap() }
                        onPlayLesson()
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = lesson.accentColor),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("play_lesson_${lesson.name}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.VolumeUp,
                        contentDescription = "استمع للدرس",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "اسْتَمِعْ لِلصَّوْت",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rule description & samples
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "طَرِيقَةُ النُّطْق: ${lesson.ruleDescription}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF334155),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = lesson.soundSamples,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = lesson.accentColor
                )
            }
        }
    }
}

@Composable
private fun PracticeLetterChip(
    letter: ArabicLetter,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Surface(
        onClick = {
            scope.launch {
                scale.animateTo(0.88f, tween(50))
                scale.animateTo(1.12f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
            onSelect()
        },
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) KidsPrimary else Color.White,
        border = BorderStroke(
            1.dp,
            if (isSelected) KidsPrimary else Color(0xFFE2E8F0)
        ),
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
            .testTag("practice_letter_${letter.char}")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = letter.char,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = if (isSelected) Color.White else Color(0xFF1E293B)
            )
            Text(
                text = letter.primaryEmoji,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun InteractivePracticeHarakaCard(
    haraka: HarakaInfo,
    chipColor: Color,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardScale = remember { Animatable(1f) }
    val glyphScale = remember { Animatable(1f) }
    val glyphOffsetY = remember { Animatable(0f) }
    val speakerScale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .graphicsLayer {
                scaleX = cardScale.value
                scaleY = cardScale.value
            }
            .clickable {
                scope.launch {
                    coroutineScope {
                        launch {
                            cardScale.animateTo(0.88f, tween(50))
                            cardScale.animateTo(1.08f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                            cardScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                        }
                        launch {
                            glyphScale.animateTo(1.30f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                            glyphScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                        }
                        launch {
                            glyphOffsetY.animateTo(-8f, tween(60))
                            glyphOffsetY.animateTo(0f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                        }
                        launch {
                            speakerScale.animateTo(1.30f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                            speakerScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                        }
                    }
                }
                onPlay()
            }
            .testTag("practice_haraka_${haraka.name}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = chipColor.copy(alpha = 0.12f)),
        border = BorderStroke(1.dp, chipColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = haraka.letterWithHaraka,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = chipColor,
                modifier = Modifier.graphicsLayer {
                    scaleX = glyphScale.value
                    scaleY = glyphScale.value
                    translationY = glyphOffsetY.value
                }
            )
            Text(
                text = haraka.name,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF475569)
            )
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .graphicsLayer {
                        scaleX = speakerScale.value
                        scaleY = speakerScale.value
                    }
                    .background(chipColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.VolumeUp,
                    contentDescription = "استمع",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
