package com.example.model

object AlphabetRepository {

    val letters: List<ArabicLetter> = listOf(
        ArabicLetter(
            id = 1,
            char = "أ",
            name = "أَلِف",
            primaryWord = "أَرْنَب",
            primaryEmoji = "🐰",
            group = 1,
            colorHex = 0xFFEF4444, // Red
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "أَ", "أَسَد", "🦁"),
                HarakaInfo("الضمة", "ـُ", "أُ", "أُذُن", "👂"),
                HarakaInfo("الكسرة", "ـِ", "إِ", "إِبْرِيق", "🫖"),
                HarakaInfo("السكون", "ـْ", "أْ", "فَأْر", "🐭")
            ),
            positions = LetterPositions(
                isolated = "أ",
                beginning = "أ",
                middle = "ـأ",
                end = "ـأ",
                beginningWord = "أَسَد",
                middleWord = "فَأْس",
                endWord = "نَبَأ"
            ),
            words = listOf(
                WordCard("أَرْنَب", "🐰"),
                WordCard("أَسَد", "🦁"),
                WordCard("أُمّ", "❤️")
            )
        ),
        ArabicLetter(
            id = 2,
            char = "ب",
            name = "بَاء",
            primaryWord = "بَطَّة",
            primaryEmoji = "🦆",
            group = 1,
            colorHex = 0xFF3B82F6, // Blue
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "بَ", "بَقَرَة", "🐄"),
                HarakaInfo("الضمة", "ـُ", "بُ", "بُرْتُقَال", "🍊"),
                HarakaInfo("الكسرة", "ـِ", "بِ", "بِنْت", "👧"),
                HarakaInfo("السكون", "ـْ", "بْ", "خُبْز", "🍞")
            ),
            positions = LetterPositions(
                isolated = "ب",
                beginning = "بـ",
                middle = "ـبـ",
                end = "ـب",
                beginningWord = "بَاب",
                middleWord = "حَبْل",
                endWord = "كَلْب"
            ),
            words = listOf(
                WordCard("بَطَّة", "🦆"),
                WordCard("بَيْت", "🏠"),
                WordCard("بُرْتُقَال", "🍊")
            )
        ),
        ArabicLetter(
            id = 3,
            char = "ت",
            name = "تَاء",
            primaryWord = "تُفَّاحَة",
            primaryEmoji = "🍎",
            group = 1,
            colorHex = 0xFF10B981, // Green
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "تَ", "تَمْر", "🌴"),
                HarakaInfo("الضمة", "ـُ", "تُ", "تُفَّاح", "🍎"),
                HarakaInfo("الكسرة", "ـِ", "تِ", "تِمْسَاح", "🐊"),
                HarakaInfo("السكون", "ـْ", "تْ", "كَتْكُوت", "🐥")
            ),
            positions = LetterPositions(
                isolated = "ت",
                beginning = "تـ",
                middle = "ـتـ",
                end = "ـت",
                beginningWord = "تِين",
                middleWord = "كِتَاب",
                endWord = "بِنْت"
            ),
            words = listOf(
                WordCard("تُفَّاحَة", "🍎"),
                WordCard("تَمْر", "🌴"),
                WordCard("تِمْسَاح", "🐊")
            )
        ),
        ArabicLetter(
            id = 4,
            char = "ث",
            name = "ثَاء",
            primaryWord = "ثَعْلَب",
            primaryEmoji = "🦊",
            group = 1,
            colorHex = 0xFFF59E0B, // Amber
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "ثَ", "ثَلْج", "❄️"),
                HarakaInfo("الضمة", "ـُ", "ثُ", "ثُعْبَان", "🐍"),
                HarakaInfo("الكسرة", "ـِ", "ثِ", "ثِيَاب", "👕"),
                HarakaInfo("السكون", "ـْ", "ثْ", "كُمَّثْرَى", "🍐")
            ),
            positions = LetterPositions(
                isolated = "ث",
                beginning = "ثـ",
                middle = "ـثـ",
                end = "ـث",
                beginningWord = "ثَوْم",
                middleWord = "مُثَلَّث",
                endWord = "غَيْث"
            ),
            words = listOf(
                WordCard("ثَعْلَب", "🦊"),
                WordCard("ثَلْج", "❄️"),
                WordCard("ثُعْبَان", "🐍")
            )
        ),
        ArabicLetter(
            id = 5,
            char = "ج",
            name = "جِيم",
            primaryWord = "جَمَل",
            primaryEmoji = "🐪",
            group = 2,
            colorHex = 0xFF8B5CF6, // Purple
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "جَ", "جَزَر", "🥕"),
                HarakaInfo("الضمة", "ـُ", "جُ", "جُبْن", "🧀"),
                HarakaInfo("الكسرة", "ـِ", "جِ", "جِسْر", "🌉"),
                HarakaInfo("السكون", "ـْ", "جْ", "نَجْم", "⭐")
            ),
            positions = LetterPositions(
                isolated = "ج",
                beginning = "جـ",
                middle = "ـجـ",
                end = "ـج",
                beginningWord = "جَبَل",
                middleWord = "شَجَرَة",
                endWord = "تَاج"
            ),
            words = listOf(
                WordCard("جَمَل", "🐪"),
                WordCard("جَزَر", "🥕"),
                WordCard("جُبْن", "🧀")
            )
        ),
        ArabicLetter(
            id = 6,
            char = "ح",
            name = "حَاء",
            primaryWord = "حِصَان",
            primaryEmoji = "🐴",
            group = 2,
            colorHex = 0xFFEC4899, // Pink
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "حَ", "حَمَامَة", "🕊️"),
                HarakaInfo("الضمة", "ـُ", "حُ", "حُوت", "🐋"),
                HarakaInfo("الكسرة", "ـِ", "حِ", "حِصَان", "🐴"),
                HarakaInfo("السكون", "ـْ", "حْ", "بَحْر", "🌊")
            ),
            positions = LetterPositions(
                isolated = "ح",
                beginning = "حـ",
                middle = "ـحـ",
                end = "ـح",
                beginningWord = "حَقِيبَة",
                middleWord = "سَحَابَة",
                endWord = "مِفْتَاح"
            ),
            words = listOf(
                WordCard("حِصَان", "🐴"),
                WordCard("حَلِيب", "🥛"),
                WordCard("حُوت", "🐋")
            )
        ),
        ArabicLetter(
            id = 7,
            char = "خ",
            name = "خَاء",
            primaryWord = "خَرُوف",
            primaryEmoji = "🐑",
            group = 2,
            colorHex = 0xFF06B6D4, // Cyan
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "خَ", "خَاتَم", "💍"),
                HarakaInfo("الضمة", "ـُ", "خُ", "خُبْز", "🥖"),
                HarakaInfo("الكسرة", "ـِ", "خِ", "خِيَار", "🥒"),
                HarakaInfo("السكون", "ـْ", "خْ", "نَخْلَة", "🌴")
            ),
            positions = LetterPositions(
                isolated = "خ",
                beginning = "خـ",
                middle = "ـخـ",
                end = "ـخ",
                beginningWord = "خَيْمَة",
                middleWord = "نَخِيل",
                endWord = "بِطِّيخ"
            ),
            words = listOf(
                WordCard("خَرُوف", "🐑"),
                WordCard("خِيَار", "🥒"),
                WordCard("خَاتَم", "💍")
            )
        ),
        ArabicLetter(
            id = 8,
            char = "د",
            name = "دَال",
            primaryWord = "دُرَّاجَة",
            primaryEmoji = "🚲",
            group = 2,
            colorHex = 0xFFF97316, // Orange
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "دَ", "دَرَّاجَة", "🚲"),
                HarakaInfo("الضمة", "ـُ", "دُ", "دُبّ", "🐻"),
                HarakaInfo("الكسرة", "ـِ", "دِ", "دِيك", "🐓"),
                HarakaInfo("السكون", "ـْ", "دْ", "هَدْهَد", "🐦")
            ),
            positions = LetterPositions(
                isolated = "د",
                beginning = "د",
                middle = "ـد",
                end = "ـد",
                beginningWord = "دَفْتَر",
                middleWord = "مَدْرَسَة",
                endWord = "وَلَد"
            ),
            words = listOf(
                WordCard("دُرَّاجَة", "🚲"),
                WordCard("دُبّ", "🐻"),
                WordCard("دِيك", "🐓")
            )
        ),
        ArabicLetter(
            id = 9,
            char = "ذ",
            name = "ذَال",
            primaryWord = "ذُرَة",
            primaryEmoji = "🌽",
            group = 3,
            colorHex = 0xFF14B8A6, // Teal
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "ذَ", "ذَهَب", "🪙"),
                HarakaInfo("الضمة", "ـُ", "ذُ", "ذُبَابَة", "🪰"),
                HarakaInfo("الكسرة", "ـِ", "ذِ", "ذِئْب", "🐺"),
                HarakaInfo("السكون", "ـْ", "ذْ", "جِذْر", "🌱")
            ),
            positions = LetterPositions(
                isolated = "ذ",
                beginning = "ذ",
                middle = "ـذ",
                end = "ـذ",
                beginningWord = "ذَيْل",
                middleWord = "بُذُور",
                endWord = "مُعَاذ"
            ),
            words = listOf(
                WordCard("ذُرَة", "🌽"),
                WordCard("ذِئْب", "🐺"),
                WordCard("ذَهَب", "🪙")
            )
        ),
        ArabicLetter(
            id = 10,
            char = "ر",
            name = "رَاء",
            primaryWord = "رُمَّان",
            primaryEmoji = "🍇",
            group = 3,
            colorHex = 0xFFE11D48, // Rose
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "رَ", "رَجُل", "👨"),
                HarakaInfo("الضمة", "ـُ", "رُ", "رُمَّان", "🍇"),
                HarakaInfo("الكسرة", "ـِ", "رِ", "رِيشَة", "🪶"),
                HarakaInfo("السكون", "ـْ", "رْ", "بَرْق", "⚡")
            ),
            positions = LetterPositions(
                isolated = "ر",
                beginning = "ر",
                middle = "ـر",
                end = "ـر",
                beginningWord = "رَسْم",
                middleWord = "شَارِع",
                endWord = "قَمَر"
            ),
            words = listOf(
                WordCard("رُمَّان", "🍇"),
                WordCard("رِيشَة", "🪶"),
                WordCard("رَبِيع", "🌸")
            )
        ),
        ArabicLetter(
            id = 11,
            char = "ز",
            name = "زَاي",
            primaryWord = "زَرَافَة",
            primaryEmoji = "🦒",
            group = 3,
            colorHex = 0xFF84CC16, // Lime
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "زَ", "زَرَافَة", "🦒"),
                HarakaInfo("الضمة", "ـُ", "زُ", "زُهُور", "💐"),
                HarakaInfo("الكسرة", "ـِ", "زِ", "زِرّ", "🔘"),
                HarakaInfo("السكون", "ـْ", "زْ", "مَزْرَعَة", "🏡")
            ),
            positions = LetterPositions(
                isolated = "ز",
                beginning = "ز",
                middle = "ـز",
                end = "ـز",
                beginningWord = "زَيْتُون",
                middleWord = "جَزَر",
                endWord = "مَوْز"
            ),
            words = listOf(
                WordCard("زَرَافَة", "🦒"),
                WordCard("زَهْرَة", "🌼"),
                WordCard("زَيْتُون", "🫒")
            )
        ),
        ArabicLetter(
            id = 12,
            char = "س",
            name = "سِين",
            primaryWord = "سَمَكَة",
            primaryEmoji = "🐟",
            group = 3,
            colorHex = 0xFF0284C7, // Light Blue
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "سَ", "سَفِينَة", "🚢"),
                HarakaInfo("الضمة", "ـُ", "سُ", "سُلَحْفَاة", "🐢"),
                HarakaInfo("الكسرة", "ـِ", "سِ", "سِتَار", "🪟"),
                HarakaInfo("السكون", "ـْ", "سْ", "مَسْجِد", "🕌")
            ),
            positions = LetterPositions(
                isolated = "س",
                beginning = "سـ",
                middle = "ـسـ",
                end = "ـس",
                beginningWord = "سَاعَة",
                middleWord = "مِسْطَرَة",
                endWord = "شَمْس"
            ),
            words = listOf(
                WordCard("سَمَكَة", "🐟"),
                WordCard("سَيَّارَة", "🚗"),
                WordCard("سَاعَة", "⏰")
            )
        ),
        ArabicLetter(
            id = 13,
            char = "ش",
            name = "شِين",
            primaryWord = "شَمْس",
            primaryEmoji = "☀️",
            group = 4,
            colorHex = 0xFFEAB308, // Yellow
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "شَ", "شَجَرَة", "🌳"),
                HarakaInfo("الضمة", "ـُ", "شُ", "شُرْطِيّ", "👮"),
                HarakaInfo("الكسرة", "ـِ", "شِ", "شِتَاء", "🌧️"),
                HarakaInfo("السكون", "ـْ", "شْ", "عُشْب", "🌿")
            ),
            positions = LetterPositions(
                isolated = "ش",
                beginning = "شـ",
                middle = "ـشـ",
                end = "ـش",
                beginningWord = "شَمْعَة",
                middleWord = "مِشْمِش",
                endWord = "فَرَاش"
            ),
            words = listOf(
                WordCard("شَمْس", "☀️"),
                WordCard("شَجَرَة", "🌳"),
                WordCard("شِرَاع", "⛵")
            )
        ),
        ArabicLetter(
            id = 14,
            char = "ص",
            name = "صَاد",
            primaryWord = "صَقْر",
            primaryEmoji = "🦅",
            group = 4,
            colorHex = 0xFFD97706, // Amber dark
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "صَ", "صَحْرَاء", "🏜️"),
                HarakaInfo("الضمة", "ـُ", "صُ", "صُنْدُوق", "📦"),
                HarakaInfo("الكسرة", "ـِ", "صِ", "صِنَّارَة", "🎣"),
                HarakaInfo("السكون", "ـْ", "صْ", "عَصِير", "🧃")
            ),
            positions = LetterPositions(
                isolated = "ص",
                beginning = "صـ",
                middle = "ـصـ",
                end = "ـص",
                beginningWord = "صَابُون",
                middleWord = "عُصْفُور",
                endWord = "مِقَصّ"
            ),
            words = listOf(
                WordCard("صَقْر", "🦅"),
                WordCard("صُنْدُوق", "📦"),
                WordCard("صَابُون", "🧼")
            )
        ),
        ArabicLetter(
            id = 15,
            char = "ض",
            name = "ضَاد",
            primaryWord = "ضِفْدَع",
            primaryEmoji = "🐸",
            group = 4,
            colorHex = 0xFF059669, // Emerald
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "ضَ", "ضَوْء", "💡"),
                HarakaInfo("الضمة", "ـُ", "ضُ", "ضُرُوس", "🦷"),
                HarakaInfo("الكسرة", "ـِ", "ضِ", "ضِمَادَة", "🩹"),
                HarakaInfo("السكون", "ـْ", "ضْ", "أَرْض", "🌍")
            ),
            positions = LetterPositions(
                isolated = "ض",
                beginning = "ضـ",
                middle = "ـضـ",
                end = "ـض",
                beginningWord = "ضَابِط",
                middleWord = "خُضَار",
                endWord = "بَيْض"
            ),
            words = listOf(
                WordCard("ضِفْدَع", "🐸"),
                WordCard("ضَوْء", "💡"),
                WordCard("ضِرْس", "🦷")
            )
        ),
        ArabicLetter(
            id = 16,
            char = "ط",
            name = "طَاء",
            primaryWord = "طَائِرَة",
            primaryEmoji = "✈️",
            group = 4,
            colorHex = 0xFF2563EB, // Royal Blue
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "طَ", "طَبِيب", "👨‍⚕️"),
                HarakaInfo("الضمة", "ـُ", "طُ", "طُيُور", "🐦"),
                HarakaInfo("الكسرة", "ـِ", "طِ", "طِفْل", "👶"),
                HarakaInfo("السكون", "ـْ", "طْ", "مَطَر", "🌧️")
            ),
            positions = LetterPositions(
                isolated = "ط",
                beginning = "طـ",
                middle = "ـطـ",
                end = "ـط",
                beginningWord = "طَبْلَة",
                middleWord = "قِطَار",
                endWord = "بَطّ"
            ),
            words = listOf(
                WordCard("طَائِرَة", "✈️"),
                WordCard("طَمَاطِم", "🍅"),
                WordCard("طَبِيب", "🩺")
            )
        ),
        ArabicLetter(
            id = 17,
            char = "ظ",
            name = "ظَاء",
            primaryWord = "ظَرْف",
            primaryEmoji = "✉️",
            group = 5,
            colorHex = 0xFF7C3AED, // Violet
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "ظَ", "ظَبْي", "🦌"),
                HarakaInfo("الضمة", "ـُ", "ظُ", "ظُفُر", "💅"),
                HarakaInfo("الكسرة", "ـِ", "ظِ", "ظِلّ", "👤"),
                HarakaInfo("السكون", "ـْ", "ظْ", "عَظْم", "🦴")
            ),
            positions = LetterPositions(
                isolated = "ظ",
                beginning = "ظـ",
                middle = "ـظـ",
                end = "ـظ",
                beginningWord = "ظَهْر",
                middleWord = "نَظَّارَة",
                endWord = "حَافِظ"
            ),
            words = listOf(
                WordCard("ظَرْف", "✉️"),
                WordCard("ظَبْي", "🦌"),
                WordCard("نَظَّارَة", "👓")
            )
        ),
        ArabicLetter(
            id = 18,
            char = "ع",
            name = "عَيْن",
            primaryWord = "عَيْن",
            primaryEmoji = "👁️",
            group = 5,
            colorHex = 0xFF0D9488, // Teal
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "عَ", "عَسَل", "🍯"),
                HarakaInfo("الضمة", "ـُ", "عُ", "عُشّ", "🪺"),
                HarakaInfo("الكسرة", "ـِ", "عِ", "عِنَب", "🍇"),
                HarakaInfo("السكون", "ـْ", "عْ", "ثَعْلَب", "🦊")
            ),
            positions = LetterPositions(
                isolated = "ع",
                beginning = "عـ",
                middle = "ـعـ",
                end = "ـع",
                beginningWord = "عَلَم",
                middleWord = "شَمْعَة",
                endWord = "زَرَع"
            ),
            words = listOf(
                WordCard("عَيْن", "👁️"),
                WordCard("عَسَل", "🍯"),
                WordCard("عِنَب", "🍇")
            )
        ),
        ArabicLetter(
            id = 19,
            char = "غ",
            name = "غَيْن",
            primaryWord = "غَزَال",
            primaryEmoji = "🦌",
            group = 5,
            colorHex = 0xFF4F46E5, // Indigo
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "غَ", "غَابَة", "🌲"),
                HarakaInfo("الضمة", "ـُ", "غُ", "غُرَاب", "🦅"),
                HarakaInfo("الكسرة", "ـِ", "غِ", "غِلَاف", "📁"),
                HarakaInfo("السكون", "ـْ", "غْ", "مَغْسَلَة", "🧼")
            ),
            positions = LetterPositions(
                isolated = "غ",
                beginning = "غـ",
                middle = "ـغـ",
                end = "ـغ",
                beginningWord = "غَيْمَة",
                middleWord = "صَغِير",
                endWord = "صَمْغ"
            ),
            words = listOf(
                WordCard("غَزَال", "🦌"),
                WordCard("غَيْمَة", "☁️"),
                WordCard("غُرَاب", "🦅")
            )
        ),
        ArabicLetter(
            id = 20,
            char = "ف",
            name = "فَاء",
            primaryWord = "فَرَاشَة",
            primaryEmoji = "🦋",
            group = 5,
            colorHex = 0xFFDB2777, // Pink
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "فَ", "فَرَاوِلَة", "🍓"),
                HarakaInfo("الضمة", "ـُ", "فُ", "فُلْفُل", "🫑"),
                HarakaInfo("الكسرة", "ـِ", "فِ", "فِيل", "🐘"),
                HarakaInfo("السكون", "ـْ", "فْ", "ضِفْدَع", "🐸")
            ),
            positions = LetterPositions(
                isolated = "ف",
                beginning = "فـ",
                middle = "ـفـ",
                end = "ـف",
                beginningWord = "فَم",
                middleWord = "تُفَّاح",
                endWord = "هَاتِف"
            ),
            words = listOf(
                WordCard("فَرَاشَة", "🦋"),
                WordCard("فِيل", "🐘"),
                WordCard("فَرَاوِلَة", "🍓")
            )
        ),
        ArabicLetter(
            id = 21,
            char = "ق",
            name = "قَاف",
            primaryWord = "قِطَّة",
            primaryEmoji = "🐱",
            group = 6,
            colorHex = 0xFF9333EA, // Purple
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "قَ", "قَلَم", "✏️"),
                HarakaInfo("الضمة", "ـُ", "قُ", "قُبَّعَة", "🧢"),
                HarakaInfo("الكسرة", "ـِ", "قِ", "قِطَار", "🚂"),
                HarakaInfo("السكون", "ـْ", "قْ", "صَقْر", "🦅")
            ),
            positions = LetterPositions(
                isolated = "ق",
                beginning = "قـ",
                middle = "ـقـ",
                end = "ـق",
                beginningWord = "قَمَر",
                middleWord = "صَقْر",
                endWord = "وَرَق"
            ),
            words = listOf(
                WordCard("قِطَّة", "🐱"),
                WordCard("قَلَم", "✏️"),
                WordCard("قَمَر", "🌙")
            )
        ),
        ArabicLetter(
            id = 22,
            char = "ك",
            name = "كَاف",
            primaryWord = "كِتَاب",
            primaryEmoji = "📖",
            group = 6,
            colorHex = 0xFF0891B2, // Cyan dark
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "كَ", "كَلْب", "🐕"),
                HarakaInfo("الضمة", "ـُ", "كُ", "كُرَة", "⚽"),
                HarakaInfo("الكسرة", "ـِ", "كِ", "كِتَاب", "📖"),
                HarakaInfo("السكون", "ـْ", "كْ", "مَكْتَب", "🪑")
            ),
            positions = LetterPositions(
                isolated = "ك",
                beginning = "كـ",
                middle = "ـكـ",
                end = "ـك",
                beginningWord = "كُرْسِيّ",
                middleWord = "سَمَكَة",
                endWord = "دِيك"
            ),
            words = listOf(
                WordCard("كِتَاب", "📖"),
                WordCard("كُرَة", "⚽"),
                WordCard("كَعْك", "🧁")
            )
        ),
        ArabicLetter(
            id = 23,
            char = "ل",
            name = "لَام",
            primaryWord = "لَيْمُون",
            primaryEmoji = "🍋",
            group = 6,
            colorHex = 0xFF65A30D, // Lime
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "لَ", "لَبَن", "🥛"),
                HarakaInfo("الضمة", "ـُ", "لُ", "لُعْبَة", "🧸"),
                HarakaInfo("الكسرة", "ـِ", "لِ", "لِسَان", "👅"),
                HarakaInfo("السكون", "ـْ", "لْ", "قَلْب", "❤️")
            ),
            positions = LetterPositions(
                isolated = "ل",
                beginning = "لـ",
                middle = "ـلـ",
                end = "ـل",
                beginningWord = "لَوْحَة",
                middleWord = "قَلَم",
                endWord = "جَمَل"
            ),
            words = listOf(
                WordCard("لَيْمُون", "🍋"),
                WordCard("لُعْبَة", "🧸"),
                WordCard("لَحْم", "🥩")
            )
        ),
        ArabicLetter(
            id = 24,
            char = "م",
            name = "مِيم",
            primaryWord = "مَوْز",
            primaryEmoji = "🍌",
            group = 6,
            colorHex = 0xFFEA580C, // Orange
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "مَ", "مَطَر", "🌧️"),
                HarakaInfo("الضمة", "ـُ", "مُ", "مُعَلِّم", "👨‍🏫"),
                HarakaInfo("الكسرة", "ـِ", "مِ", "مِقَصّ", "✂️"),
                HarakaInfo("السكون", "ـْ", "مْ", "شَمْس", "☀️")
            ),
            positions = LetterPositions(
                isolated = "م",
                beginning = "مـ",
                middle = "ـمـ",
                end = "ـم",
                beginningWord = "مَدْرَسَة",
                middleWord = "نَمْلَة",
                endWord = "قَلَم"
            ),
            words = listOf(
                WordCard("مَوْز", "🍌"),
                WordCard("مَسْجِد", "🕌"),
                WordCard("مِفْتَاح", "🔑")
            )
        ),
        ArabicLetter(
            id = 25,
            char = "ن",
            name = "نُون",
            primaryWord = "نَحْلَة",
            primaryEmoji = "🐝",
            group = 7,
            colorHex = 0xFFCA8A04, // Yellow dark
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "نَ", "نَجْمَة", "⭐"),
                HarakaInfo("الضمة", "ـُ", "نُ", "نُقُود", "🪙"),
                HarakaInfo("الكسرة", "ـِ", "نِ", "نِسْر", "🦅"),
                HarakaInfo("السكون", "ـْ", "نْ", "عِنَب", "🍇")
            ),
            positions = LetterPositions(
                isolated = "ن",
                beginning = "نـ",
                middle = "ـنـ",
                end = "ـن",
                beginningWord = "نَار",
                middleWord = "أَرْنَب",
                endWord = "عَيْن"
            ),
            words = listOf(
                WordCard("نَحْلَة", "🐝"),
                WordCard("نَجْمَة", "⭐"),
                WordCard("نَهْر", "🏞️")
            )
        ),
        ArabicLetter(
            id = 26,
            char = "هـ",
            name = "هَاء",
            primaryWord = "هِلَال",
            primaryEmoji = "🌙",
            group = 7,
            colorHex = 0xFF4338CA, // Indigo dark
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "هَ", "هَدِيَّة", "🎁"),
                HarakaInfo("الضمة", "ـُ", "هُ", "هُدْهُد", "🐦"),
                HarakaInfo("الكسرة", "ـِ", "هِ", "هِلَال", "🌙"),
                HarakaInfo("السكون", "ـْ", "هْ", "فَهْد", "🐆")
            ),
            positions = LetterPositions(
                isolated = "هـ",
                beginning = "هـ",
                middle = "ـهـ",
                end = "ـه",
                beginningWord = "هَرَم",
                middleWord = "نَهْر",
                endWord = "وَجْه"
            ),
            words = listOf(
                WordCard("هِلَال", "🌙"),
                WordCard("هَدِيَّة", "🎁"),
                WordCard("هَرَم", "🔺")
            )
        ),
        ArabicLetter(
            id = 27,
            char = "و",
            name = "وَاو",
            primaryWord = "وَرْدَة",
            primaryEmoji = "🌹",
            group = 7,
            colorHex = 0xFFBE123C, // Rose dark
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "وَ", "وَلَد", "👦"),
                HarakaInfo("الضمة", "ـُ", "وُ", "وُجُوه", "😀"),
                HarakaInfo("الكسرة", "ـِ", "وِ", "وِسَادَة", "🛋️"),
                HarakaInfo("السكون", "ـْ", "وْ", "حَوْض", "🛁")
            ),
            positions = LetterPositions(
                isolated = "و",
                beginning = "و",
                middle = "ـو",
                end = "ـو",
                beginningWord = "وَرَقَة",
                middleWord = "ضَوْء",
                endWord = "دَلْو"
            ),
            words = listOf(
                WordCard("وَرْدَة", "🌹"),
                WordCard("وَلَد", "👦"),
                WordCard("وِسَادَة", "🛋️")
            )
        ),
        ArabicLetter(
            id = 28,
            char = "ي",
            name = "يَاء",
            primaryWord = "يَد",
            primaryEmoji = "✋",
            group = 7,
            colorHex = 0xFF0D9488, // Teal dark
            harakat = listOf(
                HarakaInfo("الفتحة", "ـَ", "يَ", "يَمَامَة", "🕊️"),
                HarakaInfo("الضمة", "ـُ", "يُ", "يُوسُفِيّ", "🍊"),
                HarakaInfo("الكسرة", "ـِ", "يِ", "يَنَابِيع", "💧"),
                HarakaInfo("السكون", "ـْ", "يْ", "بَيْت", "🏠")
            ),
            positions = LetterPositions(
                isolated = "ي",
                beginning = "يـ",
                middle = "ـيـ",
                end = "ـي",
                beginningWord = "يَمَامَة",
                middleWord = "خِيَار",
                endWord = "جَدِّي"
            ),
            words = listOf(
                WordCard("يَد", "✋"),
                WordCard("يَاسَمِين", "🌼"),
                WordCard("يَخْت", "🛥️")
            )
        )
    )

    fun getLetterById(id: Int): ArabicLetter? = letters.find { it.id == id }
}
