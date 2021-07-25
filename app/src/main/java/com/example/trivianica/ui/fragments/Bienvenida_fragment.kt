package com.example.trivianica.ui.fragments

import android.animation.*
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator.REVERSE
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import androidx.core.animation.addListener
import androidx.navigation.fragment.findNavController
import com.example.trivianica.model.claseRecurso.cR
import com.example.trivianica.R
import com.example.trivianica.databinding.FragmentBienvenidaBinding
import com.example.trivianica.ui.activities.MenuPrincipal_activity
import kotlinx.android.synthetic.main.fragment_bienvenida.view.*

class Bienvenida_fragment : Fragment(), View.OnClickListener
{
    private var viewBienvenida: View?  = null
    private var botonComenzar: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        viewBienvenida = FragmentBienvenidaBinding.inflate(layoutInflater).root

        if(cR.acumulador == 0)
            cR.generarlistaAleatoria()

        botonComenzar = viewBienvenida!!.BotonComenzar
        botonComenzar!!.setOnClickListener(this)

        return viewBienvenida
    }

    override fun onClick(v: View?)
    {
        animacionBoton()
    }//Fin de la función onClick
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
}//Fin de fragmento