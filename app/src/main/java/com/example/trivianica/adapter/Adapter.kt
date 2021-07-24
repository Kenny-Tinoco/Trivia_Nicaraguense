package com.example.trivianica.adapter

import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.trivianica.R
import com.example.trivianica.model.objectoPregunta

class Adapter(val Listener: CategoriaListener)
{
    fun MostarDatos(Vista: View, objecto: objectoPregunta)
    {
        var Views= FragmentPreguntasViews(Vista)
        AsignarDatos(Views, objecto)
    }
    inner class FragmentPreguntasViews(Vista: View)
    {
        var Vista = Vista
        var Pregunta:  TextView
        var Opcion1:   TextView
        var Opcion2:   TextView
        var Opcion3:   TextView
        var Respuesta: TextView
        var RL1: RelativeLayout
        var RL2: RelativeLayout
        var RL3: RelativeLayout

        var rlPadreOpciones: RelativeLayout
        init
        {
            Pregunta  = Vista.findViewById<View>(R.id.txtPregunta) as TextView
            Opcion1   = Vista.findViewById<View>(R.id.txtOpcion1)  as TextView
            Opcion2   = Vista.findViewById<View>(R.id.txtOpcion2)  as TextView
            Opcion3   = Vista.findViewById<View>(R.id.txtOpcion3)  as TextView
            Respuesta = Vista.findViewById<View>(R.id.txtRespuesta) as TextView
            RL1      = Vista.findViewById<View>(R.id.RL1) as RelativeLayout
            RL2      = Vista.findViewById<View>(R.id.RL2) as RelativeLayout
            RL3      = Vista.findViewById<View>(R.id.RL3) as RelativeLayout

            rlPadreOpciones = Vista.findViewById<View>(R.id.RlPadreOpciones) as RelativeLayout
        }
    }
    fun AsignarDatos(holder: FragmentPreguntasViews, objecto: objectoPregunta) {
        CamposVacios(objecto, holder);

        holder.rlPadreOpciones.visibility = View.VISIBLE

        holder.Pregunta.text  = objecto!!.Pregunta
        holder.Opcion1.text   = objecto!!.Opcion1
        holder.Opcion2.text   = objecto!!.Opcion2
        holder.Opcion3.text   = objecto!!.Opcion3
        holder.Respuesta.text = objecto!!.Respuesta

        holder.RL1.setOnClickListener{
            inhabilarBotones(holder.Vista, objecto.RespuestaId.toInt())
            Listener.onOpcionClicked(1, objecto.RespuestaId.toInt(), holder.RL1, holder.Respuesta)
        }
        holder.RL2.setOnClickListener{
            inhabilarBotones(holder.Vista, objecto.RespuestaId.toInt())
            Listener.onOpcionClicked(2, objecto.RespuestaId.toInt(), holder.RL2, holder.Respuesta)
        }
        holder.RL3.setOnClickListener{
            inhabilarBotones(holder.Vista, objecto.RespuestaId.toInt())
            Listener.onOpcionClicked(3, objecto.RespuestaId.toInt(), holder.RL3, holder.Respuesta)
        }
    }
    fun inhabilarBotones(Vista: View, RespuestaCorrecta: Int)
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
        Vista!!.findViewById<RelativeLayout>(primerRL).isEnabled  = false
        Vista!!.findViewById<RelativeLayout>(segundoRl).isEnabled = false
    }
    fun CamposVacios(objecto: objectoPregunta, holder: FragmentPreguntasViews)
    {
        if(objecto.Opcion3 == "Campo_Vacio")
        {
            objecto.Opcion3 = ""
            holder.RL3.visibility = View.GONE
        }
        if(objecto.Respuesta == "Campo_Vacio")
            objecto.Respuesta = ""
    }
}