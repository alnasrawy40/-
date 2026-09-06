package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ArabicLetter
import com.example.model.HarakaInfo
import com.example.ui.theme.KidsBackground
import com.example.ui.theme.KidsGoldenStar
import com.example.ui.theme.KidsNaturalBorder
import com.example.ui.theme.KidsNaturalCream
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsPrimaryContainer
import com.example.ui.theme.KidsSecondaryContainer
import com.example.ui.theme.KidsTertiary
import com.example.ui.theme.KidsTertiaryContainer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LetterDetailSheet(
    letter: ArabicLetter,
    isMastered: Boolean,
    activeTab: Int,
    onTabSelected: (Int) -> Unit,
    onSpeakLetter: () -> Unit,
    onSpeakHaraka: (HarakaInfo) -> Unit,
    onSpeakHarakaSound: (String) -> Unit = {},
    onPlayHarakaLesson: (String) -> Unit = {},
    onSpeakWord: (String) -> Unit,
    onMarkMastered: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val baseColor = Color(letter.colorHex)
    val tabTitles = listOf("الحَرَكَات", "أَشْكَال الحَرْف", "كَلِمَات مُصَوَّرَة", "اكْتُب الحَرْف")

    val headerLetterScale = remember { Animatable(1f) }
    val headerLetterRotation = remember { Animatable(0f) }
    val headerSpeakerScale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    suspend fun animateHeaderPop() {
        coroutineScope {
            launch {
                headerLetterScale.animateTo(1.24f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
                headerLetterScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
            launch {
                headerLetterRotation.animateTo(-8f, tween(60))
                headerLetterRotation.animateTo(8f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                headerLetterRotation.animateTo(0f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
        containerColor = KidsBackground,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .testTag("letter_detail_sheet")
        ) {
            // Header: Close, Letter Title & Speaker
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(BorderStroke(1.dp, KidsNaturalBorder), RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(38.dp)
                        .background(KidsNaturalCream, CircleShape)
                        .testTag("close_sheet_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "إغلاق",
                        tint = Color(0xFF475569)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            scope.launch { animateHeaderPop() }
                            onSpeakLetter()
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = letter.char,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                color = KidsPrimary,
                                modifier = Modifier.graphicsLayer {
                                    scaleX = headerLetterScale.value
                                    scaleY = headerLetterScale.value
                                    rotationZ = headerLetterRotation.value
                                }
                            )
                            Box(
                                modifier = Modifier
                                    .graphicsLayer {
                                        scaleX = headerLetterScale.value
                                        scaleY = headerLetterScale.value
                                    }
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(KidsPrimary.copy(alpha = 0.12f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "صَوْتُهُ: ${letter.soundPhonic}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = KidsPrimary
                                )
                            }
                        }
                        Text(
                            text = "اسْمُ الحَرْف: ${letter.name}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF64748B)
                        )
                    }

                    IconButton(
                        onClick = {
                            scope.launch {
                                launch {
                                    headerSpeakerScale.animateTo(0.80f, tween(50))
                                    headerSpeakerScale.animateTo(1.25f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                                    headerSpeakerScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                                }
                                animateHeaderPop()
                            }
                            onSpeakLetter()
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .graphicsLayer {
                                scaleX = headerSpeakerScale.value
                                scaleY = headerSpeakerScale.value
                            }
                            .background(KidsPrimary, CircleShape)
                            .testTag("sheet_speak_letter_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.VolumeUp,
                            contentDescription = "استمع لصوت الحرف",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                // Mastered Badge or Button
                if (isMastered) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(KidsTertiaryContainer, RoundedCornerShape(14.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "متقن",
                            tint = KidsGoldenStar,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "مُتْقَن",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309)
                        )
                    }
                } else {
                    Button(
                        onClick = onMarkMastered,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidsPrimary),
                        modifier = Modifier.testTag("mark_mastered_button")
                    ) {
                        Text("أتقنته! ⭐", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Tabs for 1st grade topics
            TabRow(
                selectedTabIndex = activeTab,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[activeTab]),
                        color = KidsPrimary,
                        height = 3.dp
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = activeTab == index,
                        onClick = { onTabSelected(index) },
                        text = {
                            Text(
                                text = title,
                                fontSize = 13.sp,
                                fontWeight = if (activeTab == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (activeTab == index) KidsPrimary else Color(0xFF64748B)
                            )
                        }
                    )
                }
            }

            // Tab Contents
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                when (activeTab) {
                    0 -> HarakatTabContent(
                        letter = letter,
                        baseColor = baseColor,
                        onSpeakHaraka = onSpeakHaraka,
                        onSpeakHarakaSound = onSpeakHarakaSound,
                        onPlayHarakaLesson = onPlayHarakaLesson
                    )
                    1 -> PositionsTabContent(
                        letter = letter,
                        baseColor = baseColor,
                        onSpeakWord = onSpeakWord
                    )
                    2 -> WordsTabContent(
                        letter = letter,
                        baseColor = baseColor,
                        onSpeakWord = onSpeakWord
                    )
                    3 -> TracingTabContent(
                        letter = letter,
                        baseColor = baseColor,
                        onCompleted = onMarkMastered
                    )
                }
            }
        }
    }
}

@Composable
fun HarakatTabContent(
    letter: ArabicLetter,
    baseColor: Color,
    onSpeakHaraka: (HarakaInfo) -> Unit,
    onSpeakHarakaSound: (String) -> Unit = {},
    onPlayHarakaLesson: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Quick Harakat interactive banner (تعليم أصوات الحركات)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
            border = BorderStroke(1.dp, KidsNaturalBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎵 تَعْلِيمُ أَصْوَاتِ الحَرَكَاتِ (اضْغَطْ لِسَمَاعِ دَرْسِ الحَرَكَة):",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF475569)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val quickHarakat = listOf(
                        Triple("الْفَتْحَة", "ـَ", Color(0xFFE0F2FE)), // Blue
                        Triple("الضَّمَّة", "ـُ", Color(0xFFFEF3C7)), // Yellow/Amber
                        Triple("الْكَسْرَة", "ـِ", Color(0xFFDCFCE7)), // Green
                        Triple("السُّكُون", "ـْ", Color(0xFFF3E8FF))  // Purple
                    )
                    quickHarakat.forEach { (name, symbol, chipBg) ->
                        QuickHarakaChip(
                            name = name,
                            symbol = symbol,
                            chipBg = chipBg,
                            onPlay = { onPlayHarakaLesson(name) }
                        )
                    }
                }
            }
        }

        Text(
            text = "أَصْوَاتُ حَرْفِ (${letter.char}) مَعَ الحَرَكَاتِ:",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B)
        )

        letter.harakat.forEach { haraka ->
            val mouthTip = when {
                haraka.name.contains("فتحة") -> "👄 افْتَحْ فَمَك"
                haraka.name.contains("ضمة") -> "😗 ضُمَّ شَفَتَيْك"
                haraka.name.contains("كسرة") -> "😁 اخْفِضْ فَكَّك"
                else -> "🤐 اسْكُنْ وَاثْبُت"
            }

            InteractiveHarakaCard(
                haraka = haraka,
                mouthTip = mouthTip,
                onSpeakHaraka = { onSpeakHaraka(haraka) },
                onPlayHarakaLesson = { onPlayHarakaLesson(haraka.name) }
            )
        }
    }
}

@Composable
private fun QuickHarakaChip(
    name: String,
    symbol: String,
    chipBg: Color,
    onPlay: () -> Unit
) {
    val scale = remember { Animatable(1f) }
    val symbolScale = remember { Animatable(1f) }
    val symbolOffsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Surface(
        onClick = {
            scope.launch {
                launch {
                    scale.animateTo(0.88f, tween(60))
                    scale.animateTo(1.14f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                    scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                }
                launch {
                    symbolScale.animateTo(1.30f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                    symbolScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                }
                launch {
                    symbolOffsetY.animateTo(-6f, tween(60))
                    symbolOffsetY.animateTo(0f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                }
            }
            onPlay()
        },
        shape = RoundedCornerShape(14.dp),
        color = chipBg,
        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.08f)),
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
            .testTag("quick_haraka_$name")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = symbol,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E293B),
                modifier = Modifier.graphicsLayer {
                    scaleX = symbolScale.value
                    scaleY = symbolScale.value
                    translationY = symbolOffsetY.value
                }
            )
            Text(
                text = name,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )
            Icon(
                imageVector = Icons.Filled.VolumeUp,
                contentDescription = name,
                tint = KidsPrimary,
                modifier = Modifier.size(13.dp)
            )
        }
    }
}

