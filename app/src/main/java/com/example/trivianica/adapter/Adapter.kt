package com.example.trivianica.adapter

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.trivianica.R
import com.example.trivianica.model.objetoPregunta
import kotlinx.android.synthetic.main.fragment_preguntas.view.*

class Adapter(private val listener: CategoriaListener)
{
    fun mostarDatos(viewPregunta: View, objeto: objetoPregunta)
    {
        val datos = DatosPreguntas(viewPregunta)
        asignarDatos(datos, objeto)
    }
    private inner class DatosPreguntas(viewPregunta: View)
    {
        /*Nota
        * Recordar que el 'viewPregunta' es un view de binding
        */

        val viewPreguntas = viewPregunta
        val pregunta:  TextView = viewPregunta.txtPregunta
        val opcion1:   TextView = viewPregunta.txtOpcion1
        val opcion2:   TextView = viewPregunta.txtOpcion2
        val opcion3:   TextView = viewPregunta.txtOpcion3
        val respuesta: TextView = viewPregunta.txtRespuesta
        val rlOpcion1: RelativeLayout = viewPregunta.RLOpcion1
        val rlOpcion2: RelativeLayout = viewPregunta.RLOpcion2
        val rlOpcion3: RelativeLayout = viewPregunta.RLOpcion3

        val rlOpciones: RelativeLayout = viewPregunta.RLOpciones
    }
    private fun asignarDatos(datos: DatosPreguntas, objeto: objetoPregunta)
    {
        comprobarCamposVacios(objeto, datos)

        datos.rlOpciones.visibility = View.VISIBLE

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
        var primerRL = R.id.RLOpcion2
        var segundoRl = R.id.RLOpcion3
        if(RespuestaCorrecta == 2)
        {
            primerRL = R.id.RLOpcion1
            segundoRl = R.id.RLOpcion3
        }
        if(RespuestaCorrecta == 3)
        {
            primerRL = R.id.RLOpcion1
            segundoRl = R.id.RLOpcion2
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
            if(objeto.RespuestaId.toInt() == 2)
                objeto.RespuestaId = "3"
        }
        if(objeto.Respuesta == "Campo_Vacio")
            objeto.Respuesta = ""
    }
}