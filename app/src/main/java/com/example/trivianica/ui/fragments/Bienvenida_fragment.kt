package com.example.trivianica.ui.fragments

import android.animation.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.trivianica.model.claseRecurso.cR
import com.example.trivianica.R

class Bienvenida_fragment : Fragment(), View.OnClickListener
{
    var viewBienvenida: View? = null
    var botonComenzar: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        viewBienvenida = inflater.inflate(R.layout.fragment_bienvenida, container, false)

        if(cR.preguntasHechas == 0)
            cR.generarlistaAleatoria()

        botonComenzar = viewBienvenida!!.findViewById(R.id.BotonComenzar)
        botonComenzar!!.setOnClickListener(this)

        return viewBienvenida
    }

    override fun onClick(v: View?)
    {
        animacionBoton()
    }

    private fun animacionBoton()
    {
        val animacion = AnimatorInflater.loadAnimator(context, R.animator.anim_click_botones)
        animacion.
        apply{
            setTarget(botonComenzar)
            start()
        }

        animacion.addListener(object:
        AnimatorListenerAdapter()
        {
            override fun onAnimationEnd(animation: Animator?)
            {
                super.onAnimationEnd(animation)
                findNavController().navigate(R.id.action_Jugadores_to_Categorias)
            }
        })
    }
}