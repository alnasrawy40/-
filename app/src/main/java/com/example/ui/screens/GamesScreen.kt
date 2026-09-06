package com.example.ui.screens

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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
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
import com.example.ui.theme.KidsNaturalLavender
import com.example.ui.theme.KidsNaturalLavenderText
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsPrimaryContainer
import com.example.ui.theme.KidsSecondary
import com.example.ui.theme.KidsSecondaryContainer
import com.example.ui.theme.KidsSuccess
import com.example.ui.theme.KidsTertiaryContainer
import com.example.viewmodel.BalloonItem
import com.example.viewmodel.GameMode
import com.example.viewmodel.GuessQuestion
import com.example.viewmodel.HarakatQuestion

@Composable
fun GamesScreen(
    currentMode: GameMode,
    guessQuestion: GuessQuestion?,
    guessFeedback: Boolean?,
    balloonTarget: ArabicLetter?,
    balloons: List<BalloonItem>,
    balloonScore: Int,
    harakatQuestion: HarakatQuestion?,
    harakatFeedback: Boolean?,
    totalStars: Int,
    onSelectMode: (GameMode) -> Unit,
    onAnswerGuess: (ArabicLetter) -> Unit,
    onPopBalloon: (BalloonItem) -> Unit,
    onAnswerHaraka: (HarakaInfo) -> Unit,
    onSpeak: (String) -> Unit,
    onPlayHaraka: ((ArabicLetter, HarakaInfo) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val modes = listOf(
        GameMode.GUESS_LETTER to "خَمِّنِ الحَرْف 🎯",
        GameMode.BALLOON_POP to "صَائِدُ البَالُونَات 🎈",
        GameMode.HARAKAT_QUIZ to "أَصْوَاتُ الحَرَكَات 🎶"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KidsBackground)
            .testTag("games_screen")
    ) {
        // Mode Selector Tab
        TabRow(
            selectedTabIndex = modes.indexOfFirst { it.first == currentMode }.coerceAtLeast(0),
            containerColor = Color.White,
            indicator = { tabPositions ->
                val index = modes.indexOfFirst { it.first == currentMode }.coerceAtLeast(0)
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[index]),
                    color = KidsPrimary,
                    height = 3.dp
                )
            }
        ) {
            modes.forEach { (mode, title) ->
                val isSelected = currentMode == mode
                Tab(
                    selected = isSelected,
                    onClick = { onSelectMode(mode) },
                    text = {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) KidsPrimary else Color(0xFF64748B)
                        )
                    },
                    modifier = Modifier.testTag("tab_${mode.name}")
                )
            }
        }

        // Active Game Content
        when (currentMode) {
            GameMode.GUESS_LETTER -> GuessLetterGame(
                question = guessQuestion,
                feedback = guessFeedback,
                totalStars = totalStars,
                onAnswer = onAnswerGuess,
                onSpeak = onSpeak
            )
            GameMode.BALLOON_POP -> BalloonPopGame(
                target = balloonTarget,
                balloons = balloons,
                score = balloonScore,
                totalStars = totalStars,
                onPop = onPopBalloon,
                onSpeak = onSpeak
            )
            GameMode.HARAKAT_QUIZ -> HarakatQuizGame(
                question = harakatQuestion,
                feedback = harakatFeedback,
                totalStars = totalStars,
                onAnswer = onAnswerHaraka,
                onSpeak = onSpeak,
                onPlayHaraka = onPlayHaraka
            )
        }
    }
}

// ---------------- GAME 1: GUESS THE LETTER ----------------
@Composable
fun GuessLetterGame(
    question: GuessQuestion?,
    feedback: Boolean?,
    totalStars: Int,
    onAnswer: (ArabicLetter) -> Unit,
    onSpeak: (String) -> Unit
) {
    if (question == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Instruction Card
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
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "مَا هُوَ الحَرْفُ الأَوَّل؟",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )

                    IconButton(
                        onClick = { onSpeak("ما هو حرف كلمة ${question.word}؟") },
                        modifier = Modifier
                            .size(40.dp)
                            .background(KidsPrimaryContainer, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.VolumeUp,
                            contentDescription = "استمع",
                            tint = KidsPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Big Picture / Emoji
                Text(
                    text = question.emoji,
                    fontSize = 72.sp,
                    modifier = Modifier.padding(8.dp)
                )

                // Word with missing first letter or highlighted letter
                Text(
                    text = question.word,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF1E293B)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Feedback banner if answered
        AnimatedVisibility(visible = feedback != null) {
            val isCorrect = feedback == true
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCorrect) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                ),
                border = BorderStroke(1.dp, if (isCorrect) KidsSecondary else Color(0xFFFCA5A5))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (isCorrect) Icons.Filled.CheckCircle else Icons.Filled.Close,
                        contentDescription = null,
                        tint = if (isCorrect) KidsPrimary else Color.Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isCorrect) "أَحْسَنْتَ يَا بَطَل! إِجَابَةٌ صَحِيحَة ⭐" else "حَاوِلْ مَرَّةً أُخْرَى!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCorrect) Color(0xFF166534) else Color(0xFF991B1B)
                    )
                }
            }
        }

        Text(
            text = "اخْتَرِ الحَرْفَ الصَّحِيح:",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(vertical = 6.dp)
        )

        // Options Grid (4 Large Bouncy Cards)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            question.options.take(2).forEach { option ->
                LetterOptionCard(
                    letter = option,
                    modifier = Modifier.weight(1f),
                    onSelect = { onAnswer(option) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            question.options.drop(2).take(2).forEach { option ->
                LetterOptionCard(
                    letter = option,
                    modifier = Modifier.weight(1f),
                    onSelect = { onAnswer(option) }
                )
            }
        }
    }
}

