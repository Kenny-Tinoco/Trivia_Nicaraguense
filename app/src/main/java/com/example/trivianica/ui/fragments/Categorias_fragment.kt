package com.example.trivianica.ui.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import com.example.trivianica.model.claseRecurso.cR
import com.example.trivianica.ui.animacionTombola.InterfazEventoTerminado
import com.example.trivianica.ui.animacionTombola.ImageViewScrolling
import com.example.trivianica.R
import com.example.trivianica.databinding.FragmentCategoriasBinding
import kotlinx.android.synthetic.main.fragment_categorias.view.*

class Categorias_fragment : Fragment(), InterfazEventoTerminado
{
    private var imagen: ImageViewScrolling? = null
    private lateinit var titulo: TextView

    private var banderaNavegacion = true
    private var media:  MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        /*Obtiene la vista actual*/
        val viewCategoria = FragmentCategoriasBinding.inflate(layoutInflater).root

        imagen = viewCategoria.Imagen
        titulo = viewCategoria.Titulo

        /*Sónido de redobles de támbores*/
        media = MediaPlayer.create(context, R.raw.redoble_tambores)
        media!!.start()

        /*Asignar el la categoria a categoriaId*/
        val indice = cR.acumulador
        cR.categoriaId = cR.listaAleatoriaCategoria[indice]

        iniciarAnimacion(titulo, false)

        return viewCategoria
    }
    private fun iniciarAnimacion(titulo: TextView, validador: Boolean)
    {
        /*Inicio de la animacion de tombola*/
        imagen!!.setAnimacion(cR.categoriaId,11, titulo, validador)
        imagen!!.setEventEnd(this)
    }

    /*Cuando la tombola termina, se navega al Fragmento Pregunta*/
    override fun eventEnd()
    {
        if(banderaNavegacion)
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
        if(imagen != null) imagen = null
    }

    override fun onPause()
    {
        super.onPause()
        if(media != null && media!!.isPlaying) media!!.pause()

        if(imagen != null) imagen = null

        banderaNavegacion = false
    }

    override fun onResume()
    {
        super.onResume()
        if(media != null)  media!!.start()

        if(imagen != null) iniciarAnimacion(titulo, true)

        banderaNavegacion = true
    }
}