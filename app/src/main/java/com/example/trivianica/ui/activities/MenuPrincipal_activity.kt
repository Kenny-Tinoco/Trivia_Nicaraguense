package com.example.trivianica.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.trivianica.R

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