@Composable
fun LetterOptionCard(
    letter: ArabicLetter,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val baseColor = Color(letter.colorHex)
    Card(
        modifier = modifier
            .height(110.dp)
            .clip(RoundedCornerShape(26.dp))
            .clickable { onSelect() }
            .testTag("option_letter_${letter.id}"),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, KidsNaturalBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(KidsNaturalCream.copy(alpha = 0.4f))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = letter.char,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                color = baseColor
            )
            Text(
                text = "صَوْتُهُ: ${letter.soundPhonic}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )
        }
    }
}

// ---------------- GAME 2: BALLOON POP ----------------
@Composable
fun BalloonPopGame(
    target: ArabicLetter?,
    balloons: List<BalloonItem>,
    score: Int,
    totalStars: Int,
    onPop: (BalloonItem) -> Unit,
    onSpeak: (String) -> Unit
) {
    if (target == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 70.dp)
    ) {
        // Goal header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(2.dp, KidsNaturalBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "فَرْقِعْ (${target.char}) • صَوْتُهُ: ${target.soundPhonic}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color(target.colorHex), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = target.char,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                }

                IconButton(
                    onClick = { onSpeak("فرقع حرف ${target.char} بصوت ${target.soundPhonic}") },
                    modifier = Modifier
                        .size(38.dp)
                        .background(KidsPrimaryContainer, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.VolumeUp,
                        contentDescription = "استمع",
                        tint = KidsPrimary
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(KidsTertiaryContainer, RoundedCornerShape(14.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(text = "🎈 $score", fontSize = 16.sp, fontWeight = FontWeight.Black, color = KidsPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Balloon Sky Area
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFBAE6FD), Color(0xFFF0F9FF))
                    )
                )
                .testTag("balloon_sky_canvas")
        ) {
            val totalWidth = maxWidth
            val totalHeight = maxHeight

            // Render floating balloons
            balloons.forEachIndexed { index, balloon ->
                if (!balloon.isPopped) {
                    // Floating animation with stagger
                    val floatAnim = remember { Animatable(0f) }
                    LaunchedEffect(balloon.id) {
                        floatAnim.animateTo(
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1800 + index * 200, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )
                    }

                    // Vertical and horizontal positions
                    val yPos = (index % 3) * (totalHeight.value / 3.4f) + 20f + (floatAnim.value * 16f)
                    val xPos = (balloon.offsetXPercent * totalWidth.value).coerceIn(10f, totalWidth.value - 85f)

                    Box(
                        modifier = Modifier
                            .offset(x = xPos.dp, y = yPos.dp)
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(Color(balloon.colorHex))
                            .clickable { onPop(balloon) }
                            .testTag("balloon_${balloon.id}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = balloon.char,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

// ---------------- GAME 3: HARAKAT QUIZ ----------------
@Composable
fun HarakatQuizGame(
    question: HarakatQuestion?,
    feedback: Boolean?,
    totalStars: Int,
    onAnswer: (HarakaInfo) -> Unit,
    onSpeak: (String) -> Unit,
    onPlayHaraka: ((ArabicLetter, HarakaInfo) -> Unit)? = null
) {
    if (question == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "اسْتَمِعْ لِلصَّوْتِ وَاخْتَرِ الحَرَكَةَ الصَّحِيحَة! 🎵",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Speaker Action Center
                Button(
                    onClick = {
                        if (onPlayHaraka != null) {
                            onPlayHaraka(question.letter, question.targetHaraka)
                        } else {
                            onSpeak(question.targetHaraka.letterWithHaraka)
                        }
                    },
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KidsPrimary),
                    modifier = Modifier.testTag("harakat_listen_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.VolumeUp,
                        contentDescription = "استمع للصوت",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "اضْغَطْ هُنَا لِتَسْتَمِعَ لِلصَّوْت (MediaPlayer)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "الحَرْفُ المَطْلُوب: حَرْفُ (${question.letter.name})",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Feedback Banner
        AnimatedVisibility(visible = feedback != null) {
            val isCorrect = feedback == true
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCorrect) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                ),
                border = BorderStroke(1.dp, if (isCorrect) KidsSecondary else Color(0xFFFCA5A5))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (isCorrect) Icons.Filled.CheckCircle else Icons.Filled.Close,
                        contentDescription = null,
                        tint = if (isCorrect) KidsPrimary else Color.Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isCorrect) "صَحِيحٌ جِدًّا! أَحْسَنْتَ ⭐" else "اسْتَمِعْ جَيِّدًا وَحَاوِلْ مَرَّةً أُخْرَى!",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCorrect) Color(0xFF166534) else Color(0xFF991B1B)
                    )
                }
            }
        }

        // Harakat Options (4 cards)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            question.options.forEach { haraka ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .clickable { onAnswer(haraka) }
                        .testTag("haraka_quiz_opt_${haraka.name}"),
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
                        Text(
                            text = haraka.letterWithHaraka,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = KidsPrimary
                        )

                        Text(
                            text = haraka.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )

                        Text(
                            text = "${haraka.emoji} ${haraka.exampleWord}",
                            fontSize = 15.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }
        }
    }
}
