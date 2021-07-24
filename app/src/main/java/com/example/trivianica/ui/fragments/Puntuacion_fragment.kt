package com.example.trivianica.ui.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.trivianica.model.claseRecurso.cR
import com.example.trivianica.R
import com.example.trivianica.model.claseRecurso.RegistroDispositivo
import com.example.trivianica.model.claseRecurso.cR.Valores.puntaje


class Puntuacion_fragment : Fragment()
{
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        for(categoriaId in (0..5))
        {
            val nombreCategoria = cR.getNombreDeRegistro(categoriaId)
            RegistroDispositivo.setRegistroEnDispositivo(nombreCategoria, categoriaId)
        }

        /*Obtiene el View principal en el Fragmento*/
        val view = inflater.inflate(R.layout.fragment_puntuacion, container, false)

        /*Audio que reacciona en función del puntaje de la partida*/
        audioReaccionPartida(view)

        /*Presenta su puntaje al usuario.*/
        view.findViewById<TextView>(R.id.textoPuntuacion).text = "$puntaje/5"

        val audioReaccion = MediaPlayer.create(context, recursoReaccion())

        audioReaccion.start()
        audioReaccion.setOnCompletionListener{
            if(puntaje == 5)
                mostrarBotones(view)
            else
            {
                val audioReaccionSG = MediaPlayer.create(context, R.raw.audio_puedes_seguir_jugando)
                Thread.sleep(70)
                audioReaccionSG.start()
                audioReaccionSG.setOnCompletionListener{mostrarBotones(view)}
            }
        }

        return view
    }
    private fun mostrarBotones(Vista: View)
    {
        Thread.sleep(50)
        Vista.findViewById<RelativeLayout>(R.id.BotonesElecciones).visibility = View.VISIBLE
        eleccionClickListener(Vista)
    }

    /*Acciona los eventos de los botones*/
    private fun eleccionClickListener(Vista: View)
    {
        cR.acumulador = 0
        cR.opcionCorrecta = true
        puntaje = 0

        val botonSeguir = Vista.findViewById<Button>(R.id.Seguir)
        val botonSalir = Vista.findViewById<Button>(R.id.Salir)

        /*Evento del Botón Seguir*/
        botonSeguir.setOnClickListener{
            botonSalir.isEnabled = false
            val animacion = agrandarBoton(botonSeguir)
            animacion.start()
            animacion.addListener(object:
                AnimatorListenerAdapter()
            {
                override fun onAnimationEnd(animation: Animator?)
                {
                    super.onAnimationEnd(animation)
                    findNavController().navigate(R.id.action_Puntuacion_to_Jugadores)
                }
            })
        }

        /*Evento del Botón Salir*/
        botonSalir.setOnClickListener{
            botonSeguir.isEnabled = false
            val animacion = agrandarBoton(botonSalir)
            animacion.start()
            animacion.addListener(object:
                AnimatorListenerAdapter()
            {
                override fun onAnimationEnd(animation: Animator?)
                {
                    super.onAnimationEnd(animation)
                    /*Cierra la aplicación de raíz y quita la tarea del teléfono.*/
                    activity?.finishAndRemoveTask()
                }
            })
        }
    }
    private fun agrandarBoton(boton: Button): Animator
    {
        val animacion = AnimatorInflater.loadAnimator(context, R.animator.anim_click_botones)
        animacion
            .apply {
                setTarget(boton)
            }
        return animacion
    }
    private fun audioReaccionPartida(Vista: View)
    {
        val media: MediaPlayer
        if(resultadoPartida())
        {
            media = MediaPlayer.create(context, R.raw.audio_partida_ganada)
        }
        else
        {
            media = MediaPlayer.create(context, R.raw.audio_partida_perdida)
            /*Se oculta la animación de confetti*/
            Vista.findViewById<View>(R.id.animacionPuntuacion).visibility = View.GONE
        }

        media.start()
        //Thread.sleep(200)
    }

    private fun resultadoPartida(): Boolean
    {
        var resultado = false
        if(puntaje >= 4)
            resultado = true
        return resultado
    }
    private fun recursoReaccion(): Int
    {
        return when (puntaje)
        {
            0 -> R.raw.audio_puntuacion_cero
            4 -> R.raw.audio_puntuacion_de_cuatro
            5 -> R.raw.audio_puntuacion_perfecta
            else -> R.raw.audio_puntuacion_baja
        }
    }
}