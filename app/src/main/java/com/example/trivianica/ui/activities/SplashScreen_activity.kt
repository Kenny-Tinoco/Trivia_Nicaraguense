package com.example.trivianica.ui.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import com.example.trivianica.R
import com.example.trivianica.model.objectoPregunta
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONArray
import org.json.JSONObject

class SplashScreen_activity : AppCompatActivity()
{
    private val tiempo: Int = 4000
    lateinit var Audio: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splashscreen)

        Audio = MediaPlayer.create(this, R.raw.audio_bienvenida)
        Audio.start()

        Handler(Looper.getMainLooper()).postDelayed(object : Runnable
        {
            override fun run()
            {
                val intent = Intent(this@SplashScreen_activity, MenuPrincipal_activity::class.java)
                startActivity(intent);
                finish()
            }
        }, tiempo.toLong())
    }
}