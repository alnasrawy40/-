package com.example.model

data class HarakaInfo(
    val name: String, // الفتحة، الضمة، الكسرة، السكون
    val symbol: String, // ـَ ، ـُ ، ـِ ، ـْ
    val letterWithHaraka: String, // أَ، بَ ...
    val exampleWord: String, // أَرْنَب
    val emoji: String
)

data class LetterPositions(
    val isolated: String, // ب
    val beginning: String, // بـ
    val middle: String, // ـبـ
    val end: String, // ـب
    val beginningWord: String, // بَقَرَة
    val middleWord: String, // حَبْل
    val endWord: String // كِتَاب
)

data class WordCard(
    val word: String,
    val emoji: String,
    val meaningHint: String = ""
)

data class ArabicLetter(
    val id: Int,
    val char: String,
    val name: String,
    val primaryWord: String,
    val primaryEmoji: String,
    val group: Int, // 1 to 7 for 1st grade curriculum units
    val colorHex: Long,
    val harakat: List<HarakaInfo>,
    val positions: LetterPositions,
    val words: List<WordCard>
) {
    /**
     * Primary phonic sound of the letter (e.g. أَ، بَ، تَ...) for 1st grade phonics,
     * prioritizing sound over traditional letter name.
     */
    val soundPhonic: String
        get() = harakat.firstOrNull { it.name.contains("فتحة") }?.letterWithHaraka ?: "$charَ"
}
