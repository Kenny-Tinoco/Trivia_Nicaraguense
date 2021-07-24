package com.example.trivianica.ui.activities

import android.app.assist.AssistContent
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.trivianica.R
import org.json.JSONObject

class MenuPrincipal_activity : AppCompatActivity()
{
    companion object Contexto
    {lateinit var ContextoApp: Context}

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        ContextoApp = this
    }

    override fun onBackPressed()
    {Log.d("Boton Atras","Presionado")}
}