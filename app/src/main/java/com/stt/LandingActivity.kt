package com.stt

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import java.util.*

class LandingActivity : AppCompatActivity() {

    private lateinit var mBtnSpeak: AppCompatImageView
    private lateinit var mResultTextView: TextView
    private val CODE = 7584

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        mBtnSpeak = findViewById(R.id.speakBtn)
        mBtnSpeak.setOnClickListener { view ->
            onSpeakButtonClick()
        }
        mResultTextView = findViewById(R.id.resultMessage)
    }

    private fun onSpeakButtonClick() {
        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt))

        try {
            startActivityForResult(intent, CODE)
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    var result: ArrayList<String> = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    mResultTextView.text = result.get(0)
                }
            }
            else ->
                Toast.makeText(this, getString(R.string.error_in_recognizing), Toast.LENGTH_SHORT).show()
        }
    }

}
