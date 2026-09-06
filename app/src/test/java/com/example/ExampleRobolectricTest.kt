package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.AlphabetRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("حروف الهجاء", appName)
  }

  @Test
  fun `verify all 28 arabic alphabet letters exist`() {
    assertEquals(28, AlphabetRepository.letters.size)
    assertEquals("أ", AlphabetRepository.letters.first().char)
    assertEquals("ي", AlphabetRepository.letters.last().char)
  }

  @Test
  fun `verify letter phonic sounds and harakat are present`() {
    val alif = AlphabetRepository.letters.first()
    assertEquals("أَ", alif.soundPhonic)
    assertEquals(4, alif.harakat.size)
    val fatha = alif.harakat[0]
    val damma = alif.harakat[1]
    val kasra = alif.harakat[2]
    assertEquals("الفتحة", fatha.name)
    assertEquals("الضمة", damma.name)
    assertEquals("الكسرة", kasra.name)

    val baa = AlphabetRepository.letters[1]
    assertEquals("بَ", baa.soundPhonic)
  }
}
