package com.example.sound

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import com.example.model.ArabicLetter
import com.example.model.HarakaInfo
import java.io.IOException

/**
 * Audio playback library using Android MediaPlayer to play crystal-clear voice recordings
 * of Arabic letters and short vowels/diacritics (الفتحة، الضمة، الكسرة، السكون).
 */
class ArabicAudioPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    var isMuted: Boolean = false

    private val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
        .build()

    /**
     * Plays an audio file from assets/audio/ using Android's MediaPlayer.
     */
    @Synchronized
    fun playAssetAudio(assetPath: String, onCompletion: (() -> Unit)? = null): Boolean {
        if (isMuted) return false

        try {
            stop()

            val afd: AssetFileDescriptor = try {
                context.assets.openFd(assetPath)
            } catch (e: IOException) {
                Log.w("ArabicAudioPlayer", "Asset audio file not found: $assetPath")
                return false
            }

            val player = MediaPlayer().apply {
                setAudioAttributes(audioAttributes)
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()

                setOnPreparedListener { mp ->
                    try {
                        mp.start()
                    } catch (e: Exception) {
                        Log.e("ArabicAudioPlayer", "Error starting MediaPlayer", e)
                    }
                }

                setOnCompletionListener { mp ->
                    try {
                        mp.reset()
                        mp.release()
                    } catch (e: Exception) {
                        Log.e("ArabicAudioPlayer", "Error releasing MediaPlayer on complete", e)
                    }
                    if (mediaPlayer === mp) {
                        mediaPlayer = null
                    }
                    onCompletion?.invoke()
                }

                setOnErrorListener { mp, what, extra ->
                    Log.e("ArabicAudioPlayer", "MediaPlayer playback error: what=$what extra=$extra")
                    try {
                        mp.reset()
                        mp.release()
                    } catch (e: Exception) {
                        // ignore
                    }
                    if (mediaPlayer === mp) {
                        mediaPlayer = null
                    }
                    true
                }

                prepareAsync()
            }
            mediaPlayer = player
            return true
        } catch (e: Exception) {
            Log.e("ArabicAudioPlayer", "Error setting up MediaPlayer for $assetPath", e)
            return false
        }
    }

    /**
     * Plays the letter recording via MediaPlayer focusing on sound pronunciation (e.g. "بَ.. بَ.. بَطَّة").
     */
    fun playLetter(letter: ArabicLetter, onCompletion: (() -> Unit)? = null): Boolean {
        val path = "audio/letter_${letter.id}.mp3"
        return playAssetAudio(path, onCompletion)
    }

    /**
     * Plays the pure phonic sound of the letter via MediaPlayer (e.g. "بَ.. بَ").
     */
    fun playPureLetterSound(letter: ArabicLetter, onCompletion: (() -> Unit)? = null): Boolean {
        val path = "audio/letter_${letter.id}_pure.mp3"
        val played = playAssetAudio(path, onCompletion)
        if (!played) {
            return playLetter(letter, onCompletion)
        }
        return true
    }

    /**
     * Plays the letter name recording via MediaPlayer (e.g. "أَلِف").
     */
    fun playLetterName(letter: ArabicLetter, onCompletion: (() -> Unit)? = null): Boolean {
        val path = "audio/letter_${letter.id}_name.mp3"
        return playAssetAudio(path, onCompletion)
    }

    /**
     * Plays the diacritic / haraka recording via MediaPlayer for a given letter
     * (الفتحة، الضمة، الكسرة، السكون).
     */
    fun playHaraka(letter: ArabicLetter, haraka: HarakaInfo, onCompletion: (() -> Unit)? = null): Boolean {
        val harakaKey = getHarakaKey(haraka.name)
        val path = "audio/letter_${letter.id}_$harakaKey.mp3"
        return playAssetAudio(path, onCompletion)
    }

    /**
     * Plays the recording of the haraka itself (الْفَتْحَة، الضَّمَّة، الْكَسْرَة، السُّكُون).
     */
    fun playHarakaSound(harakaName: String, onCompletion: (() -> Unit)? = null): Boolean {
        val harakaKey = getHarakaKey(harakaName)
        val path = "audio/haraka_$harakaKey.mp3"
        return playAssetAudio(path, onCompletion)
    }

    /**
     * Plays the educational audio lesson for a specific haraka
     * (تعليم صوت الفتحة، وصوت الضمة، وصوت الكسرة، وصوت السكون).
     */
    fun playHarakaLesson(harakaName: String, onCompletion: (() -> Unit)? = null): Boolean {
        val harakaKey = getHarakaKey(harakaName)
        val path = "audio/haraka_edu_$harakaKey.mp3"
        val played = playAssetAudio(path, onCompletion)
        if (!played) {
            return playHarakaSound(harakaName, onCompletion)
        }
        return true
    }

    private fun getHarakaKey(harakaName: String): String {
        return when {
            harakaName.contains("فتحة") -> "fatha"
            harakaName.contains("ضمة") -> "damma"
            harakaName.contains("كسرة") -> "kasra"
            harakaName.contains("سكون") -> "sukun"
            else -> "fatha"
        }
    }

    /**
     * Stops and releases the current MediaPlayer instance.
     */
    @Synchronized
    fun stop() {
        try {
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    player.stop()
                }
                player.reset()
                player.release()
            }
        } catch (e: Exception) {
            Log.e("ArabicAudioPlayer", "Error stopping MediaPlayer", e)
        } finally {
            mediaPlayer = null
        }
    }

    fun release() {
        stop()
    }
}
