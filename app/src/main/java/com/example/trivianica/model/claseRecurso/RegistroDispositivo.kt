package com.example.trivianica.model.claseRecurso

import android.content.Context
import com.example.trivianica.ui.activities.MenuPrincipal_activity

class RegistroDispositivo
{
    /*
    * Esta clase contiene los metodos utilizados para
    * obtener y guardar registros de la preguntas que
    * le van apareciendo al usuario en el dispositivos,
    * esto con el fin de que no se le repitan las preguntas.
    */

    companion object
    {
        val Contexto = MenuPrincipal_activity.ContextoApp
        val VACIO = -1

        var Arte        = PreguntasRealizadas()
        var Deporte     = PreguntasRealizadas()
        var Gastronomia = PreguntasRealizadas()
        var Geografia   = PreguntasRealizadas()
        var Historia    = PreguntasRealizadas()
        var Tradicion   = PreguntasRealizadas()

        /*
        * Clase anidada
        * Estructura que almacena en una lista los id's de las
        * preguntas ya realizadas al usuario. (Preguntas de una
        * determinada categoria)
        */
        class PreguntasRealizadas
        {
            var tamaño: Int = VACIO
            var nombre: String = ""
            lateinit var preguntas: MutableList<Int>
            fun inicializarLista(nombreCategoria: String)
            {
                nombre = nombreCategoria
                preguntas = MutableList(tamaño){ VACIO }
            }
        }

        fun getRegistroEnDispositivo(Nombre: String, categoriaId: Int)
        {
            var PreferenciasCompartidas = Contexto.getSharedPreferences(Nombre, Context.MODE_PRIVATE)
            var cadenaGuardada = PreferenciasCompartidas.getString(Nombre,"-1")

            if (cadenaGuardada != null && cadenaGuardada != "")
                getIdsPreguntasRealizadas(cadenaGuardada, categoriaId)
        }

        fun setRegistroEnDispositivo(nombreCategoria: String, categoriaId: Int)
        {
            val sharedPreferences = Contexto.getSharedPreferences(nombreCategoria, Context.MODE_PRIVATE)
            val preguntasRealizadas = getPreguntasRealizadas(categoriaId)

            if(preguntasRealizadas.tamaño != VACIO)
                with(sharedPreferences.edit())
                {
                    val nuevaCadena = concatenarLosElementosDe(preguntasRealizadas.preguntas)
                    putString(nombreCategoria, nuevaCadena)
                    commit()
                }
        }
        fun inicializarRegistros(size: Int, categoriaId: Int)
        {
            var preguntasHechas = getPreguntasRealizadas(categoriaId)

            if(preguntasHechas.tamaño == cR.VACIO)
            {
                val nombreDeRegistro = cR.getNombreDeRegistro(categoriaId)
                preguntasHechas.tamaño = size
                preguntasHechas.inicializarLista(nombreDeRegistro)
                getRegistroEnDispositivo(nombreDeRegistro, categoriaId)
                updatePreguntasRealizadas(categoriaId, preguntasHechas)
            }
        }

        fun updatePreguntasRealizadas(categoriaId: Int, Preguntas: PreguntasRealizadas)
        {
            if(categoriaId == cR.ARTE)        Arte        = Preguntas
            if(categoriaId == cR.DEPORTE)     Deporte     = Preguntas
            if(categoriaId == cR.GASTRONOMIA) Gastronomia = Preguntas
            if(categoriaId == cR.GEOGRAFIA)   Geografia   = Preguntas
            if(categoriaId == cR.HISTORIA)    Historia    = Preguntas
            if(categoriaId == cR.TRADICION)   Tradicion   = Preguntas
        }

        fun getPreguntasRealizadas(categoriaId: Int): PreguntasRealizadas
        {
            var preguntasHechas = PreguntasRealizadas()

            if(categoriaId == cR.ARTE)        preguntasHechas = Arte
            if(categoriaId == cR.DEPORTE)     preguntasHechas = Deporte
            if(categoriaId == cR.GASTRONOMIA) preguntasHechas = Gastronomia
            if(categoriaId == cR.GEOGRAFIA)   preguntasHechas = Geografia
            if(categoriaId == cR.HISTORIA)    preguntasHechas = Historia
            if(categoriaId == cR.TRADICION)   preguntasHechas = Tradicion

            return preguntasHechas
        }
        private fun concatenarLosElementosDe(idPreguntas: MutableList<Int>): String
        {
            var elementosConcatenados = ""

            for(elemento in idPreguntas)
            {
                if(elemento != VACIO)
                {
                    elementosConcatenados += "$elemento "
                }
            }

            return elementosConcatenados
        }
        fun getIdPreguntaAleatoria(preguntasHechas: PreguntasRealizadas): Int
        {
            var preguntaId: Int = (0 until preguntasHechas.tamaño).random()

            if(preguntasHechas.preguntas.contains(preguntaId))
                preguntaId = getIdPreguntaAleatoria(preguntasHechas)

            return preguntaId
        }

        private fun getIdsPreguntasRealizadas(cadenaGuardada: String, categoriaId: Int)
        {
            val Preguntas = getPreguntasRealizadas(categoriaId)
            var primerIndice = 0
            while(primerIndice < cadenaGuardada.length)
            {
                val segundoIndice = getSegundoIndice(primerIndice, cadenaGuardada, cadenaGuardada.length)
                val preguntaId    = cadenaGuardada.substring(primerIndice, segundoIndice).toInt()

                guardarDatoEnListaPreguntasRealizadas(preguntaId, Preguntas)
                comprobarListaLLena(Preguntas)

                primerIndice = segundoIndice + 1
            }
            updatePreguntasRealizadas(categoriaId, Preguntas)
        }
        private fun getSegundoIndice(indice: Int, cadenaGuardada: String, tamaño: Int): Int
        {
            var variableIndice = indice

            if( variableIndice != tamaño )
            {
                if( cadenaGuardada[variableIndice].toString() != " " )
                {
                    variableIndice = getSegundoIndice(variableIndice+1, cadenaGuardada, tamaño)
                }
            }
            return variableIndice
        }

        fun guardarDatoEnListaPreguntasRealizadas(preguntaId: Int, listaPreguntasRealizadas: PreguntasRealizadas)
        {
            var bandera = true
            for( (i, valor) in listaPreguntasRealizadas.preguntas.withIndex())
            {
                if(valor == -1 && bandera)
                {
                    listaPreguntasRealizadas.preguntas[i] = preguntaId
                    bandera = false
                }
            }
        }
        private fun comprobarListaLLena(listaPreguntasRealizadas: PreguntasRealizadas)
        {
            if(listaPreguntasRealizadas.preguntas.find{it == -1} == null)
            {
                for(i in (0 until listaPreguntasRealizadas.tamaño))
                {
                    listaPreguntasRealizadas.preguntas[i] = -1
                }
            }
        }//Final de comprobarListaLLena
    }//Final de companion object
}//Final de la Clase