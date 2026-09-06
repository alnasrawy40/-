package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AlphabetRepository
import com.example.model.ArabicLetter
import com.example.model.HarakaInfo
import com.example.ui.components.HarakatEducationDialog
import com.example.ui.components.LetterCard
import com.example.ui.theme.KidsGoldenStar
import com.example.ui.theme.KidsNaturalBorder
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import kotlinx.coroutines.launch

@Composable
fun LettersGridScreen(
    selectedGroup: Int,
    masteredLetters: Set<Int>,
    totalStars: Int,
    isMuted: Boolean,
    onGroupSelected: (Int) -> Unit,
    onLetterClick: (ArabicLetter) -> Unit,
    onSpeakLetter: (ArabicLetter) -> Unit,
    onToggleMute: () -> Unit,
    onPlayHarakaLesson: (String) -> Unit = {},
    onPlayHarakaLetter: (ArabicLetter, HarakaInfo) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    var showHarakatEducationDialog by remember { mutableStateOf(false) }

    if (showHarakatEducationDialog) {
        HarakatEducationDialog(
            onDismiss = { showHarakatEducationDialog = false },
            onPlayHarakaLesson = onPlayHarakaLesson,
            onPlayHarakaLetter = onPlayHarakaLetter
        )
    }

    val allLetters = AlphabetRepository.letters
    val filteredLetters = if (selectedGroup == 0) {
        allLetters
    } else {
        allLetters.filter { it.group == selectedGroup }
    }

    val groups = listOf(
        0 to "كُلُّ الحُرُوف (28)",
        1 to "الوِحْدَة 1 (أ، ب، ت، ث)",
        2 to "الوِحْدَة 2 (ج، ح، خ، د)",
        3 to "الوِحْدَة 3 (ذ، ر، ز، س)",
        4 to "الوِحْدَة 4 (ش، ص، ض، ط)",
        5 to "الوِحْدَة 5 (ظ، ع، غ، ف)",
        6 to "الوِحْدَة 6 (ق، ك، ل، م)",
        7 to "الوِحْدَة 7 (ن، هـ، و، ي)"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .testTag("letters_grid_screen")
    ) {
        // Top App Bar & Mascot Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Banner image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_banner_kids),
                        contentDescription = "Kids Classroom Banner",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.45f))
                                )
                            )
                    )

                    // Sound mute icon overlay
                    IconButton(
                        onClick = onToggleMute,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .background(Color.White.copy(alpha = 0.85f), CircleShape)
                            .size(36.dp)
                            .testTag("mute_toggle_button")
                    ) {
                        Icon(
                            imageVector = if (isMuted) Icons.Filled.VolumeOff else Icons.Filled.VolumeUp,
                            contentDescription = "كتم الصوت",
                            tint = if (isMuted) Color.Red else KidsPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Welcome title
                    Text(
                        text = "مَرْحَبًا بِأَبْطَالِ الصَّفِّ الأَوَّل! 🎒",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                    )
                }

                // Progress Bar and Stars summary
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "الحُرُوفُ المُتْقَنَة:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF475569)
                            )
                            Text(
                                text = "${masteredLetters.size} / 28",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = KidsPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { (masteredLetters.size / 28f).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = KidsPrimary,
                            trackColor = Color(0xFFE2E8F0)
                        )
                    }

                    // Stars pill
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFEF3C7), RoundedCornerShape(16.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Stars",
                                tint = KidsGoldenStar,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "$totalStars",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFB45309)
                            )
                        }
                    }
                }
            }
        }

        // Harakat Sounds Teaching Quick Bar (تعليم أصوات الحركات)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, KidsNaturalBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "🎵", fontSize = 18.sp)
                        Text(
                            text = "أَصْوَاتُ الحَرَكَاتِ (فَتْحَة، ضَمَّة، كَسْرَة):",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1E293B)
                        )
                    }

                    // Button to open the full interactive lesson dialog
                    androidx.compose.material3.Button(
                        onClick = { showHarakatEducationDialog = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = KidsPrimary
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.testTag("open_harakat_dialog_button")
                    ) {
                        Text(
                            text = "تَعَلَّمْ النُّطْق 🎧",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val quickItems = listOf(
                        Triple("الْفَتْحَة (ـَ)", "أَ.. بَ", Color(0xFF0284C7)),
                        Triple("الضَّمَّة (ـُ)", "أُ.. بُ", Color(0xFFD97706)),
                        Triple("الْكَسْرَة (ـِ)", "إِ.. بِ", Color(0xFF16A34A)),
                        Triple("السُّكُون (ـْ)", "أْ.. بْ", Color(0xFF9333EA))
                    )
                    quickItems.forEach { (title, sample, color) ->
                        HomeQuickHarakaButton(
                            title = title,
                            sample = sample,
                            color = color,
                            onPlay = { onPlayHarakaLesson(title) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Learning Unit Filter Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(groups) { (id, label) ->
                val isSelected = selectedGroup == id
                FilterChip(
                    selected = isSelected,
                    onClick = { onGroupSelected(id) },
                    label = {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = KidsPrimary,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = Color(0xFF475569)
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.testTag("filter_chip_$id")
                )
            }
        }

        // Alphabet Grid
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 90.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredLetters, key = { it.id }) { letter ->
                LetterCard(
                    letter = letter,
                    isMastered = masteredLetters.contains(letter.id),
                    onCardClick = { onLetterClick(letter) },
                    onSpeakClick = { onSpeakLetter(letter) }
                )
            }
        }
    }
}

@Composable
private fun HomeQuickHarakaButton(
    title: String,
    sample: String,
    color: Color,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale = remember { Animatable(1f) }
    val scope = androidx.compose.runtime.rememberCoroutineScope()

    androidx.compose.material3.Surface(
        onClick = {
            scope.launch {
                scale.animateTo(0.86f, tween(60))
                scale.animateTo(1.12f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
            }
            onPlay()
        },
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.1f),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        modifier = modifier
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
            .testTag("quick_listen_haraka_${title}")
    ) {
        Column(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                color = color
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = sample,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF334155)
                )
                Icon(
                    imageVector = Icons.Filled.VolumeUp,
                    contentDescription = "استمع",
                    tint = color,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}
