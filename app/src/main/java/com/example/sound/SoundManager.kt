package com.example.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.model.ArabicLetter
import com.example.model.HarakaInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.sin

class SoundManager(context: Context) : TextToSpeech.OnInitListener {

    val audioPlayer = ArabicAudioPlayer(context.applicationContext)
    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private val scope = CoroutineScope(Dispatchers.Default)

    var isMuted = false
        set(value) {
            field = value
            audioPlayer.isMuted = value
            if (value) {
                audioPlayer.stop()
                tts?.stop()
            }
        }

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.e("SoundManager", "Error initializing TTS", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val arLocale = Locale("ar")
            val result = tts?.setLanguage(arLocale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback to default or any Arabic dialect
                val saLocale = Locale("ar", "SA")
                tts?.setLanguage(saLocale)
            }
            tts?.setSpeechRate(0.85f) // Slower, clearer cadence for 1st grade elementary learners
            tts?.setPitch(1.1f) // Cheerful, friendly pitch
            isTtsReady = true
        }
    }

    /**
     * Plays the audio recording of the letter pronunciation by its SOUND (e.g. "بَ.. بَ.. بَطَّة") using MediaPlayer.
     * Prioritizes sound phonics over traditional letter name.
     */
    fun playLetterAudio(letter: ArabicLetter) {
        if (isMuted) return
        val played = audioPlayer.playLetter(letter)
        if (!played) {
            speak("${letter.soundPhonic}.. ${letter.soundPhonic}.. ${letter.primaryWord}")
        }
    }

    /**
     * Plays the pure phonic sound of the letter (e.g. "بَ.. بَ") using MediaPlayer.
     */
    fun playPureLetterSound(letter: ArabicLetter) {
        if (isMuted) return
        val played = audioPlayer.playPureLetterSound(letter)
        if (!played) {
            speak("${letter.soundPhonic}.. ${letter.soundPhonic}")
        }
    }

    /**
     * Plays the letter name recording using MediaPlayer (for optional reference).
     */
    fun playLetterNameAudio(letter: ArabicLetter) {
        if (isMuted) return
        val played = audioPlayer.playLetterName(letter)
        if (!played) {
            speak(letter.name)
        }
    }

    /**
     * Plays the recording of the letter with the specific haraka (الفتحة، الضمة، الكسرة) using MediaPlayer.
     * Falls back to TTS if needed.
     */
    fun playHarakaAudio(letter: ArabicLetter, haraka: HarakaInfo) {
        if (isMuted) return
        val played = audioPlayer.playHaraka(letter, haraka)
        if (!played) {
            speak("${haraka.letterWithHaraka}. ${haraka.exampleWord}")
        }
    }

    /**
     * Plays the audio recording for the haraka sound itself (الفتحة، الضمة، الكسرة، السكون) using MediaPlayer.
     */
    fun playHarakaSound(harakaName: String) {
        if (isMuted) return
        val played = audioPlayer.playHarakaSound(harakaName)
        if (!played) {
            speak(harakaName)
        }
    }

    /**
     * Plays the educational audio lesson for a haraka (تعليم أصوات الفتحة والضمة والكسرة).
     */
    fun playHarakaLesson(harakaName: String) {
        if (isMuted) return
        val played = audioPlayer.playHarakaLesson(harakaName)
        if (!played) {
            val text = when {
                harakaName.contains("فتحة") -> "صَوْتُ الْفَتْحَة: نَفْتَحُ الْفَمَ لِلْأَعْلَى: أَ.. بَ.. تَ"
                harakaName.contains("ضمة") -> "صَوْتُ الضَّمَّة: نَضُمُّ الشَّفَتَيْنِ لِلْأَمَام: أُ.. بُ.. تُ"
                harakaName.contains("كسرة") -> "صَوْتُ الْكَسْرَة: نَخْفِضُ الْفَكَّ وَنَبْتَسِم: إِ.. بِ.. تِ"
                else -> "صَوْتُ السُّكُون: وُقُوفٌ عَلَى صَوْتِ الْحَرْف: أْ.. بْ.. تْ"
            }
            speak(text)
        }
    }

    fun speak(text: String) {
        if (isMuted) return
        if (isTtsReady && tts != null) {
            tts?.stop()
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "ArabicAlphabetTTS")
        } else {
            // Synthesized tone feedback if TTS is not yet ready
            playTone(523.25, 120) // High C
        }
    }

    fun playClick() {
        if (isMuted) return
        scope.launch {
            playTone(659.25, 60) // E5
        }
    }

    fun playPop() {
        if (isMuted) return
        scope.launch {
            // Frequency sweep upwards for a fun bubble pop
            playSweep(400.0, 900.0, 70)
        }
    }

    fun playSuccess() {
        if (isMuted) return
        scope.launch {
            // Happy triad chime: C5, E5, G5, C6
            playTone(523.25, 80)
            Thread.sleep(85)
            playTone(659.25, 80)
            Thread.sleep(85)
            playTone(783.99, 100)
            Thread.sleep(105)
            playTone(1046.50, 200)
        }
    }

    fun playWrong() {
        if (isMuted) return
        scope.launch {
            // Gentle low boing
            playTone(329.63, 120)
            Thread.sleep(130)
            playTone(261.63, 160)
        }
    }

    fun playCelebration() {
        if (isMuted) return
        scope.launch {
            val notes = doubleArrayOf(523.25, 659.25, 783.99, 1046.50, 783.99, 1046.50)
            for (note in notes) {
                playTone(note, 100)
                Thread.sleep(110)
            }
        }
    }

    private fun playTone(freqHz: Double, durationMs: Int) {
        try {
            val sampleRate = 22050
            val numSamples = (sampleRate * (durationMs / 1000.0)).toInt().coerceAtLeast(1)
            val generatedSnd = ShortArray(numSamples)
            val twoPi = 2.0 * Math.PI

            for (i in 0 until numSamples) {
                // Envelope decay to prevent clicks
                val attack = (numSamples * 0.1).coerceAtLeast(1.0)
                val decay = (numSamples * 0.9).coerceAtLeast(1.0)
                val env = when {
                    i < attack -> (i / attack)
                    else -> ((numSamples - i) / decay).coerceIn(0.0, 1.0)
                }
                val sample = sin(twoPi * i / (sampleRate / freqHz)) * env
                generatedSnd[i] = (sample * 28000).toInt().toShort()
            }

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(numSamples * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(generatedSnd, 0, numSamples)
            audioTrack.play()
            Thread.sleep(durationMs.toLong() + 20)
            audioTrack.release()
        } catch (e: Exception) {
            Log.e("SoundManager", "Error playing tone", e)
        }
    }

    private fun playSweep(startFreq: Double, endFreq: Double, durationMs: Int) {
        try {
            val sampleRate = 22050
            val numSamples = (sampleRate * (durationMs / 1000.0)).toInt().coerceAtLeast(1)
            val generatedSnd = ShortArray(numSamples)
            var phase = 0.0

            for (i in 0 until numSamples) {
                val t = i.toDouble() / numSamples
                val currentFreq = startFreq + (endFreq - startFreq) * t
                phase += 2.0 * Math.PI * currentFreq / sampleRate
                val env = 1.0 - (i.toDouble() / numSamples)
                val sample = sin(phase) * env
                generatedSnd[i] = (sample * 28000).toInt().toShort()
            }

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(numSamples * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(generatedSnd, 0, numSamples)
            audioTrack.play()
            Thread.sleep(durationMs.toLong() + 20)
            audioTrack.release()
        } catch (e: Exception) {
            Log.e("SoundManager", "Error playing sweep", e)
        }
    }

    fun shutdown() {
        audioPlayer.release()
        tts?.stop()
        tts?.shutdown()
    }
}
