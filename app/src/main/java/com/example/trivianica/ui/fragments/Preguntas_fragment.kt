package com.example.trivianica.ui.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.trivianica.model.claseRecurso.cR
import com.example.trivianica.R
import com.example.trivianica.ViewModel.CategoriaViewModel
import com.example.trivianica.adapter.Adapter
import com.example.trivianica.adapter.CategoriaListener
import com.example.trivianica.databinding.FragmentPreguntasBinding

class Preguntas_fragment : Fragment(), CategoriaListener
{
    private val ViewModel by lazy {ViewModelProviders.of(this).get(CategoriaViewModel::class.java)}

    var Vista: View? = null

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        Thread.sleep(5000)
        Vista = FragmentPreguntasBinding.inflate(layoutInflater).root

        val Adapter = Adapter(this)

        if(cR.validador)
        {
            cR.validador = false
            observadorObtenerLista(Vista as LinearLayout, Adapter)
        }
        else
            Adapter.MostarDatos(Vista as LinearLayout,cR.respaldoPregunta)

        return Vista;
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observadorObtenerLista(Vista: View, Adapter: Adapter)
    {
        var nombreCategoria = cR.obtenerNombreCategoria(cR.categoriaId)

        ViewModel.obtenerCategoria(nombreCategoria).observe(this, Observer{ resultado ->
            cR.insertarTamañoListaRegistro(resultado.size)
            var indice: Int
            indice = cR.obtenerPreguntaAleatoria()
            cR.respaldoPregunta = resultado[indice]
            Adapter.MostarDatos(Vista,resultado[indice])
        })
    }
    override fun onOpcionClicked(OpcionMarcada: Int, RespuestaCorrecta: Int, Contenedor: RelativeLayout, Respuesta: TextView)
    {
        var media:  MediaPlayer

        cR.validador = true
        cR.opcionCorrecta = true

        if(OpcionMarcada == RespuestaCorrecta)
        {
            cR.puntaje++
            Contenedor.setBackgroundResource(R.drawable.opciones_correcta)
            media = MediaPlayer.create(getContext(), R.raw.audio_opcion_correcta);
            media.start();
        }
        else
        {
            Respuesta.visibility = View.VISIBLE
            Contenedor.setBackgroundResource(R.drawable.opciones_erronea)

            var recursoAudio = R.id.RL1
            if(RespuestaCorrecta == 2) recursoAudio = R.id.RL2
            if(RespuestaCorrecta == 3) recursoAudio = R.id.RL3

            Vista!!.findViewById<RelativeLayout>(recursoAudio).setBackgroundResource(R.drawable.opciones_erronea_complemento)

            media = MediaPlayer.create(getContext(), R.raw.audio_opcion_incorrecta);
            media.start();
            media.setOnCompletionListener{
                Vista!!.findViewById<TextView>(R.id.txtRespuesta).visibility = View.VISIBLE
            }
            cR.opcionCorrecta = false
        }
        cR.acumulador++

        Handler(Looper.getMainLooper()).postDelayed(object : Runnable
        {
            override fun run()
            {navegacionTombolaPuntuacion()}
        }, cR.tiempoEspera())
    }

    fun navegacionTombolaPuntuacion()
    {
        /*Si el acumulador es menor a 5, se navega a la tombola*/
        if( cR.acumulador < 5 )
        {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_Preguntas_to_Categorias)
        }
        else/*Sino, al puntaje*/
        {
            cR.acumulador = 0;
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_Preguntas_to_Puntuacion)
        }
    }
}