package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AlphabetRepository
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.LetterDetailSheet
import com.example.ui.screens.AwardsScreen
import com.example.ui.screens.GamesScreen
import com.example.ui.screens.LettersGridScreen
import com.example.ui.screens.TracingScreen
import com.example.ui.theme.ArabicAlphabetTheme
import com.example.ui.theme.KidsPrimary
import com.example.viewmodel.AlphabetViewModel
import com.example.viewmodel.AppTab

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArabicAlphabetTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    ArabicAlphabetApp()
                }
            }
        }
    }
}

@Composable
fun ArabicAlphabetApp(viewModel: AlphabetViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    var sheetOpenLetterId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("app_scaffold"),
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .navigationBarsPadding()
                    .testTag("bottom_navigation_bar"),
                containerColor = Color.White,
                contentColor = KidsPrimary
            ) {
                NavigationBarItem(
                    selected = state.selectedTab == AppTab.LETTERS,
                    onClick = { viewModel.selectTab(AppTab.LETTERS) },
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = "الحروف"
                        )
                    },
                    label = {
                        Text(
                            text = "الحُرُوف",
                            fontWeight = if (state.selectedTab == AppTab.LETTERS) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = KidsPrimary,
                        selectedTextColor = KidsPrimary,
                        indicatorColor = KidsPrimary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_tab_letters")
                )

                NavigationBarItem(
                    selected = state.selectedTab == AppTab.TRACING,
                    onClick = { viewModel.selectTab(AppTab.TRACING) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "الكتابة"
                        )
                    },
                    label = {
                        Text(
                            text = "الكِتَابَة",
                            fontWeight = if (state.selectedTab == AppTab.TRACING) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = KidsPrimary,
                        selectedTextColor = KidsPrimary,
                        indicatorColor = KidsPrimary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_tab_tracing")
                )

                NavigationBarItem(
                    selected = state.selectedTab == AppTab.GAMES,
                    onClick = { viewModel.selectTab(AppTab.GAMES) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.SportsEsports,
                            contentDescription = "الألعاب"
                        )
                    },
                    label = {
                        Text(
                            text = "الأَلْعَاب",
                            fontWeight = if (state.selectedTab == AppTab.GAMES) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = KidsPrimary,
                        selectedTextColor = KidsPrimary,
                        indicatorColor = KidsPrimary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_tab_games")
                )

                NavigationBarItem(
                    selected = state.selectedTab == AppTab.AWARDS,
                    onClick = { viewModel.selectTab(AppTab.AWARDS) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.EmojiEvents,
                            contentDescription = "الإنجازات"
                        )
                    },
                    label = {
                        Text(
                            text = "الإِنْجَازَات",
                            fontWeight = if (state.selectedTab == AppTab.AWARDS) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = KidsPrimary,
                        selectedTextColor = KidsPrimary,
                        indicatorColor = KidsPrimary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_tab_awards")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state.selectedTab) {
                AppTab.LETTERS -> {
                    LettersGridScreen(
                        selectedGroup = state.selectedGroup,
                        masteredLetters = state.masteredLetters,
                        totalStars = state.totalStars,
                        isMuted = state.isMuted,
                        onGroupSelected = { viewModel.setGroup(it) },
                        onLetterClick = { letter ->
                            sheetOpenLetterId = letter.id
                            viewModel.selectLetter(letter)
                        },
                        onSpeakLetter = { letter ->
                            viewModel.speakLetter(letter)
                        },
                        onToggleMute = { viewModel.toggleMute() },
                        onPlayHarakaLesson = { harakaName ->
                            viewModel.speakHarakaLesson(harakaName)
                        },
                        onPlayHarakaLetter = { letter, haraka ->
                            viewModel.speakHaraka(letter, haraka)
                        }
                    )
                }

                AppTab.TRACING -> {
                    val activeLetter = state.selectedLetter ?: AlphabetRepository.letters.first()
                    TracingScreen(
                        currentLetter = activeLetter,
                        onLetterSelected = { viewModel.selectLetter(it) },
                        onSpeakLetter = { viewModel.speakLetter(it) },
                        onCompleted = { letterId ->
                            viewModel.markLetterMastered(letterId)
                        }
                    )
                }

                AppTab.GAMES -> {
                    GamesScreen(
                        currentMode = state.currentGameMode,
                        guessQuestion = state.guessQuestion,
                        guessFeedback = state.guessFeedback,
                        balloonTarget = state.balloonTarget,
                        balloons = state.balloons,
                        balloonScore = state.balloonScore,
                        harakatQuestion = state.harakatQuestion,
                        harakatFeedback = state.harakatFeedback,
                        totalStars = state.totalStars,
                        onSelectMode = { viewModel.selectGameMode(it) },
                        onAnswerGuess = { viewModel.answerGuess(it) },
                        onPopBalloon = { viewModel.popBalloon(it) },
                        onAnswerHaraka = { viewModel.answerHaraka(it) },
                        onSpeak = { text -> viewModel.speakWord(text) },
                        onPlayHaraka = { letter, haraka -> viewModel.speakHaraka(letter, haraka) }
                    )
                }

                AppTab.AWARDS -> {
                    AwardsScreen(
                        masteredLetters = state.masteredLetters,
                        totalStars = state.totalStars
                    )
                }
            }

            // Letter Detail Studio Bottom Sheet
            sheetOpenLetterId?.let { letterId ->
                AlphabetRepository.getLetterById(letterId)?.let { letter ->
                    LetterDetailSheet(
                        letter = letter,
                        isMastered = state.masteredLetters.contains(letter.id),
                        activeTab = state.detailActiveTab,
                        onTabSelected = { viewModel.setDetailTab(it) },
                        onSpeakLetter = { viewModel.speakLetter(letter) },
                        onSpeakHaraka = { haraka -> viewModel.speakHaraka(letter, haraka) },
                        onSpeakHarakaSound = { harakaName -> viewModel.speakHarakaSound(harakaName) },
                        onPlayHarakaLesson = { harakaName -> viewModel.speakHarakaLesson(harakaName) },
                        onSpeakWord = { word -> viewModel.speakWord(word) },
                        onMarkMastered = { viewModel.markLetterMastered(letter.id) },
                        onDismiss = { sheetOpenLetterId = null }
                    )
                }
            }

            // Confetti / Stars Celebration Overlay
            CelebrationOverlay(
                visible = state.showCelebration,
                message = state.celebrationMessage,
                onDismiss = { viewModel.dismissCelebration() }
            )
        }
    }
}
