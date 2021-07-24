package com.example.trivianica.ui.fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
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
import android.widget.Button
import androidx.core.animation.addListener
import androidx.navigation.fragment.findNavController
import com.example.trivianica.model.claseRecurso.cR
import com.example.trivianica.R
import com.example.trivianica.ui.activities.MenuPrincipal_activity

class Bienvenida_fragment : Fragment(), View.OnClickListener
{
    var Vista: View? = null
    var BotonComenzar: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        Vista = inflater.inflate(R.layout.fragment_bienvenida, container, false);

        if(cR.acumulador == 0)
            cR.generarlistaAleatoria()

        BotonComenzar = Vista!!.findViewById<Button>(R.id.BotonComenzar)

        BotonComenzar!!.setOnClickListener(this)

        return Vista
    }

    override fun onClick(v: View?)
    {
        var animColor = ObjectAnimator
            .ofArgb(
                BotonComenzar,
                "backgroundColor",
                Color.parseColor("#99CB38"),
                Color.YELLOW,
                Color.parseColor("#FFA500"),
                Color.RED,
                Color.parseColor("#FFC0CB"),
                Color.parseColor("#EE82EE"),
                Color.BLUE,
                Color.CYAN,
                Color.parseColor("#99CB38")
            )
            .apply{
                duration = 1000
                start()
            }

        animColor.addListener(
            object: AnimatorListenerAdapter()
            {
                override fun onAnimationEnd(animation: Animator?)
                {
                    super.onAnimationEnd(animation)
                    findNavController().navigate(R.id.action_Jugadores_to_Categorias)
                }
            })
    }//Fin de la función
}