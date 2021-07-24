package com.example.trivianica.adapter

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.trivianica.R
import com.example.trivianica.model.objetoPregunta

class Adapter(private val listener: CategoriaListener)
{
    fun mostarDatos(viewPregunta: View, objeto: objetoPregunta)
    {
        val datos = DatosPreguntas(viewPregunta)
        asignarDatos(datos, objeto)
    }
    private inner class DatosPreguntas(view: View)
    {
        val viewPreguntas = view
        val pregunta:  TextView = view.findViewById<View>(R.id.txtPregunta) as TextView
        val opcion1:   TextView = view.findViewById<View>(R.id.txtOpcion1)  as TextView
        val opcion2:   TextView = view.findViewById<View>(R.id.txtOpcion2)  as TextView
        val opcion3:   TextView = view.findViewById<View>(R.id.txtOpcion3)  as TextView
        val respuesta: TextView = view.findViewById<View>(R.id.txtRespuesta) as TextView
        val rlOpcion1: RelativeLayout = view.findViewById<View>(R.id.RL1) as RelativeLayout
        val rlOpcion2: RelativeLayout = view.findViewById<View>(R.id.RL2) as RelativeLayout
        val rlOpcion3: RelativeLayout = view.findViewById<View>(R.id.RL3) as RelativeLayout

        val rlPrincipal: RelativeLayout = view.findViewById<View>(R.id.rlPricipalOpciones) as RelativeLayout
    }
    private fun asignarDatos(datos: DatosPreguntas, objeto: objetoPregunta)
    {
        comprobarCamposVacios(objeto, datos);

        datos.rlPrincipal.visibility = View.VISIBLE

        datos.pregunta.text  = objeto.Pregunta
        datos.opcion1.text   = objeto.Opcion1
        datos.opcion2.text   = objeto.Opcion2
        datos.opcion3.text   = objeto.Opcion3
        datos.respuesta.text = objeto.Respuesta

        val opcionCorrecta = objeto.RespuestaId.toInt()
        listenersOpcionMarcada(datos, opcionCorrecta)
    }

    private fun listenersOpcionMarcada(holder: DatosPreguntas, opcionCorrecta: Int)
    {
        holder.rlOpcion1.setOnClickListener{
            inhabilarBotones(holder.viewPreguntas, opcionCorrecta)
            listener.onOpcionClicked(1, opcionCorrecta)
        }

        holder.rlOpcion2.setOnClickListener{
            inhabilarBotones(holder.viewPreguntas, opcionCorrecta)
            listener.onOpcionClicked(2, opcionCorrecta)
        }

        holder.rlOpcion3.setOnClickListener{
            inhabilarBotones(holder.viewPreguntas, opcionCorrecta)
            listener.onOpcionClicked(3, opcionCorrecta)
        }
    }

    private fun inhabilarBotones(viewPregunta: View, RespuestaCorrecta: Int)
    {
        var primerRL = R.id.RL2
        var segundoRl = R.id.RL3
        if(RespuestaCorrecta == 2)
        {
            primerRL = R.id.RL1
            segundoRl = R.id.RL3
        }
        if(RespuestaCorrecta == 3)
        {
            primerRL = R.id.RL1
            segundoRl = R.id.RL2
        }
        viewPregunta.findViewById<RelativeLayout>(primerRL).isEnabled  = false
        viewPregunta.findViewById<RelativeLayout>(segundoRl).isEnabled = false
    }
    private fun comprobarCamposVacios(objeto: objetoPregunta, holder: DatosPreguntas)
    {
        if(objeto.Opcion3 == "Campo_Vacio")
        {
            objeto.Opcion3 = objeto.Opcion2
            holder.rlOpcion2.visibility = View.GONE
        }
        if(objeto.Respuesta == "Campo_Vacio")
            objeto.Respuesta = ""
    }
}