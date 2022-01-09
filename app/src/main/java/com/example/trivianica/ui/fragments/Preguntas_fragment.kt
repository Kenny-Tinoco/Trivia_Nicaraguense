package com.example.trivianica.ui.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.trivianica.model.claseRecurso.cR
import com.example.trivianica.R
import com.example.trivianica.ViewModel.CategoriaViewModel
import com.example.trivianica.adapter.Adapter
import com.example.trivianica.adapter.CategoriaListener
import com.example.trivianica.databinding.FragmentPreguntasBinding
import com.example.trivianica.model.claseRecurso.RegistroDispositivo
import com.example.trivianica.model.objetoPregunta

class Preguntas_fragment : Fragment(), CategoriaListener
{
    private var viewPreguntas: View? = null
    private val adapter by lazy {Adapter(this)}
    private val viewModel by lazy {ViewModelProviders.of(this).get(CategoriaViewModel::class.java)}

    private var mediaPlayer : MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        viewPreguntas = FragmentPreguntasBinding.inflate(layoutInflater).root

        if(cR.validador)
        {
            cR.validador = false
            observadorObtenerLista()
        }
        else
            adapter.mostarDatos(viewPreguntas as LinearLayout,cR.respaldoPregunta)

        return viewPreguntas
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observadorObtenerLista()
    {
        val nombreCategoria = cR.getNombreCategoria(cR.categoriaId)

        viewModel
            .obtenerCategoria(nombreCategoria)
            .observe(
                this, { listaPreguntas -> mostrarPregunta(listaPreguntas)}
                    )
    }

    private fun mostrarPregunta(listaPreguntas: MutableList<objetoPregunta>)
    {
        RegistroDispositivo.inicializarRegistros(listaPreguntas.size, cR.categoriaId)

        /* pregunta aleatoria que no ha parecido antes */
        val indice = preguntaAleatoria()

        cR.respaldoPregunta = listaPreguntas[indice]
        adapter.mostarDatos(viewPreguntas as LinearLayout, listaPreguntas[indice])
    }

    override fun onOpcionClicked(opcionMarcada: Int, opcionCorrecta: Int)
    {
        cR.preguntasHechas++
        cR.validador = true
        cR.opcionCorrecta = true

        if(opcionMarcada == opcionCorrecta)
        {
            opcionCorrectaMarcada(opcionCorrecta)
        }
        else
        {
            opcionIncorrectaMarcada(opcionMarcada, opcionCorrecta)
        }

        /* 'mediaPlayer' inicializada dentro de las funciones */
        mediaPlayer!!.start()

        Handler(Looper.getMainLooper())
            .postDelayed({ navegacion_A_Tombola_o_Puntuacion() }, cR.tiempoEspera())
    }

    private fun opcionIncorrectaMarcada(opcionMarcada: Int, opcionCorrecta: Int)
    {
        cR.opcionCorrecta = false

        val contenedorMarcado  = contenedorOpcion(opcionMarcada)
        val contenedorCorrecto = contenedorOpcion(opcionCorrecta)

        val animacion = agrandarContenedorIncorrecto(contenedorMarcado)
        animacion.start()

        contenedorMarcado.setBackgroundResource( R.drawable.opciones_erronea)
        animacion.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                contenedorCorrecto.setBackgroundResource(R.drawable.opciones_erronea_complemento)
            }
        })

        mediaPlayer = MediaPlayer.create(context, R.raw.audio_opcion_incorrecta)

        Handler(Looper.getMainLooper())
            .postDelayed({viewPreguntas!!.findViewById<TextView>(R.id.txtRespuesta).visibility = View.VISIBLE},600)
    }

    private fun agrandarContenedorIncorrecto(contenedorOpcionMarcada: RelativeLayout): Animator
    {
        val animacion = AnimatorInflater.loadAnimator(context, R.animator.anim_click_opciones__opcioncorrecta)
        animacion
            .apply{
                setTarget(contenedorOpcionMarcada)
            }
        return animacion
    }

    private fun opcionCorrectaMarcada(opcionCorrecta: Int)
    {
        cR.puntaje++
        val contenedorOpcionCorrecta = contenedorOpcion(opcionCorrecta)
        animacionOpcionCorrecta(contenedorOpcionCorrecta)
        contenedorOpcionCorrecta.setBackgroundResource(R.drawable.opciones_correcta)
        mediaPlayer = MediaPlayer.create(context, R.raw.audio_opcion_correcta)
    }

    private fun animacionOpcionCorrecta(contenedorOpcionCorrecta: RelativeLayout)
    {
        val animacion = AnimatorInflater.loadAnimator(context, R.animator.anim_click_opciones__opcioncorrecta)

        animacion
            .apply {
                setTarget(contenedorOpcionCorrecta)
                start()
            }
    }

    private fun contenedorOpcion(opcionMarcada: Int): RelativeLayout
    {
        val nombreContenedor: Int =
        when (opcionMarcada)
        {
            1    -> R.id.RL1
            2    -> R.id.RL2
            else -> R.id.RL3
        }
        return viewPreguntas!!.findViewById(nombreContenedor)
    }

    private fun navegacion_A_Tombola_o_Puntuacion()
    {
        if( cR.preguntasHechas < 5 )
        {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_Preguntas_to_Categorias)
        }
        else
        {
            cR.preguntasHechas = 0
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_Preguntas_to_Puntuacion)
        }
    }

    private fun preguntaAleatoria(): Int
    {
        val preguntasHechas = RegistroDispositivo.getPreguntasRealizadas(cR.categoriaId)
        val preguntaId : Int = RegistroDispositivo.getIdPreguntaAleatoria(preguntasHechas)

        RegistroDispositivo.guardarDatoEnListaPreguntasRealizadas(preguntaId, preguntasHechas)
        RegistroDispositivo.updatePreguntasRealizadas(cR.categoriaId, preguntasHechas)

        return preguntaId
    }
}