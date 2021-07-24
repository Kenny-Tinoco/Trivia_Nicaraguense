package com.example.trivianica.adapter

import android.widget.RelativeLayout
import android.widget.TextView

interface CategoriaListener
{fun onOpcionClicked(OpcionMarcada: Int, RespuestaCorrecta:Int, Contenedor: RelativeLayout, Respuesta: TextView)}