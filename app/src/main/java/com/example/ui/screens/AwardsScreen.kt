package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AlphabetRepository
import com.example.ui.theme.KidsBackground
import com.example.ui.theme.KidsEmeraldProgress
import com.example.ui.theme.KidsGoldenStar
import com.example.ui.theme.KidsNaturalBorder
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsSecondaryContainer
import com.example.ui.theme.KidsSuccess
import com.example.ui.theme.KidsTertiaryContainer

data class BadgeItem(
    val title: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean
)

@Composable
fun AwardsScreen(
    masteredLetters: Set<Int>,
    totalStars: Int,
    modifier: Modifier = Modifier
) {
    val allLetters = AlphabetRepository.letters
    val percentage = (masteredLetters.size / 28f).coerceIn(0f, 1f)

    val badges = listOf(
        BadgeItem(
            title = "بِدَايَةُ الرِّحْلَة",
            description = "تَعَلَّمْتَ حَرْفَكَ الأَوَّل",
            icon = "🌱",
            isUnlocked = masteredLetters.isNotEmpty()
        ),
        BadgeItem(
            title = "صَائِدُ النُّجُوم",
            description = "جَمَعْتَ أَكْثَرَ مِنْ 20 نَجْمَة",
            icon = "⭐",
            isUnlocked = totalStars >= 20
        ),
        BadgeItem(
            title = "فَنَّانُ الخَطّ",
            description = "تَدَرَّبْتَ عَلَى كِتَابَةِ الحُرُوف",
            icon = "✏️",
            isUnlocked = masteredLetters.size >= 5
        ),
        BadgeItem(
            title = "بَطَلُ الصَّفِّ الأَوَّل",
            description = "أَتْقَنْتَ جَمِيعَ الحُرُوفِ الـ 28!",
            icon = "🏆",
            isUnlocked = masteredLetters.size == 28
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KidsBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 90.dp)
            .testTag("awards_screen")
    ) {
        // Hero Star Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(2.dp, KidsNaturalBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .background(KidsTertiaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.EmojiEvents,
                        contentDescription = "Trophy",
                        tint = KidsPrimary,
                        modifier = Modifier.size(46.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "لَوْحَةُ الشَّرَفِ وَالإِنْجَازَات 🌟",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Stars",
                        tint = KidsGoldenStar,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "$totalStars نَجْمَة ذَهَبِيَّة",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFB45309)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Progress Bar
                LinearProgressIndicator(
                    progress = { percentage },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = KidsEmeraldProgress,
                    trackColor = Color(0xFFE2E8F0)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "أَتْقَنْتَ ${masteredLetters.size} مِنْ أصل 28 حَرْفًا (${(percentage * 100).toInt()}%)",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Badges Section
        Text(
            text = "أَوْسِمَةُ الأَبْطَال:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B)
        )

        Spacer(modifier = Modifier.height(10.dp))

        badges.forEach { badge ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (badge.isUnlocked) Color.White else Color(0xFFF9FAF5)
                ),
                border = BorderStroke(
                    width = 1.5.dp,
                    color = if (badge.isUnlocked) KidsSecondaryContainer else KidsNaturalBorder
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (badge.isUnlocked) 1.5.dp else 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (badge.isUnlocked) badge.icon else "🔒",
                        fontSize = 32.sp
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = badge.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (badge.isUnlocked) Color(0xFF1E293B) else Color(0xFF94A3B8)
                        )
                        Text(
                            text = badge.description,
                            fontSize = 12.sp,
                            color = if (badge.isUnlocked) Color(0xFF64748B) else Color(0xFF94A3B8)
                        )
                    }

                    if (badge.isUnlocked) {
                        Text(
                            text = "مُكْتَمَل ✅",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = KidsPrimary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Mastered Letters Grid
        Text(
            text = "حُرُوفُكَ المُتْقَنَة (اضْغَطْ لِلتَّأَلُّق):",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(2.dp, KidsNaturalBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val chunks = allLetters.chunked(7)
                chunks.forEach { rowLetters ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowLetters.forEach { letter ->
                            val isMastered = masteredLetters.contains(letter.id)
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(
                                        if (isMastered) KidsSecondaryContainer else Color.White
                                    )
                                    .border(
                                        width = 1.5.dp,
                                        color = if (isMastered) KidsSecondary else KidsNaturalBorder,
                                        shape = RoundedCornerShape(14.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = letter.char,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isMastered) KidsPrimary else Color(0xFF94A3B8)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