@Composable
private fun InteractiveHarakaCard(
    haraka: HarakaInfo,
    mouthTip: String,
    onSpeakHaraka: () -> Unit,
    onPlayHarakaLesson: () -> Unit
) {
    val cardScale = remember { Animatable(1f) }
    val glyphScale = remember { Animatable(1f) }
    val glyphOffsetY = remember { Animatable(0f) }
    val mouthScale = remember { Animatable(1f) }
    val speakerScale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    suspend fun animateHarakaTap() {
        coroutineScope {
            launch {
                cardScale.animateTo(0.92f, tween(60))
                cardScale.animateTo(1.04f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
                cardScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
            launch {
                glyphScale.animateTo(1.30f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
                glyphScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
            launch {
                glyphOffsetY.animateTo(-10f, tween(70))
                glyphOffsetY.animateTo(0f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
            }
            launch {
                mouthScale.animateTo(1.35f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                mouthScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
            launch {
                speakerScale.animateTo(0.80f, tween(50))
                speakerScale.animateTo(1.28f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                speakerScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
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
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                scope.launch { animateHarakaTap() }
                onSpeakHaraka()
            }
            .testTag("haraka_card_${haraka.name}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, KidsNaturalBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Right: Letter with Haraka (pops up and jumps when clicked!)
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .graphicsLayer {
                        scaleX = glyphScale.value
                        scaleY = glyphScale.value
                        translationY = glyphOffsetY.value
                    }
                    .background(KidsPrimaryContainer, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = haraka.letterWithHaraka,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = KidsPrimary
                )
            }

            // Middle: Haraka Name badge & Mouth tip & Word Example
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Surface(
                    onClick = {
                        scope.launch { animateHarakaTap() }
                        onPlayHarakaLesson()
                    },
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.testTag("haraka_badge_${haraka.name}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "${haraka.name} (${haraka.symbol}) • $mouthTip",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF475569)
                        )
                        Icon(
                            imageVector = Icons.Filled.VolumeUp,
                            contentDescription = haraka.name,
                            tint = Color(0xFF64748B),
                            modifier = Modifier
                                .size(12.dp)
                                .graphicsLayer {
                                    scaleX = mouthScale.value
                                    scaleY = mouthScale.value
                                }
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = haraka.emoji,
                        fontSize = 20.sp,
                        modifier = Modifier.graphicsLayer {
                            scaleX = mouthScale.value
                            scaleY = mouthScale.value
                        }
                    )
                    Text(
                        text = haraka.exampleWord,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                }
            }

            // Left: Audio button to play recording with MediaPlayer
            IconButton(
                onClick = {
                    scope.launch { animateHarakaTap() }
                    onSpeakHaraka()
                },
                modifier = Modifier
                    .size(42.dp)
                    .graphicsLayer {
                        scaleX = speakerScale.value
                        scaleY = speakerScale.value
                    }
                    .background(KidsPrimary, CircleShape)
                    .testTag("haraka_play_button_${haraka.name}")
            ) {
                Icon(
                    imageVector = Icons.Filled.VolumeUp,
                    contentDescription = "استمع لصوت الحرف بالحركة",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
fun PositionsTabContent(
    letter: ArabicLetter,
    baseColor: Color,
    onSpeakWord: (String) -> Unit
) {
    val pos = letter.positions
    val items = listOf(
        Triple("أَوَّلُ الكَلِمَة", pos.beginning, pos.beginningWord),
        Triple("وَسَطُ الكَلِمَة", pos.middle, pos.middleWord),
        Triple("آخِرُ الكَلِمَة", pos.end, pos.endWord),
        Triple("مُنْفَصِل", pos.isolated, letter.primaryWord)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "أَشْكَالُ حَرْفِ (${letter.char}) فِي مَوَاضِعِ الكَلِمَة:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B)
        )

        items.forEach { (label, shape, exampleWord) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .clickable { onSpeakWord(exampleWord) },
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.5.dp, KidsNaturalBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Shape Box
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .background(KidsPrimaryContainer, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = shape,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = KidsPrimary
                        )
                    }

                    // Label & Word
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = exampleWord,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                    }

                    // Audio
                    IconButton(
                        onClick = { onSpeakWord(exampleWord) },
                        modifier = Modifier
                            .size(38.dp)
                            .background(KidsPrimaryContainer, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.VolumeUp,
                            contentDescription = "استمع للكلمة",
                            tint = KidsPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WordsTabContent(
    letter: ArabicLetter,
    baseColor: Color,
    onSpeakWord: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "كَلِمَاتٌ تَبْدَأُ بِحَرْفِ (${letter.char}):",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B)
        )

        letter.words.forEach { wordCard ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { onSpeakWord(wordCard.word) }
                    .testTag("word_card_${wordCard.word}"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.5.dp, KidsNaturalBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = wordCard.emoji,
                        fontSize = 42.sp
                    )

                    Text(
                        text = wordCard.word,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )

                    IconButton(
                        onClick = { onSpeakWord(wordCard.word) },
                        modifier = Modifier
                            .size(44.dp)
                            .background(KidsPrimary, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.VolumeUp,
                            contentDescription = "استمع",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TracingTabContent(
    letter: ArabicLetter,
    baseColor: Color,
    onCompleted: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "تَدَرَّبْ عَلَى كِتَابَةِ حَرْفِ (${letter.char}) بَإِصْبَعِكَ!",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF334155),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        TracingCanvas(
            letter = letter,
            onCompleted = onCompleted
        )
    }
}
