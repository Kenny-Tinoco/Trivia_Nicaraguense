package com.example.trivianica.ui.fragments

import android.media.MediaPlayer
import android.net.Uri
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


class Puntuacion_fragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        for(i in (0..5))
            cR.guardarListasRegistro(i, cR.obtenerNombreRegistroCategoria(i))

        /*Obtiene el View actual.*/
        val Vista: View = inflater.inflate(R.layout.fragment_puntuacion, container, false);

        /*Audio que reacciona en función del puntaje de la partida*/
        audioReaccionResultadoPartdida(Vista)

        /*Presenta su puntaje al usuario.*/
        Vista.findViewById<TextView>(R.id.textoPuntuacion).text = "${cR.puntaje}/5"

        var audioReaccion: MediaPlayer

        audioReaccion = MediaPlayer.create(context, recursoReaccion())

        audioReaccion.start()
        audioReaccion.setOnCompletionListener{
            if(cR.puntaje == 5)
                mostrarBotones(Vista)
            else
            {
                var audioReaccionSG: MediaPlayer
                audioReaccionSG = MediaPlayer.create(context, R.raw.audio_puedes_seguir_jugando)
                Thread.sleep(70)
                audioReaccionSG.start()
                audioReaccionSG.setOnCompletionListener{mostrarBotones(Vista)}
            }
        }

        return Vista;
    }
    fun mostrarBotones(Vista: View)
    {
        Thread.sleep(50)
        Vista.findViewById<RelativeLayout>(R.id.BotonesElecciones).visibility = View.VISIBLE
        eleccionClickListener(Vista)
    }

    /*Acciona los eventos de los botones*/
    fun eleccionClickListener(Vista: View)
    {
        cR.acumulador = 0;
        cR.opcionCorrecta = true
        cR.puntaje = 0;

        /*Evento del Botón Seguir*/
        Vista.findViewById<Button>(R.id.Seguir).setOnClickListener{
            Vista.findViewById<Button>(R.id.Salir).isEnabled = false
            findNavController().navigate(R.id.action_Puntuacion_to_Jugadores)
        }

        /*Evento del Boton Salir*/
        Vista.findViewById<Button>(R.id.Salir).setOnClickListener{
            Vista.findViewById<Button>(R.id.Seguir).isEnabled = false

            /*Cierra la aplicación de raíz y quita la tarea del telefono.*/
            activity?.finishAndRemoveTask()
        }
    }
    fun audioReaccionResultadoPartdida(Vista: View)
    {
        var media: MediaPlayer
        if(resultadoPartida())
        {
            media = MediaPlayer.create(context, R.raw.audio_partida_ganada)
        }
        else
        {
            media = MediaPlayer.create(context, R.raw.audio_partida_perdida)
            /*Ocultamiento de animación de confetti*/
            Vista.findViewById<View>(R.id.animacionPuntuacion).visibility = View.GONE
        }

        media.start()
        //Thread.sleep(200)
    }
    /*Retorna verdadero si el puntaje es mayor a o igual a 4 y falso en caso contrario*/
    fun resultadoPartida(): Boolean
    {
        var uri: Uri

        var resultado:Boolean = false
        if(cR.puntaje > 3)  resultado = true
        return resultado
    }
    fun recursoReaccion(): Int
    {
        var recurso: Int
        if(cR.puntaje == 5)
            recurso = R.raw.audio_puntuacion_perfecta
        else if(cR.puntaje == 4)
            recurso = R.raw.audio_puntuacion_de_cuatro
        else if(cR.puntaje == 0)
            recurso = R.raw.audio_puntuacion_cero
        else
            recurso = R.raw.audio_puntuacion_baja
        return recurso
    }
}