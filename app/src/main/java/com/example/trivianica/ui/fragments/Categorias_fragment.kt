package com.example.trivianica.ui.fragments

import android.app.ActionBar
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import com.example.trivianica.model.claseRecurso.cR
import com.example.trivianica.ui.animacionTombola.animEventoFinalizar
import com.example.trivianica.ui.animacionTombola.ImageViewScrolling
import com.example.trivianica.R
import java.lang.Exception

class Categorias_fragment : Fragment(), animEventoFinalizar
{
    var Imagen: ImageViewScrolling? = null
    lateinit var Titulo: TextView
    lateinit var Vista: View

    var validadorNavegación = true
    var media:  MediaPlayer? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        /*Obtiene la vista actual*/
        Vista = inflater.inflate(R.layout.fragment_categorias, container, false)

        Imagen = Vista.findViewById(R.id.Imagen);
        Titulo = Vista.findViewById(R.id.Titulo);

        /*Sonido de redobles de tambores*/
        media = MediaPlayer.create(getContext(), R.raw.redoble_tambores);
        media!!.start();

        /*Asignar el la categoria a categoriaId*/
        var indice = cR.acumulador
        cR.categoriaId = cR.listaAleatoriaCategoria[indice]

        iniciarAnimacion(Vista, Titulo, false)

        return Vista;
    }
    private fun iniciarAnimacion(Vista: View, Titulo: TextView, validador: Boolean)
    {
        /*Inicio de la animacion de tombola*/
        Imagen!!.setAnimacion(cR.categoriaId,11, Vista, Titulo, validador)
        Imagen!!.setEventEnd(this)
    }

    /*Cuando la tombola termina, se navega al Fragmento Pregunta*/
    override fun eventEnd(Categoria: Int, vista: View)
    {
        if(validadorNavegación)
        {
            Handler(Looper.getMainLooper())
                .postDelayed(
                    { NavHostFragment.findNavController(this).navigate(R.id.fg_Preguntas) },
                    2000)
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        if(media != null)
        {
            media!!.stop()
            media!!.release()
            media = null
        }
        if(Imagen != null)
            Imagen = null
    }

    override fun onPause()
    {
        super.onPause()
        if(media != null && media!!.isPlaying)
            media!!.pause()
        if(Imagen != null)
            Imagen = null

        validadorNavegación = false
    }

    override fun onResume()
    {
        super.onResume()
        if(media != null)
            media!!.start()
        if(Imagen != null)
            iniciarAnimacion(Vista, Titulo, true)
        validadorNavegación = true
    }
}