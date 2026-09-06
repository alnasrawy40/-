package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AlphabetRepository
import com.example.model.ArabicLetter
import com.example.ui.components.TracingCanvas
import com.example.ui.theme.KidsBackground
import com.example.ui.theme.KidsNaturalBorder
import com.example.ui.theme.KidsNaturalCream
import com.example.ui.theme.KidsNaturalLavender
import com.example.ui.theme.KidsNaturalLavenderText
import com.example.ui.theme.KidsPrimary
import com.example.ui.theme.KidsPrimaryContainer

@Composable
fun TracingScreen(
    currentLetter: ArabicLetter,
    onLetterSelected: (ArabicLetter) -> Unit,
    onSpeakLetter: (ArabicLetter) -> Unit,
    onCompleted: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val allLetters = AlphabetRepository.letters
    var selectedLetter by remember(currentLetter) { mutableStateOf(currentLetter) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KidsBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 96.dp)
            .testTag("tracing_screen")
    ) {
        // Title Bar with Natural Card Styling
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(2.dp, KidsNaturalBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(KidsNaturalLavender, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "كتابة",
                            tint = KidsNaturalLavenderText,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "سَبُّورَةُ كِتَابَةِ الحُرُوف ✏️",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "اخْتَرْ حَرْفًا وَاكْتُبْهُ بَإِصْبَعِكَ كَمَا فِي المَدْرَسَة",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                IconButton(
                    onClick = { onSpeakLetter(selectedLetter) },
                    modifier = Modifier
                        .size(40.dp)
                        .background(KidsPrimaryContainer, CircleShape)
                        .testTag("tracing_speak_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.VolumeUp,
                        contentDescription = "استمع للحرف",
                        tint = KidsPrimary
                    )
                }
            }
        }

        // Horizontal Letter Selector
        Text(
            text = "اخْتَرِ الحَرْفَ:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(allLetters, key = { it.id }) { letter ->
                val isSelected = selectedLetter.id == letter.id
                val itemColor = Color(letter.colorHex)
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) KidsPrimary else Color.White)
                        .border(
                            width = if (isSelected) 2.dp else 1.5.dp,
                            color = if (isSelected) KidsPrimary else KidsNaturalBorder,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable {
                            selectedLetter = letter
                            onLetterSelected(letter)
                            onSpeakLetter(letter)
                        }
                        .testTag("select_tracing_letter_${letter.id}"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = letter.char,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else itemColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tracing Board Area
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(2.dp, KidsNaturalBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "حَرْفُ (${selectedLetter.char}) • صَوْتُهُ: ${selectedLetter.soundPhonic}",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = KidsPrimary
                    )

                    Text(
                        text = "${selectedLetter.primaryEmoji} ${selectedLetter.primaryWord}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF64748B)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                TracingCanvas(
                    letter = selectedLetter,
                    onCompleted = { onCompleted(selectedLetter.id) }
                )
            }
        }
    }
}
