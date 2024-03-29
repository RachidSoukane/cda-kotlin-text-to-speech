package com.example.kotlin_texttospeech

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var editText: EditText? = null
    private var buttonTime: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonSpeak = this.button_speak
        editText = this.edit_text
        buttonTime =this.button_time

        buttonSpeak!!.isEnabled =false

        tts = TextToSpeech(applicationContext, this)

        buttonSpeak!!.setOnClickListener { speakOut() }
        buttonTime!!.setOnClickListener { timeOut() }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun speakOut() {
        val text = editText!!.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    private fun timeOut(){
        val heures = Instant.now().atZone(ZoneOffset.UTC).hour
        val minutes = Instant.now().atZone(ZoneOffset.UTC).minute
        val secondes = Instant.now().atZone(ZoneOffset.UTC).second
        val text = "Il est ${heures} heures ${minutes} minutes et ${secondes} secondes"
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }
    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.FRANCE)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }
}