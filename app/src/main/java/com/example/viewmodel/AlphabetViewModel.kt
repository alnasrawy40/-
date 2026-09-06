package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AlphabetRepository
import com.example.model.ArabicLetter
import com.example.model.HarakaInfo
import com.example.sound.SoundManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppTab {
    LETTERS,
    TRACING,
    GAMES,
    AWARDS
}

enum class GameMode {
    GUESS_LETTER,
    BALLOON_POP,
    HARAKAT_QUIZ
}

data class BalloonItem(
    val id: Int,
    val char: String,
    val colorHex: Long,
    val offsetXPercent: Float, // 0.1 to 0.9
    val isPopped: Boolean = false
)

data class GuessQuestion(
    val word: String,
    val emoji: String,
    val correctLetter: ArabicLetter,
    val options: List<ArabicLetter>
)

data class HarakatQuestion(
    val letter: ArabicLetter,
    val targetHaraka: HarakaInfo,
    val options: List<HarakaInfo>
)

data class UiState(
    val selectedTab: AppTab = AppTab.LETTERS,
    val selectedGroup: Int = 0, // 0 means all groups
    val selectedLetter: ArabicLetter? = null,
    val detailActiveTab: Int = 0, // 0: Harakat, 1: Positions, 2: Words, 3: Trace
    val masteredLetters: Set<Int> = emptySet(),
    val totalStars: Int = 0,
    val showCelebration: Boolean = false,
    val celebrationMessage: String = "",
    val isMuted: Boolean = false,

    // Games
    val currentGameMode: GameMode = GameMode.GUESS_LETTER,
    val guessQuestion: GuessQuestion? = null,
    val guessFeedback: Boolean? = null, // true=correct, false=wrong, null=none
    val balloonTarget: ArabicLetter? = null,
    val balloons: List<BalloonItem> = emptyList(),
    val balloonScore: Int = 0,
    val harakatQuestion: HarakatQuestion? = null,
    val harakatFeedback: Boolean? = null
)

class AlphabetViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("arabic_alphabet_prefs", Context.MODE_PRIVATE)
    val soundManager = SoundManager(application)

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        // Load saved stars and mastered letters
        val savedStars = prefs.getInt("stars", 10)
        val savedMastered = prefs.getStringSet("mastered_ids", emptySet())
            ?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()

        _uiState.update {
            it.copy(
                totalStars = savedStars,
                masteredLetters = savedMastered,
                selectedLetter = AlphabetRepository.letters.first()
            )
        }

        initGuessGame()
        initBalloonGame()
        initHarakatGame()
    }

    fun selectTab(tab: AppTab) {
        soundManager.playClick()
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun setGroup(group: Int) {
        soundManager.playClick()
        _uiState.update { it.copy(selectedGroup = group) }
    }

    fun selectLetter(letter: ArabicLetter) {
        soundManager.playClick()
        _uiState.update { it.copy(selectedLetter = letter, detailActiveTab = 0) }
        speakLetter(letter)
    }

    fun setDetailTab(tabIndex: Int) {
        soundManager.playClick()
        _uiState.update { it.copy(detailActiveTab = tabIndex) }
    }

    fun speakLetter(letter: ArabicLetter) {
        // Play recorded pronunciation by SOUND via MediaPlayer (e.g. "بَ.. بَ.. بَطَّة")
        soundManager.playLetterAudio(letter)
    }

    fun speakPureLetter(letter: ArabicLetter) {
        // Play pure phonic sound via MediaPlayer (e.g. "بَ.. بَ")
        soundManager.playPureLetterSound(letter)
    }

    fun speakLetterName(letter: ArabicLetter) {
        // Play letter name via MediaPlayer (e.g. "أَلِف")
        soundManager.playLetterNameAudio(letter)
    }

    fun speakHaraka(letter: ArabicLetter, haraka: HarakaInfo) {
        // Play recording of letter with haraka (الفتحة، الضمة، الكسرة) via MediaPlayer
        soundManager.playHarakaAudio(letter, haraka)
    }

    fun speakHaraka(haraka: HarakaInfo, letterName: String) {
        val letter = AlphabetRepository.letters.find { it.name == letterName }
            ?: _uiState.value.selectedLetter
        if (letter != null) {
            soundManager.playHarakaAudio(letter, haraka)
        } else {
            soundManager.speak("${haraka.letterWithHaraka}. ${haraka.exampleWord}")
        }
    }

    fun speakHarakaSound(harakaName: String) {
        // Play pronunciation recording of the haraka itself ("الْفَتْحَة", "الضَّمَّة", "الْكَسْرَة", "السُّكُون")
        soundManager.playHarakaSound(harakaName)
    }

    fun speakHarakaLesson(harakaName: String) {
        // Play educational lesson audio teaching the sound and mouth movement for the haraka
        soundManager.playHarakaLesson(harakaName)
    }

    fun speakWord(word: String) {
        soundManager.speak(word)
    }

    fun toggleMute() {
        val newMuted = !_uiState.value.isMuted
        soundManager.isMuted = newMuted
        _uiState.update { it.copy(isMuted = newMuted) }
    }

    fun markLetterMastered(letterId: Int) {
        val updated = _uiState.value.masteredLetters + letterId
        val updatedStars = _uiState.value.totalStars + 3
        prefs.edit()
            .putInt("stars", updatedStars)
            .putStringSet("mastered_ids", updated.map { it.toString() }.toSet())
            .apply()

        soundManager.playSuccess()
        _uiState.update {
            it.copy(
                masteredLetters = updated,
                totalStars = updatedStars,
                showCelebration = true,
                celebrationMessage = "أَحْسَنْتَ يَا بَطَل! حَصَلْتَ عَلَى 3 نُجُوم!"
            )
        }
    }

    fun dismissCelebration() {
        _uiState.update { it.copy(showCelebration = false) }
    }

    // --- GAMES LOGIC ---
    fun selectGameMode(mode: GameMode) {
        soundManager.playClick()
        _uiState.update { it.copy(currentGameMode = mode) }
        when (mode) {
            GameMode.GUESS_LETTER -> initGuessGame()
            GameMode.BALLOON_POP -> initBalloonGame()
            GameMode.HARAKAT_QUIZ -> initHarakatGame()
        }
    }

    fun initGuessGame() {
        val all = AlphabetRepository.letters
        val correct = all.random()
        val distractors = all.filter { it.id != correct.id }.shuffled().take(3)
        val options = (distractors + correct).shuffled()
        _uiState.update {
            it.copy(
                guessQuestion = GuessQuestion(
                    word = correct.primaryWord,
                    emoji = correct.primaryEmoji,
                    correctLetter = correct,
                    options = options
                ),
                guessFeedback = null
            )
        }
        viewModelScope.launch {
            delay(200)
            soundManager.speak("مَا هُوَ حَرْفُ كَلِمَة ${correct.primaryWord}؟")
        }
    }

    fun answerGuess(selected: ArabicLetter) {
        val current = _uiState.value.guessQuestion ?: return
        if (selected.id == current.correctLetter.id) {
            soundManager.playSuccess()
            addStars(2)
            _uiState.update { it.copy(guessFeedback = true) }
            viewModelScope.launch {
                delay(1200)
                initGuessGame()
            }
        } else {
            soundManager.playWrong()
            _uiState.update { it.copy(guessFeedback = false) }
            viewModelScope.launch {
                delay(900)
                _uiState.update { it.copy(guessFeedback = null) }
            }
        }
    }

    fun initBalloonGame() {
        val all = AlphabetRepository.letters
        val target = all.random()
        val colors = listOf(
            0xFFEF4444, 0xFF3B82F6, 0xFF10B981, 0xFFF59E0B,
            0xFF8B5CF6, 0xFFEC4899, 0xFF06B6D4, 0xFFF97316
        )

        val balloons = mutableListOf<BalloonItem>()
        // Ensure 2-3 target balloons and other random balloons
        val totalBalloons = 6
        val targetSlots = (0 until totalBalloons).shuffled().take(2)

        for (i in 0 until totalBalloons) {
            val char = if (i in targetSlots) {
                target.char
            } else {
                all.filter { it.id != target.id }.random().char
            }
            balloons.add(
                BalloonItem(
                    id = i,
                    char = char,
                    colorHex = colors[i % colors.size],
                    offsetXPercent = (i * 0.15f + 0.08f).coerceIn(0.05f, 0.9f)
                )
            )
        }

        _uiState.update {
            it.copy(
                balloonTarget = target,
                balloons = balloons
            )
        }
        viewModelScope.launch {
            delay(200)
            soundManager.speak("فَرْقِعْ بَالُونَ حَرْفِ: ${target.name}")
        }
    }

    fun popBalloon(balloon: BalloonItem) {
        val target = _uiState.value.balloonTarget ?: return
        if (balloon.char == target.char) {
            soundManager.playPop()
            val updatedBalloons = _uiState.value.balloons.map {
                if (it.id == balloon.id) it.copy(isPopped = true) else it
            }
            val newScore = _uiState.value.balloonScore + 1
            addStars(1)

            val remainingTargets = updatedBalloons.count { it.char == target.char && !it.isPopped }
            _uiState.update {
                it.copy(
                    balloons = updatedBalloons,
                    balloonScore = newScore
                )
            }

            if (remainingTargets == 0) {
                soundManager.playCelebration()
                viewModelScope.launch {
                    delay(1000)
                    initBalloonGame()
                }
            }
        } else {
            soundManager.playWrong()
        }
    }

    fun initHarakatGame() {
        val letter = AlphabetRepository.letters.random()
        val targetHaraka = letter.harakat.random()
        val options = letter.harakat.shuffled()

        _uiState.update {
            it.copy(
                harakatQuestion = HarakatQuestion(
                    letter = letter,
                    targetHaraka = targetHaraka,
                    options = options
                ),
                harakatFeedback = null
            )
        }

        viewModelScope.launch {
            delay(200)
            soundManager.speak("أَيْنَ صَوْتُ: ${targetHaraka.letterWithHaraka}؟")
        }
    }

    fun answerHaraka(selected: HarakaInfo) {
        val current = _uiState.value.harakatQuestion ?: return
        if (selected.name == current.targetHaraka.name) {
            soundManager.playSuccess()
            addStars(2)
            _uiState.update { it.copy(harakatFeedback = true) }
            viewModelScope.launch {
                delay(1200)
                initHarakatGame()
            }
        } else {
            soundManager.playWrong()
            _uiState.update { it.copy(harakatFeedback = false) }
            viewModelScope.launch {
                delay(900)
                _uiState.update { it.copy(harakatFeedback = null) }
            }
        }
    }

    private fun addStars(count: Int) {
        val newStars = _uiState.value.totalStars + count
        prefs.edit().putInt("stars", newStars).apply()
        _uiState.update { it.copy(totalStars = newStars) }
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.shutdown()
    }
}
