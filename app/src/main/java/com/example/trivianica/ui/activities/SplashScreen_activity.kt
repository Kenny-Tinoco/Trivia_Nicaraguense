package com.example.trivianica.ui.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import com.example.trivianica.R

class SplashScreen_activity : AppCompatActivity()
{
    private val duration: Long = 4000
    private lateinit var audio: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splashscreen)

        audio = MediaPlayer.create(this, R.raw.audio_bienvenida)
        audio.start()

        Handler( Looper.getMainLooper() )
            .postDelayed(
                {
                    val intent = Intent(this, MenuPrincipal_activity::class.java)
                    startActivity(intent)
                    finish()
                },
                duration)
    }
}