package com.edu.uabc.appm.textoavozkotlin


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*



class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

  private var tts: TextToSpeech? = null
  private var buttonSpeak: Button? = null
  private var editText: EditText? = null
  private var pitch= 1.0
  private var speed= 1.0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    buttonSpeak = this.button_speak
    editText = this.edittext_input

    buttonSpeak!!.isEnabled = false;
    tts = TextToSpeech(this, this)

    buttonSpeak!!.setOnClickListener { speakOut() }
  }

  override fun onInit(status: Int) {

    if (status == TextToSpeech.SUCCESS) {
      // set US English as language for tts
      val result = tts!!.setLanguage(Locale.US)

      if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
        Log.e("TTS","The Language specified is not supported!")
      } else {
        buttonSpeak!!.isEnabled = true
      }

    } else {
      Log.e("TTS", "Initilization Failed!")
    }



    mySeekBar.setOnSeekBarChangeListener(
      object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
        // Display the current progress of SeekBar
        pitch = ((i.toFloat()/(seekBar.max/2)).toDouble())
        Log.e("TEXTOAVOZ","Porcentaje:"+(i.toFloat()/(seekBar.max/2)))
      }


      override fun onStartTrackingTouch(seekBar: SeekBar) {
        // Do something
      }

      override fun onStopTrackingTouch(seekBar: SeekBar) {
        // Do something
      }
    })


    mySeekSpeed.setOnSeekBarChangeListener(
      object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
          // Display the current progress of SeekBar
          speed = ((i.toFloat()/(seekBar.max/2)).toDouble())
        }


        override fun onStartTrackingTouch(seekBar: SeekBar) {
          // Do something
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
          // Do something
        }
      })



  }

  private fun speakOut() {
    val text = editText!!.text.toString()

    tts!!.setLanguage(Locale.forLanguageTag("es"))
    tts!!.setPitch(pitch.toFloat())
    tts!!.setSpeechRate(speed.toFloat());
    tts!!.speak(text, TextToSpeech.LANG_AVAILABLE, null,"")

  }

  public override fun onDestroy() {
    // Shutdown TTS
    if (tts != null) {
      tts!!.stop()
      tts!!.shutdown()
    }
    super.onDestroy()
  }

}

